## How to scrape Cannondale bikes

1. In a browser, open a page on Cannondale.com with a list of bike models. (See readme.md.)
* [Elite road](http://www.cannondale.com/en/USA/Products/ProductGrid?Id=c773876c-7f1c-41d9-9e1c-e45bf6a82abf)
* [Endurance road](http://www.cannondale.com/en/USA/Products/ProductGrid?Id=7799a4b9-073f-4fa9-a508-c840eb4c6bcc)
1. Repeatedly scroll to the bottom of the page to load all models.
1. Paste [`cannondale_models.js`](cannondale_models.js) into the browser's javascript console and run it.
1. The page will be replaced with a list of links for each model. Open all of them.
1. On each page with a bike model, run [`cannondale_versions.js`](cannondale_versions.js).
1. After running `cannondale_versions.js` each time, the page with the list of links will be updated.