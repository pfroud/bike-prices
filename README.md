
# Bicycle price infographic generator

Visualizes price and performance of road bicycles.

<p align="center" style="text-align: center">
<img src="sample_output/sample_output.png?raw=true" alt="Bike price infographic">
</p>


## Background

In May 2015, I was interested in buying a road bike but didn't know very much about them.

There are a lot of road bikes. I looked at four mainstream bicycle brands for this project, which each have a column in the table below. Each brand has several models, and each model has several versions. In total, there are 190 versions to consider.

| [Cannondale](http://www.cannondale.com/en/USA) | [Giant](https://www.giant-bicycles.com/us/) | [Specialized](https://www.specialized.com/) | [Trek](http://www.trekbikes.com/us/en_US/) |
|------------|-------|-------------|------|
| <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Synapse Carbon](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=71fb0b57-8db4-4549-b9eb-b50f7cfd4ed0)</td><td>11</td></tr><tr><td>[Synapse](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=61013240-d2c5-418d-a97d-a844c8b5e456)</td><td>5</td></tr><tr><td>[CAAD12](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=185f8303-a384-4816-b3bf-9feb5f067c25)</td><td>7</td></tr><tr><td>CAAD8</td><td>4</td></tr><tr><td>[SuperSix EVO](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=a31f8962-635b-47e1-adb8-efb64487b587)</td><td>10</td></tr></table> | <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Propel Advanced SL](https://www.giant-bicycles.com/us/bikes-propel-advanced-sl)</td><td>4</td></tr><tr><td>[Propel Advanced Pro](https://www.giant-bicycles.com/us/bikes-propel-advanced-pro)</td><td>1</td></tr><tr><td>[Propel Advanced](https://www.giant-bicycles.com/us/bikes-propel-advanced)</td><td>3</td></tr><tr><td>[TCR Advanced SL](https://www.giant-bicycles.com/us/bikes-tcr-advanced-sl)</td><td>3</td></tr><tr><td>[TCR Advanced Pro](https://www.giant-bicycles.com/us/bikes-tcr-advanced-pro)</td><td>2</td></tr><tr><td>[TCR Advanced](https://www.giant-bicycles.com/us/bikes-tcr-advanced)</td><td>3</td></tr><tr><td>[Defy Advanced SL](https://www.giant-bicycles.com/us/bikes-defy-advanced-sl)</td><td>2</td></tr><tr><td>[Defy Advanced Pro](https://www.giant-bicycles.com/us/bikes-defy-advanced-pro)</td><td>2</td></tr><tr><td>[Defy Advanced](https://www.giant-bicycles.com/us/bikes-defy-advanced)</td><td>3</td></tr><tr><td>Defy Disc</td><td>2</td></tr><tr><td>Defy</td><td>2</td></tr></table> | <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Tarmac](https://www.specialized.com/us/en/men/bikes/road/performance/tarmac)</td><td>13</td></tr><tr><td>[Ruby](https://www.specialized.com/us/en/women/bikes/road/performance/ruby)</td><td>10</td></tr><tr><td>[Diverge](https://www.specialized.com/us/en/men/bikes/road/adventure/diverge)</td><td>10</td></tr><tr><td>[Dolce](https://www.specialized.com/us/en/women/bikes/road/performance/dolce)</td><td>7</td></tr><tr><td>[Roubaix](https://www.specialized.com/us/en/men/bikes/road/performance/roubaix)</td><td>13</td></tr><tr><td>[Allez](https://www.specialized.com/us/en/men/bikes/road/performance/allez)</td><td>9</td></tr><tr><td>[Venge](https://www.specialized.com/us/en/men/bikes/road/performance/venge)</td><td>5</td></tr><tr><td>[Amira](https://www.specialized.com/us/en/women/bikes/road/performance/amira)</td><td>7</td></tr></table> | <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Domane](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/domane/c/B221/)</td><td>19</td></tr><tr><td>[Lexa](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/lexa-womens/c/B223/)</td><td>4</td></tr><tr><td>[Slique](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/silque-womens/c/B222/)</td><td>6</td></tr><tr><td>[1 Series](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/1-series/c/B214/)</td><td>2</td></tr><tr><td>[Emonda ALR](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/%C3%A9monda-alr/c/B212/)</td><td>3</td></tr><tr><td>[Emonda](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/%C3%A9monda/c/B211/)</td><td>14</td></tr><tr><td>[Madone](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/madone/c/B213/)</td><td>4</td></tr></table> |

Notes -  I excluded track, cyclocross, and triathalon bicycles. Models without links have been discontinued or renamed. The number of models in the table is from whenever I last scraped data and may not be current.

### Variables and terminology

I use three specifications to give an idea of the bike's performance. 

(1) **Price:** the MSRP price given on their website.

(2) **Material:** describes which key parts of the bicycle are made from [carbon-fiber](https://en.wikipedia.org/wiki/Carbon_fiber_reinforced_polymer). The two parts in question are the [frame](https://en.wikipedia.org/wiki/Bicycle_frame#Carbon_fiber), colored blue below, and  the [fork](https://en.wikipedia.org/wiki/Bicycle_fork#Materials), colored green below:
 
<p align="center" style="text-align: center">
<img src="img/frame-fork.png?raw=true" alt="Diagram showing bicycle frame and fork" width="500px">
</p>

Carbon-fiber is lightweight, strong, corrosion-resistant, and can be molded into almost any shape, but is difficult to manufacture so expensive. Parts not made of carbon are most likely aluminum, which is still a very good material but not as cool.

This value of the material field is one of:

 * none - neither the frame nor fork are carbon-fiber
 * fork - the fork is carbon fiber, but the frame isn't
 * all - both the frame and fork are carbon-fiber

Notes - they don't make bikes with carbon frames but non-carbon forks.

(3) **[Groupset](https://en.wikipedia.org/wiki/Groupset):** the bicycle's drivetrain components, which come in a set. The parts shown below are most of a groupset:

<p align="center" style="text-align: center">
<img src="img/drivetrain.png?raw=true" alt="Parts of bicycle drivetrain" width="500px">
</p>

Importantly, a groupset also includes the brake/shift levers, which play a big role in the feel. More expensive groupsets are lightweight and have faster/smoother/nicer mechanisms.

Two companies make groupsets at this price level:

|[Shimano](http://bike.shimano.com/) | [SRAM](https://www.sram.com/sram/road) |
|---------|------|
[Dura-Ace](http://bike.shimano.com/content/sac-bike/en/home/components11/road/dura-ace-9100.html) ([Di2](http://bike.shimano.com/content/sac-bike/en/home/components11/road/dura-ace-9170-di2.html)) <br> [Ultegra](http://bike.shimano.com/content/sac-bike/en/home/components11/road/ultegra1.html) ([Di2](http://bike.shimano.com/content/sac-bike/en/home/components11/road/ultegra-di21.html)) <br> [105](http://bike.shimano.com/content/sac-bike/en/home/components11/road/1051.html) <br> [Tiagra](http://bike.shimano.com/content/sac-bike/en/home/components11/road/Tiagra4700.html) <br> [Sora](http://bike.shimano.com/content/sac-bike/en/home/components11/road/sora11.html) <br> [Claris](http://bike.shimano.com/content/sac-bike/en/home/components11/road/claris1.html) | [Red](https://www.sram.com/sram/road/family/sram-red) ([eTAP](https://www.sram.com/sram/road/family/sram-red-etap))<br>[Force](https://www.sram.com/sram/road/family/sram-force) <br> [Rival](https://www.sram.com/sram/road/family/sram-rival) <br> [Apex](https://www.sram.com/sram/road/family/sram-apex) |

Comparing groupset hierarchy *between brands* is inexact, but it's not much of a problem because most of the bikes use Shimano. In the current diagram, Sram groupsets are displayed as their Shimano approximation.

Notes - Di2 and eTAP versions are electronic, which shift using batteries, motors, and wired and/or wireless signals. Di2 stands for Digital Integrated Intelligence.
[Campagnolo](http://www.campagnolo.com/US/en/) makes more expensive groupsets.

### Preliminary questions

I made the first chart just to see and visually compare all my options at once.

Later, I hoped to answer some specific questions:

* do different brands have different pricing styles?
* few models with versions spread far apart, or many models spread far apart, each with few versions?
* is there a bike at almost every price point? jumps? discontinuities? a continuum?
* what are the price tradeoffs between carbon and groupset?
* are there any obvious best values?

Answers are discussed below.

## Implementation

### Scraping data


I made JavaScript web scrapers to help gather  bike specs; read about them in folder [`scripts_to_scrape/`](scripts_to_scrape). The resulting data are in  [`bikesInput.txt`](bikesInput.txt).

### Drawing diagram

It would make a lot of sense to have a data visualization program do the drawing for me, but so far I haven't found a satisfactory way to replicate this kind of chart. I did make a version using Tableau Public, published [here](https://public.tableau.com/profile/peter.froud#!/vizhome/roadBikes/prices), but Tableau seems to be poorly suited for this application.

I made the first proof-of-concept chart only to see if this was a good idea. I used Java because it was installed on my computer, I knew how to use it, and it's easy to draw shapes with. The current diagram is drawn using Erich Seifert's [`VectorGraphics2D`](http://trac.erichseifert.de/vectorgraphics2d/) library.

To begin, the program reads a text file containing info about all the bikes, currently [`bikesInput.txt`](bikesInput.txt). Next, each model is represented by an instance of [`Bike`](src/Bike.java), which contains all the versions of the model. Enums for [`Groupset`](src/Groupset.java) and [`Carbon`](src/Carbon.java) are used to describe specs. Then, a [`Diagram`](src/Diagram.java)  draws all the bikes and a [`Legend`](src/Legend.java) shows what the symbols mean.

There is an ability to make [`Histogram`](src/Histogram.java)s, which would get added to the `Diagram` by adding an [`Analysis`](src/Analysis.java) object. Currently this is not added to the chart, but you can try it using [`AnalysisTesting`](src/AnalysisTesting.java).

## Results


I got a [2015 Giant Defy 1](https://www.giant-bicycles.com/us/defy-1-2015) on sale for $1,000, with a 105 groupset. The price before going on sale was $1,325 I think, but we cannot compare street and list prices. 
at [The Offramp](http://offrampbikes.com/) in Mountain View.

**Do different brands have different pricing styles?**
* Giant has many models (11) each with few versions (max 4), while the other brands have fewer  models (5, 7, and 8) with many models (max 19!!).
* Trek's bikes have enormous range within one model. The Domane goes from $1,360 to $11,000 in 19 models, and the Emonda goes from $1,570 to $12,080 in 14 models.


* is there a bike at almost every price point? jumps? discontinuities? a continuum?
* what are the price tradeoffs between carbon and groupset?

**Are there any obvious best values?**
No.


### Interesting bikes

Check out these 

* the third Trek Domane version downgrades from the 105 groupset to Sora, skipping Tiagra, when 
*  every Cannondale CAAD12 has a carbon fork only, even though you could get an all-carbon bike with the same groupset for less
* The jump in starting price between the Cannondale Synapse and Giant Defy Disc, where Claris and Sora are phased out

## Issues & future work

Obviously, a static pdf diagram is really shitty. A dynamic diagram should be able to zoom, and see details of a bike by clickign on its dot.


- Model versions with the same price are drawn on top of each other.
- When cost range override is on, colored bars go off the edge of the page.
- Handle relationship between Shimano and Sram groupsets better
- Make Groupset.java better
- Show the Di2 versions of groupset as the same groupset
- use a better color scale for the groupset?
- put the legend somewhere better so you don't need to manually move it when you change the zoom

classify by brand
get curret bike data, and compare over time


-----------------------

**Image credits** 

frame - derivative of [Bicycle Frame Diagram-en.svg](https://en.wikipedia.org/wiki/File:Bicycle_Frame_Diagram-en.svg) by [Keithonearth](https://commons.wikimedia.org/wiki/User:Keithonearth) on Wikimedia Commons, licensed under [CC BY 3.0](https://creativecommons.org/licenses/by/3.0/deed.en)

drivetrain - [Derailleur Bicycle Drivetrain.svg](https://commons.wikimedia.org/wiki/File:Derailleur_Bicycle_Drivetrain.svg) by [Keithonearth](https://commons.wikimedia.org/wiki/User:Keithonearth) on Wikimedia Commons, licensed under [CC BY-SA 3.0](https://creativecommons.org/licenses/by-sa/3.0/deed.en)

