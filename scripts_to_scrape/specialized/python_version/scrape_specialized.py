"""
An automated scraper which replaces specialized_models.js and specialized_versions.js.
"""

from bs4 import BeautifulSoup
import requests
import json
import os.path

JSON_FILENAME = 'models.json'


def _remove_duplicates_preserve_order(list_in: list) -> list:
    """
    Removes duplicates form a list and preserve the order.
    http://stackoverflow.com/a/480227

    :param list_in: the list to remove duplicates in
    :type list_in: list
    :return: the list with duplicates removed, with order preserved
    :rtype: list
    """
    seen = set()
    seen_add = seen.add
    return [x for x in list_in if not (x in seen or seen_add(x))]


def _get_soup(url: str) -> BeautifulSoup:
    """
    Returnes a BeautifulSoup object of the specified url.

    :param url: url of the page to load
    :type url: str
    :return: BeautifulSoup object of the url
    :rtype: bs4.BeautifulSoup
    """
    r = requests.get(url)
    r.raise_for_status()
    return BeautifulSoup(r.text, 'html.parser')


def main() -> None:
    """
    Writes a file with partial output of Specialized bikes.
    """

    models = []

    if os.path.isfile(JSON_FILENAME):
        with open(JSON_FILENAME, 'r') as f:
            models = json.load(f)
            f.close()
    else:
        soup = _get_soup('https://www.specialized.com/us/en/bikes/road')
        model_a_tags = soup('a', class_ = 'product-tile__anchor')
        model_hrefs = [x['href'] for x in model_a_tags]

        for href in model_hrefs:
            if 'shiv' not in href and 'langster' not in href:
                models.append(read_model(href))

        with open(JSON_FILENAME, 'w') as f:
            json.dump(models, f, indent = 4, sort_keys = True)
            f.close()

    write_output(models)


def read_model(relative_url: str) -> dict:
    """
    Gets info about all versions of a Specialized bike model.

    :param relative_url: a url relative to specialized.com
    :type relative_url: str
    :return: dict containing name of model and dict of versions
    :rtype: dict
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
            versions.append(read_version(model_url + url, model_name))

    print()

    return {'name': model_name, 'versions': versions, 'num_versions': str(len(versions))}


def read_version(url: str, model_name: str) -> dict:
    """
    Gets info about a Specialized bike version.

    :param url: the url of the version
    :type url: str
    :param model_name: name of the model this version is for
    :type model_name: str
    :return: dict containing data about the version
    :rtype: dict
    """
    soup = _get_soup(url)

    version_name = soup.find('h2', class_ = 'pdp-hero__heading').string.replace(model_name + ' ', '')

    print('    ' + version_name)

    version_data = {
        'name': version_name,
        'price': _get_price(soup),
        'frame': _get_spec(soup, "frame"),
        'fork': _get_spec(soup, "fork"),
        'der_front': _get_spec(soup, "front derailleur"),
        'der_rear': _get_spec(soup, "rear derailleur")
    }

    for k, v in version_data.items():
        version_data[k] = str(version_data[k])

    return version_data


def _get_spec(soup, spec_name: str) -> str:
    """
    Gets a specification about a Specialized bike

    :param soup: BeautifulSoup object of the version's webpage
    :type soup: bs4.BeautifulSoup
    :param spec_name: name of the spec to retrieve
    :type: spec_name: str
    :return: value of the specification
    :rtype: str
    """
    try:
        return soup.find('h2', string = spec_name.upper()).parent.next_sibling.next_sibling.p.string
    except AttributeError:
        return "[{} not found]".format(spec_name)


def _get_price(soup) -> str:
    """
    Gets the price of a Specialized bike version.

    :param soup: BeautifulSoup object of the version to get the price of
    :type soup: bs4.BeautifulSoup
    :return: price of the bike version
    :rtype: str
    """
    escaped_json = soup.find('div', class_ = 'js-select-container')['data-available-params']

    replace = ['&quot;', '&quoquot;', '&ququot;']
    for target in replace:
        escaped_json = escaped_json.replace(target, '\"')
    escaped_json = escaped_json.replace('quot;', '')

    data = json.loads(escaped_json)

    first_color = data[list(data)[0]]

    for key, value in first_color['sizes'].items():
        if key.isnumeric():
            return value['price']

    return '[price not found]'


def write_output(models: dict) -> None:
    """
    Writes to a file a list of bike models.
    Only writes version names and prices because material and groupset require human classification.

    :param models: models to print
    :type models: dict
    """

    with open('partial_output.txt', 'w+') as f:
        for model in models:
            buff_name = []
            buff_price = []
            for version in model['versions']:
                buff_name.append(version['name'])
                buff_price.append(version['price'])

            f.write('\n'.join([model['name'] + ': ' + model['num_versions'],
                               ', '.join(buff_name),
                               ', '.join(buff_price),
                               '', '']))
        f.close()


main()
