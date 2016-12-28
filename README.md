
# Bicycle price infographic generator

Visualizes price and performance of road bicycles.

<p align="center" style="text-align: center">
<img src="sample_output/sample_output.png?raw=true" alt="Bike price infographic">
</p>


## Background

In May 2015, I was interested in buying a road bike but didn't know very much about them.

There are a lot of road bikes. In this project, I look at four well-known bicycle brands, each which get a column in the table below. Each brand has several models, and each model has several versions. In total, there are 190 versions to consider.

| [Cannondale](http://www.cannondale.com/en/USA) | [Giant](https://www.giant-bicycles.com/us/) | [Specialized](https://www.specialized.com/) | [Trek](http://www.trekbikes.com/us/en_US/) |
|------------|-------|-------------|------|
| <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Synapse Carbon](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=71fb0b57-8db4-4549-b9eb-b50f7cfd4ed0)</td><td>11</td></tr><tr><td>[Synapse](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=61013240-d2c5-418d-a97d-a844c8b5e456)</td><td>5</td></tr><tr><td>[CAAD12](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=185f8303-a384-4816-b3bf-9feb5f067c25)</td><td>7</td></tr><tr><td>CAAD8</td><td>4</td></tr><tr><td>[SuperSix EVO](http://www.cannondale.com/en/USA/Products/ProductCategory.aspx?nid=a31f8962-635b-47e1-adb8-efb64487b587)</td><td>10</td></tr></table> | <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Propel Advanced SL](https://www.giant-bicycles.com/us/bikes-propel-advanced-sl)</td><td>4</td></tr><tr><td>[Propel Advanced Pro](https://www.giant-bicycles.com/us/bikes-propel-advanced-pro)</td><td>1</td></tr><tr><td>[Propel Advanced](https://www.giant-bicycles.com/us/bikes-propel-advanced)</td><td>3</td></tr><tr><td>[TCR Advanced SL](https://www.giant-bicycles.com/us/bikes-tcr-advanced-sl)</td><td>3</td></tr><tr><td>[TCR Advanced Pro](https://www.giant-bicycles.com/us/bikes-tcr-advanced-pro)</td><td>2</td></tr><tr><td>[TCR Advanced](https://www.giant-bicycles.com/us/bikes-tcr-advanced)</td><td>3</td></tr><tr><td>[Defy Advanced SL](https://www.giant-bicycles.com/us/bikes-defy-advanced-sl)</td><td>2</td></tr><tr><td>[Defy Advanced Pro](https://www.giant-bicycles.com/us/bikes-defy-advanced-pro)</td><td>2</td></tr><tr><td>[Defy Advanced](https://www.giant-bicycles.com/us/bikes-defy-advanced)</td><td>3</td></tr><tr><td>Defy Disc</td><td>2</td></tr><tr><td>Defy</td><td>2</td></tr></table> | <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Tarmac](https://www.specialized.com/us/en/men/bikes/road/performance/tarmac)</td><td>13</td></tr><tr><td>[Ruby](https://www.specialized.com/us/en/women/bikes/road/performance/ruby)</td><td>10</td></tr><tr><td>[Diverge](https://www.specialized.com/us/en/men/bikes/road/adventure/diverge)</td><td>10</td></tr><tr><td>[Dolce](https://www.specialized.com/us/en/women/bikes/road/performance/dolce)</td><td>7</td></tr><tr><td>[Roubaix](https://www.specialized.com/us/en/men/bikes/road/performance/roubaix)</td><td>13</td></tr><tr><td>[Allez](https://www.specialized.com/us/en/men/bikes/road/performance/allez)</td><td>9</td></tr><tr><td>[Venge](https://www.specialized.com/us/en/men/bikes/road/performance/venge)</td><td>5</td></tr><tr><td>[Amira](https://www.specialized.com/us/en/women/bikes/road/performance/amira)</td><td>7</td></tr></table> | <table><tr><th>Model</th><th>Versions</th></tr><tr><td>[Domane](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/domane/c/B221/)</td><td>19</td></tr><tr><td>[Lexa](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/lexa-womens/c/B223/)</td><td>4</td></tr><tr><td>[Slique](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/silque-womens/c/B222/)</td><td>6</td></tr><tr><td>[1 Series](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/1-series/c/B214/)</td><td>2</td></tr><tr><td>[Emonda ALR](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/%C3%A9monda-alr/c/B212/)</td><td>3</td></tr><tr><td>[Emonda](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/%C3%A9monda/c/B211/)</td><td>14</td></tr><tr><td>[Madone](http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/madone/c/B213/)</td><td>4</td></tr></table> |

Models without links have been discontinued or renamed. The number of models in the table is from when I last scraped data and may not be current. I excluded track, cyclocross, and triathalon bicycles.

### Variables and terminology

 Each version has three variables. 

(1) **Price:** the MSRP price as stated on their website. Hopefully, the difference between list and street price is the same across brands.

(2) **Material:** which key parts of the bicycle are made from [carbon-fiber](https://en.wikipedia.org/wiki/Carbon_fiber_reinforced_polymer). Carbon-fiber is lightweight and stiff but difficult to manufacture so expensive. We're only concerned with the [*frame*](https://en.wikipedia.org/wiki/Bicycle_frame#Carbon_fiber), colored blue below, and  the [*fork*](https://en.wikipedia.org/wiki/Bicycle_fork#Materials), colored green.
 
<p align="center" style="text-align: center">
<img src="img/frame-fork.png?raw=true" alt="Bicycle frame and fork" width="400px">
</p>

This value of the material is one of:

 * none - neither the frame nor fork are carbon-fiber
 * fork - the fork is carbon fiber, but the frame isn't
 * all - both the frame and fork are carbon-fiber

They don't make bikes with carbon frames but not carbon forks.
If something isn't carbon-fiber, it's probably made of aluminum. 

(3) **[Groupset](https://en.wikipedia.org/wiki/Groupset).**  Drivetrain components comes in sets

<p align="center" style="text-align: center">
<img src="img/drivetrain.png?raw=true" alt="Bicycle drivetrain" width="400px">
</p>



|   | Shimano | SRAM |
|---|---------|------|
| race<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>enthusiast<br>&nbsp;<br>&nbsp;<br>entry-level | Dura-Ace Di2 <br> Dura-Ace <br> Ultegra Di2 <br> Ultegra <br> 105 <br> Tiagra <br> Sora <br> Claris | &nbsp;<br>Red<br>&nbsp;<br>Force <br> Rival <br>&nbsp;<br> Apex<br>&nbsp; |


### Preliminary questions

I was overwhelmed with options and wanted to be able to see all options at once and visually compare them.

I created the very first version of this chart to explore one question:

>**Is it better to get an expensive version of a cheap model, or a cheap version of an expensive model?**

It turns out this question cannot be answered by a chart.

However, I did gain insight into specific questions.

* do different brands have different pricing styles?
* few models with versions spread far apart, or many models spread far apart, each with few versions?
* is there a bike at almost every price point? jumps? discontinuities? a continuum?
* what are the price tradeoffs between carbon and groupset?
* are there any obvious best values?


## Implementation

### Scraping data

### Drawing diagram

I figured it was easier to make a shitty proof-of-concept program to make the diagram I wanted than it was to find and learn a data viz library. I made the first chart to see if the style I had in mind would be useful. It was, and I have been developing the project since then.

I have still not been able to find a data viz library to this this for me, mostly because I'm not sure what to call this kind of chart. I did do this though: https://public.tableau.com/profile/peter.froud#!/vizhome/roadBikes/prices [old data!!!]


## Results

