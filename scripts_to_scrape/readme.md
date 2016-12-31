# scripts_to_scrape/

This folder contains web scrapers to collect data about bikes from their websites.

## Overview

Each brand lays out their website differently so each brand has its own scraper. For each website, this pattern is followed:

<!-- rename these files -->
1. go to a page which lists versions of a model
1. run `[brand]_models.js` to print links for that model
1. open all the links
1. on each page with a version, run `[brand]_versions.js` to extract info about the version and write it to [Storage](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API)
1.  return to the page with the links. all the versions and outputs it in a single comma-separated value table

So, the scraping is far from totally automated. I made one fully automated scraper, at [`specialized/python_version/`](specialized/python_version/). It was more work than I thought it would be.

## Classifying material and groupset

Currently, groupset and material classification must be done by hand. The scripts just return the entire description for a specification.

I wasn't sure brands would actually say "carbon-fiber" or "aluminum", or might say "composite" or "alloy" or something instead. This was not the case, and if/when I update the scripts, I will automate all the classification.

### Material

It would totally work to just look for the words "carbon" or "composite".

Sometimes the full descriptions of parts are humorously long&mdash;for example, the Trek Domane 6.2 Disc ($4,500) has [the following](https://github.com/pfroud/bike-prices/blob/master/scripts_to_scrape/trek/output/trek%20endurance.txt#L75) frame:

> 600 Series OCLV Carbon, IsoSpeed, Ride Tuned seatmast, Power Transfer Construction, disc balanced post mount, 142x12 Closed Convert dropouts, hidden fender mounts, E2 tapered head tube, BB90, performance cable routing, DuoTrap compatible, 3S chain keeper
> 

On the other extreme, Cannondale [specifies](https://github.com/pfroud/bike-prices/blob/master/scripts_to_scrape/cannondale/output/cannondale%20elite%20road.txt#L52) the CAAD8 (under $1,500) frames just as:

> Aluminum

### Groupset

I wasn't sure how many components I would need to see at to determine which groupset a bike really had. 

For example, a bike could have front and rear derailleurs from different groupsets. Or, the derailleurs could be the same but the shifters could be from something else. (It turns out this never happened!)

I scraped Specialized first and pulled data about basically everything: frame, fork, shifters, front and rear brakes, crankset, chain, and front and rear derailleur. See [Specialized  Ruby](https://github.com/pfroud/bike-prices/blob/master/scripts_to_scrape/specialized/output/Ruby.txt) for example.

For later brands I just pulled front and rear derailleurs, but even this was unnecessary. This works:

```js
re = /(dura-?ace|ultegra|105|tiagra|sora|claris|red|force|rival|apex)/i
groupset = re.exec(string)[0]
```
(with slightly more needed for Di2 and eTAP.)


## How many bikes?

<!-- Github formats markdown tables poorly so I'm using html -->
<table><tr>
    <th>Brand</th>
    <th>Count</th>
  </tr><tr>
    <td>Cannondale</td>
    <td>37</td>
  </tr><tr>
    <td>Gaint</td>
    <td>27</td>
  </tr><tr>
    <td>Specialized</td>
    <td>74</td>
  </tr><tr>
    <td>Trek</td>
    <td>52</td>
  </tr><tr>
    <td>Total</td>
    <td>190</td>
</tr></table>
