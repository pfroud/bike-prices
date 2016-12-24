A fully automatic Python version of the Javascript scraper.

Uses [Requests](http://docs.python-requests.org/en/master/) and [Beautiful Soup](https://www.crummy.com/software/BeautifulSoup/).

Other files:

* `partial_output.txt`: output from the python script, in the format used for input to the main Java program. Only has name and price because the material and groupset need manual designation.
* `models.json`: the data from scraping, serialized so you don't have to scrape it again.
* `scraping_log.txt`: console output printed when scraping, showing all the bikes read.