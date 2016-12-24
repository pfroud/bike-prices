## How to scrape Giant bikes

1. In a browser, open [this page](https://www.giant-bicycles.com/en-us/bike-catalogue/series-for-block/?block_id=7&level=performance).
1. Paste [`giant_models.js`](giant_models.js) into the browser's javascript console and run it.
1. The page will be replaced with a list of links for each model. Open all of them.
1. On each page with a bike model, run [`giant_versions.js`](giant_versions.js).
1. After running `giant_versions.js` each time, the page with the list of links will be updated.