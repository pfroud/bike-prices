
# Bicycle price infographic generator

Visualizes price and performance of road bicycles.

<p align="center" style="text-align: center">
<img src="sample_output/sample_output.png?raw=true" alt="Bike price infographic">
</p>


## Background

In May 2015, I was interested in buying a road bike but didn't know very much about them. I started this project to get an idea of the market.

There are a lot of road bikes. I looked at four mainstream bicycle brands for this project, which each have a column in the table below. Each brand has several models, and each model has several versions. In total, there are 190 versions.

| [Cannondale](http://www.cannondale.com/en/USA) | [Giant](https://www.giant-bicycles.com/us/) | [Specialized](https://www.specialized.com/) | [Trek](http://www.trekbikes.com/us/en_US/) |
|------------|-------|-------------|------|
| <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Synapse Carbon](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=71fb0b57-8db4-4549-b9eb-b50f7cfd4ed0)</td><td>11</td></tr><tr><td>[Synapse](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=61013240-d2c5-418d-a97d-a844c8b5e456)</td><td>5</td></tr><tr><td>[CAAD12](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=185f8303-a384-4816-b3bf-9feb5f067c25)</td><td>7</td></tr><tr><td>CAAD8</td><td>4</td></tr><tr><td>[SuperSix EVO](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=a31f8962-635b-47e1-adb8-efb64487b587)</td><td>10</td></tr></table> | <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Propel Advanced SL](https://www.giant-bicycles.com/us/bikes-propel-advanced-sl)</td><td>4</td></tr><tr><td>[Propel Advanced Pro](https://www.giant-bicycles.com/us/bikes-propel-advanced-pro)</td><td>1</td></tr><tr><td>[Propel Advanced](https://www.giant-bicycles.com/us/bikes-propel-advanced)</td><td>3</td></tr><tr><td>[TCR Advanced SL](https://www.giant-bicycles.com/us/bikes-tcr-advanced-sl)</td><td>3</td></tr><tr><td>[TCR Advanced Pro](https://www.giant-bicycles.com/us/bikes-tcr-advanced-pro)</td><td>2</td></tr><tr><td>[TCR Advanced](https://www.giant-bicycles.com/us/bikes-tcr-advanced)</td><td>3</td></tr><tr><td>[Defy Advanced SL](https://www.giant-bicycles.com/us/bikes-defy-advanced-sl)</td><td>2</td></tr><tr><td>[Defy Advanced Pro](https://www.giant-bicycles.com/us/bikes-defy-advanced-pro)</td><td>2</td></tr><tr><td>[Defy Advanced](https://www.giant-bicycles.com/us/bikes-defy-advanced)</td><td>3</td></tr><tr><td>Defy Disc</td><td>2</td></tr><tr><td>Defy</td><td>2</td></tr></table> | <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Tarmac](https://www.specialized.com/us/en/men/bikes/road/performance/tarmac)</td><td>13</td></tr><tr><td>[Ruby](https://www.specialized.com/us/en/women/bikes/road/performance/ruby)</td><td>10</td></tr><tr><td>[Diverge](https://www.specialized.com/us/en/men/bikes/road/adventure/diverge)</td><td>10</td></tr><tr><td>[Dolce](https://www.specialized.com/us/en/women/bikes/road/performance/dolce)</td><td>7</td></tr><tr><td>[Roubaix](https://www.specialized.com/us/en/men/bikes/road/performance/roubaix)</td><td>13</td></tr><tr><td>[Allez](https://www.specialized.com/us/en/men/bikes/road/performance/allez)</td><td>9</td></tr><tr><td>[Venge](https://www.specialized.com/us/en/men/bikes/road/performance/venge)</td><td>5</td></tr><tr><td>[Amira](https://www.specialized.com/us/en/women/bikes/road/performance/amira)</td><td>7</td></tr></table> | <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Domane](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/domane/c/B221/)</td><td>19</td></tr><tr><td>[Lexa](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/lexa-womens/c/B223/)</td><td>4</td></tr><tr><td>[Slique](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/silque-womens/c/B222/)</td><td>6</td></tr><tr><td>[1 Series](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/1-series/c/B214/)</td><td>2</td></tr><tr><td>[&Egrave;monda ALR](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/%C3%A9monda-alr/c/B212/)</td><td>3</td></tr><tr><td>[&Egrave;monda](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/%C3%A9monda/c/B211/)</td><td>14</td></tr><tr><td>[Madone](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/madone/c/B213/)</td><td>4</td></tr></table> |

Notes -  I excluded track, cyclocross, and triathlon bicycles. Models without links have been discontinued or renamed. The number of models in the table is from whenever I last scraped data and may not be current.


### Variables and terminology

I use three specifications to give an idea of the bike's performance.

(1) **Price:** the MSRP price given on their website.

(2) **Material:** describes which key parts of the bicycle are made from [carbon-fiber](https://en.wikipedia.org/wiki/Carbon_fiber_reinforced_polymer). The two parts in question are the [frame](https://en.wikipedia.org/wiki/Bicycle_frame#Carbon_fiber), colored blue below, and  the [fork](https://en.wikipedia.org/wiki/Bicycle_fork#Materials), colored green below:
 
<p align="center" style="text-align: center">
<img src="img/frame-fork.png?raw=true" alt="Diagram showing bicycle frame and fork" width="500px">
</p>

Carbon-fiber is lightweight, strong, corrosion-resistant, and can be molded into almost any shape, but is difficult to manufacture so expensive. On these bikes, parts not made of carbon are made of aluminum.

A carbon frame is probably, but not necessarily, better than an aluminum frame. See [Results &sect; Useful scope](#useful-scope) for more.

This value of the material field is one of:

 * none - neither the frame nor fork are carbon-fiber
 * fork - the fork is carbon fiber, but the frame isn't
 * all - both the frame and fork are carbon-fiber

Almost every bike I sampled has at least a carbon fork. They don't make bikes with carbon frames but non-carbon forks.

(3) **[Groupset](https://en.wikipedia.org/wiki/Groupset):** the bicycle's drivetrain components, which come in a set. The parts shown below are most of a groupset:

<p align="center" style="text-align: center">
<img src="img/drivetrain.png?raw=true" alt="Parts of bicycle drivetrain" width="500px">
</p>

Importantly, a groupset also includes the brake/shift levers. More expensive groupsets are lightweight and have faster/smoother/nicer mechanisms.

Two companies make groupsets at this price level, and each company makes multiple lines:

|[Shimano](http://bike.shimano.com/) | [SRAM](https://www.sram.com/sram/road) |
|---------|------|
[Dura-Ace](http://bike.shimano.com/content/sac-bike/en/home/components11/road/dura-ace-9100.html) ([Di2](http://bike.shimano.com/content/sac-bike/en/home/components11/road/dura-ace-9170-di2.html)) <br> [Ultegra](http://bike.shimano.com/content/sac-bike/en/home/components11/road/ultegra1.html) ([Di2](http://bike.shimano.com/content/sac-bike/en/home/components11/road/ultegra-di21.html)) <br> [105](http://bike.shimano.com/content/sac-bike/en/home/components11/road/1051.html) <br> [Tiagra](http://bike.shimano.com/content/sac-bike/en/home/components11/road/Tiagra4700.html) <br> [Sora](http://bike.shimano.com/content/sac-bike/en/home/components11/road/sora11.html) <br> [Claris](http://bike.shimano.com/content/sac-bike/en/home/components11/road/claris1.html) | [Red](https://www.sram.com/sram/road/family/sram-red) ([eTAP](https://www.sram.com/sram/road/family/sram-red-etap))<br>[Force](https://www.sram.com/sram/road/family/sram-force) <br> [Rival](https://www.sram.com/sram/road/family/sram-rival) <br> [Apex](https://www.sram.com/sram/road/family/sram-apex) |

Comparing groupset hierarchy *between brands* is inexact, but most of these bikes use Shimano so it's not a major problem. In the current diagram, Sram groupsets are displayed as their Shimano approximation.

Notes - Di2 and eTAP versions are electronic, which shift using batteries, motors, and wired and/or wireless signals. Di2 stands for Digital Integrated Intelligence.
[Campagnolo](http://www.campagnolo.com/US/en/) makes more expensive groupsets.

### Preliminary questions

The idea for making the first chart was to get a feel for what was out there: see all my options and visually compare everything.

Soon, I hoped to answer some specific questions:

* Do different brands have different pricing styles?
* What's price distribution like? Is there a bike at almost every price point? Or, are bike clustered around price points?
* Are there any obvious best values?

Answers are discussed in [Results &sect; Questions answered](#questions-answered).

I also was interested to learn if, in general, it is better to buy a cheap version of an expensive model or an expensive model of a cheap version. It turns out this question doesn't have an answer.

## Implementation

### Scraping data


I made JavaScript web scrapers to help gather  bike specs; read about them in folder [`scripts_to_scrape/`](scripts_to_scrape). The resulting data are in  [`bikesInput.txt`](bikesInput.txt).

### Drawing the diagram

It would make a lot of sense to have a visualization program do all the work for me. But, I haven't found anything that can replicate this kind of chart. I made a version using Tableau Public, published [here](https://public.tableau.com/profile/peter.froud#!/vizhome/roadBikes/prices), but it's pretty shitty.

Instead, I made the first proof-of-concept chart the hard way, just to see if this was a good idea. I used Java because it was installed on my computer, I knew how to use it, and it's easy to draw shapes with. Since then, the project has expanded and I'm still using Java. The current diagram is drawn using Erich Seifert's [`VectorGraphics2D`](http://trac.erichseifert.de/vectorgraphics2d/) library.

To draw a diagram:

1. Read a text file of bike data. The current file is [`bikesInput.txt`](bikesInput.txt).
1. Each entry becomes a [`Bike`](src/Bike.java) instance. Enums for [`Groupset`](src/Groupset.java) and [`Carbon`](src/Carbon.java) describe specs.
1. A [`Diagram`](src/Diagram.java) draws each bike and a [`Legend`](src/Legend.java) shows what the shapes and colors used mean.

You can add [`Histogram`](src/Histogram.java)s to the `Diagram` using an [`Analysis`](src/Analysis.java) object. Currently this is not added to the chart, but you can try it using [`AnalysisTesting`](src/AnalysisTesting.java).

## Results

### I got a bike

In September 2015 I got a [2015 Giant Defy 1](https://www.giant-bicycles.com/us/defy-1-2015) with a 105 groupset, carbon fork, and aluminum frame.

The MSRP is $1,425 which is comparable to bikes with the same specs. The price at [The Off Ramp](http://offrampbikes.com/) in Mountain View was $1,325 *and* it was on sale for $1,000, which was a great deal. But, I didn't use my chart to help choose bicycles. I just compared bikes in stores around me.

(The bike I got isn't show on the diagram because data is from the previous model year.)

### Useful scope

The three variables described in [Background &sect; Variables and terminology](#variables-and-terminology) do not fully describe how good a bike is.

In particular, frame material is less useful than I first thought, because a carbon frame can be heavier and less comfortable than an aluminum frame. And, features like wheel weight, internal cable routing, disc brakes, and others are not captured.

Here's a concrete example about frame type. The two most expensive aluminum-frame bikes are from the same model:

* [Cannondale CAAD12 Black Inc.](http://www.cannondale.com/en/USA/Bike/ProductDetail?Id=29556e81-b4cb-4d65-b232-f1c2514871bc) ($5,860)
* [Cannondale CAAD12 Disc Dura Ace](http://www.cannondale.com/en/USA/Bike/ProductDetail?Id=cce06ed1-bc31-48ed-a039-81d04316ac57) ($4,260)

CAAD stands for Cannondale Advanced Aluminum Design, so in fact [every Cannondale CAAD12](https://github.com/pfroud/bike-prices/blob/master/bikesInput.txt#L13-L17) has an aluminum frame&mdash;even at nearly $6,000. *Every* other bike at that price range is full-carbon. 

Does that mean Cannondale skimped on components? No. The [CAAD12 Red](http://www.cannondale.com/en/USA/Bike/ProductDetail?Id=e8b007ea-76a6-480c-b8e3-61589e0d69f9) ($3,200) is the cheapest bike to have a Dura-Ace/Red level groupset, *and* is the only aluminum-frame bike to have that level of groupset.

So, costs saved from manufacturing with aluminum instead of carbon-fiber can be transferred to high-grade components. Fixating on frame type when choosing a bike would be foolish.

### Questions answered

> Do different brands have different pricing styles?

Giant has many (11) models each with few versions (4 max), while the other brands have fewer (6.6 avg) models with many models (19 max).

Some Trek bikes have enormous range within the model. The Domane goes from $1,360 to $11,000 in 19 models, and the Emonda goes from $1,570 to $12,080 in 14 models.

Otherwise, pricing styles are similar. 


> What's price distribution like? Is there a bike at almost every price point? Or, are bike clustered around price points?

Spread out fairly continuously. Click for bigger:

<p align="center" style="text-align: center">
<img src="img/price-distribution.png?raw=true" alt="Distribution of bike prices">
</p>


> Are there any obvious best values?

No, but there is a worst value.

The most expensive bike with a Sora groupset is the [Trek Domane 4.0 Disc](http://archive.trekbikes.com/us/en/2015/Trek/domane_4_0_disc_compact), $2,100. The second most expensive bike with Sora is the [Specialized Dolce Sport Disc](https://www.specialized.com/us/en/dolce-sport-disc/107326), $1,150. That Domane is nearly twice as much as that Dolce, with the same (entry-level) groupset.

Furthermore, the Domane version one cheaper than the 4.0 Disc is the [2.3](http://archive.trekbikes.com/us/en/2015/Trek/domane_2_3_compact) ($1,680), which has a 105 groupset. That means if you spend $420 to upgrade from the Domane 2.3 to the Domane 4.0 Disc, you downgrade from 105, *skipping Tiagra,* and get Sora.

The Domane 4.0 Disc has a carbon-fiber frame,  which the Dolce and other Domane I just compared it to don't. But you can choose from *at least five other* bikes cheaper than the Domane 4.0 Disc which still have a 105 groupset and a full-carbon frame.

## Issues & future work

If or when I put more time into this, here's what I'll work on:

### Fixes

- Distinguish between Shimano and Sram groupsets, and represent groupsets better. Currently, [`Groupset.java`](src/Groupset.java) is pretty stupid.

- Instead of showing Di2 groupsets as their own groupset, show them as variants of the non-electronic version. Maybe with a little lightning bolt icon.

- Use a [better colormap](http://matplotlib.org/users/colormaps.html) for groupsets.
	- [How Bad Is Your Colormap? (Or, Why People Hate Jet – and You Should Too)](https://jakevdp.github.io/blog/2014/10/16/how-bad-is-your-colormap/) by Jake Vanderplas
	- [The 'jet' colormap must die! Or: how to improve your map plots and create your own nice colormaps](http://cresspahl.blogspot.com/2012/03/expanded-control-of-octaves-colormap.html) by xmhk

- Make `Analysis` useful.

### New features

- Find a better way to display stuff. A static PDF is really shitty. I would like to be able to zoom, sort, and filter the data, and see details of a bike by clicking on its dot.

- Get bike data for current model year, and compare over time.

- Scrape more bike brands.


## Aside &mdash; pronunciation


Trek [is from](https://en.wikipedia.org/wiki/Trek_Bicycle_Corporation) Wisconsin and Specialized [is from](https://en.wikipedia.org/wiki/Specialized_Bicycle_Components) California, but they like sounding European.

I've done research so you don't have to:


* Specialized **Roubaix** is [pronounced](https://youtu.be/jjNWIZ0RHT4?t=31s) roo-BAY and is named after the [Paris–Roubaix race](https://en.wikipedia.org/wiki/Paris%E2%80%93Roubaix).

* Trek **Madone** is [pronounced](https://youtu.be/Tsri7rkAbao?t=8s) muh-DOHN and is named after the Col de la Madone climb.<sup>[\[1\]](https://en.wikipedia.org/wiki/Trek_Bicycle_Corporation#1997.E2.80.932005_.E2.80.94_The_Armstrong_years_and_further_expansion)</sup>

* Trek **&Egrave;monda** is [pronounced](https://youtu.be/_7J4-G2QxX8?t=16s) eh-MON-da and is named after Italian *&egrave;monder*, [meaning](https://translate.google.com/#fr/en/%C3%A9monder) "prune" or "trim" (as in weight).<sup>[\[2\]](http://road.cc/content/news/122120-trek-launch-superlight-%C3%A9monda-road-bike)[\[3\]](http://www.velonews.com/2014/07/bikes-and-tech/first-ride-treks-featherweight-emonda_334248)</sup>

	* Shouldn't &Egrave;monda be pronounced EH-monda, with the spoken accent on the first syllable? Shouldn't the way Trek pronounced it be spelled Em&ograve;nda, with the grave accent on the O?  What the hell do I know.

* Trek **Domane** is [pronounced](https://youtu.be/sB8jJH-mumY?t=1m4s) do-MAH-nee. Allegedly, *domane* is Latin for "king's crown," <sup>[\[4\]](https://www.bikerumor.com/2012/03/30/trek-domane-road-bike-unveiled/)[\[5\]](http://road.cc/content/news/56064-trek-domane-launch-pave-busting-road-bike-with%E2%80%A6-suspension-video)</sup> but I think they made that up.<sup>[\[6\]](https://translate.google.com/#la/en/domane)[\[7\]](https://translate.google.com/#en/la/king%27s%20crown)</sup>


Those three Trek model names are anagrams.
