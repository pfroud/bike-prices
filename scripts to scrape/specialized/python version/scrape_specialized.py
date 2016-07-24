from pprint import pprint

from bs4 import BeautifulSoup
import requests
import json
import pickle

indent = '   '
stop_after = 999


def remove_duplicates_preserve_order(seq):
    """
    http://stackoverflow.com/a/480227
    """
    seen = set()
    seen_add = seen.add
    return [x for x in seq if not (x in seen or seen_add(x))]


def main():
    r = requests.get('https://www.specialized.com/us/en/bikes/road')
    r.raise_for_status()
    soup = BeautifulSoup(r.text, 'html.parser')

    models = []

    count = 0
    for bike in soup('a', class_ = 'product-tile__anchor'):
        if count >= stop_after:
            break
        href = bike['href']
        if 'triathlon' not in href:
            models.append(read_model(href))
        count += 1

    with open('models.pickle', 'wb') as f:
        pickle.dump(models, f)

    # print_input_file(models)


def read_model(absolute_url):
    r = requests.get('https://www.specialized.com' + absolute_url)
    r.raise_for_status()
    soup = BeautifulSoup(r.text, 'html.parser')

    model_name = soup.find('h1', class_ = 'grid-header__heading').string.strip()

    print(model_name)

    version_a_tags = soup('a', class_ = 'colorway-tile__anchor-link')  # <a> tags
    version_hrefs = [x['href'] for x in version_a_tags]  # change <a> tags to hrefs
    # version_hrefs = list(set(version_hrefs))  # remove duplicates
    version_hrefs = remove_duplicates_preserve_order(version_hrefs)

    versions = []

    for url in version_hrefs:
        if 'frameset' not in url and 'module' not in url and 'sbuild' not in url:
            # pprint(read_version(r.url + url))
            versions.append(read_version(r.url + url))
            # exit()

    print()

    return {'name': model_name, 'versions': versions}


def read_version(url):
    r = requests.get(url)
    r.raise_for_status()
    soup = BeautifulSoup(r.text, 'html.parser')

    for h2 in soup('h2', class_ = 'pdp-hero__heading'):
        print(indent + h2.string)

    struct = {
        'version': soup.find('h2', class_ = 'pdp-hero__heading').string,
        'price': get_price(soup),
        'frame': get_spec(soup, "frame"),
        'fork': get_spec(soup, "fork"),
        'der_front': get_spec(soup, "front derailleur"),
        'der_rear': get_spec(soup, "rear derailleur")
    }

    for key, value in struct.items():
        struct[key] = str(struct[key])

    return struct


def get_spec(soup, spec_name):
    try:
        return soup.find('h2', string = spec_name.upper()).parent.next_sibling.next_sibling.p.string
    except AttributeError:
        return "[{} not found]".format(spec_name)


def get_price(soup):
    price_data = soup.find('div', class_ = 'js-select-container')['data-available-params']

    replace = ['&quot;', '&quoquot;', '&ququot;']
    for target in replace:
        price_data = price_data.replace(target, '\"')

    price_data = price_data.replace('quot;', '')

    the_json = json.loads(price_data)

    sizes = get_first_dict_item(the_json)['sizes']
    first_size = get_first_dict_item(sizes)

    return first_size['price']


def get_first_dict_item(d):
    return d[list(d)[0]]


def print_input_file(models):
    with open('output.txt', 'w+') as f:
        for model in models:
            f.write(model['name'])


# with open('models.pickle', 'rb') as f:
#     print_input_file(pickle.load(f))

main()
# read_model('/us/en/bikes/road/amira')
