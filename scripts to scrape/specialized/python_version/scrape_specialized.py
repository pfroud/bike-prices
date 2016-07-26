from pprint import pprint

from bs4 import BeautifulSoup
import requests
import json
import pickle

def _remove_duplicates_preserve_order(input):
    """
    http://stackoverflow.com/a/480227

    :param input:
    :type input: list
    :return:
    :rtype: list
    """
    seen = set()
    seen_add = seen.add
    return [x for x in input if not (x in seen or seen_add(x))]


def _get_soup(url):
    """

    :param url:
    :return:
    """
    r = requests.get(url)
    r.raise_for_status()
    return BeautifulSoup(r.text, 'html.parser')


def main():
    """

    :return:
    """
    soup = _get_soup('https://www.specialized.com/us/en/bikes/road')

    models = []
    for bike in soup('a', class_ = 'product-tile__anchor'):
        href = bike['href']
        if 'triathlon' not in href:
            models.append(read_model(href))

    with open('models.pickle', 'wb') as f:
        pickle.dump(models, f)

        # print_input_file(models)


def read_model(relative_url):
    """

    :param relative_url:
    :return:
    """
    model_url = 'https://www.specialized.com' + relative_url
    soup = _get_soup(model_url)

    model_name = soup.find('h1', class_ = 'grid-header__heading').string.strip()

    print(model_name)

    # <a> tags
    version_a_tags = soup('a', class_ = 'colorway-tile__anchor-link')

    # get href of each <a> tag
    version_hrefs = [x['href'] for x in version_a_tags]
    version_hrefs = _remove_duplicates_preserve_order(version_hrefs)

    versions = []
    for url in version_hrefs:
        if 'frameset' not in url and 'module' not in url and 'sbuild' not in url:
            versions.append(read_version(model_url + url))

    print()

    return {'name': model_name, 'versions': versions}


def read_version(url):
    """

    :param url:
    :return:
    """
    soup = _get_soup(url)

    version_name = soup.find('h2', class_ = 'pdp-hero__heading').string

    print('    ' + version_name)

    version_data = {
        'version': soup.find('h2', class_ = 'pdp-hero__heading').string,
        'price': _get_price(soup),
        'frame': _get_spec(soup, "frame"),
        'fork': _get_spec(soup, "fork"),
        'der_front': _get_spec(soup, "front derailleur"),
        'der_rear': _get_spec(soup, "rear derailleur")
    }

    version_data = [str(v) for v in version_data.values()]  # change bs4's NavigableString to str

    return version_data


def _get_spec(soup, spec_name):
    """

    :param soup:
    :param spec_name:
    :return:
    """
    try:
        return soup.find('h2', string = spec_name.upper()).parent.next_sibling.next_sibling.p.string
    except AttributeError:
        return "[{} not found]".format(spec_name)


def _get_price(soup):
    """

    :param soup:
    :return:
    """
    escaped_json = soup.find('div', class_ = 'js-select-container')['data-available-params']

    replace = ['&quot;', '&quoquot;', '&ququot;']
    for target in replace:
        escaped_json = escaped_json.replace(target, '\"')
    escaped_json = escaped_json.replace('quot;', '')

    data = json.loads(escaped_json)

    first_color = data[list(data)[0]]

    for key, value in first_color.items():
        if key.isnumeric():
            return value


def print_input_file(models):
    with open('output.txt', 'w+') as f:
        for model in models:
            f.write(model['name'])


# with open('models.pickle', 'rb') as f:
#     print_input_file(pickle.load(f))

main()
