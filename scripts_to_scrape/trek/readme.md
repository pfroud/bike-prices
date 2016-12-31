## How to scrape Trek bikes

1. In a browser, open [this page](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/c/B260).
1. Paste [`trek_get_links.js`](trek_get_links.js) into the browser's javascript console and run it.
1. The page will be replaced with a list of links for each model. Open all of them.
1. On each page with a bike model, run [`trek_read_bike.js`](trek_read_bike.js).
1. After running `trek_read_bike.js` each time, the page with the list of links will be updated.