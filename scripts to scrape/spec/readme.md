## How to scrape Specialized bikes

1. In a browser, open the pages for these models:
* [Tarmac](https://www.specialized.com/bikes/road/tarmac)
* [Amira](https://www.specialized.com/bikes/road/amira)
* [Venge](https://www.specialized.com/bikes/road/venge)
* [Roubaix](https://www.specialized.com/bikes/road/roubaix)
* [Ruby](https://www.specialized.com/bikes/road/ruby)
* [Diverge](https://www.specialized.com/bikes/road/diverge)
* [Dolce](https://www.specialized.com/bikes/road/dolce)
* [Allez](https://www.specialized.com/bikes/road/allez)
Note the  [Shiv](https://www.specialized.com/bikes/triathlon/shiv) is excluded because it's a triathlon bike and the
[Langster](https://www.specialized.com/bikes/road/langster) is excluded because it's a track bike.
1. On each page, paste [`specialized_models.js`](specialized_models.js) into the browser's javascript console and run it.
1. The page will be replaced with a list of links for each model. Open all of them.
1. On each page with a bike model, run [`specialized_versions.js`](specialized_versions.js).
1. After running `specialized_versions.js` each time, the page with the list of links will be updated.