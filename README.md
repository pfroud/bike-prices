# Bicycle price infographic generator

Visualizes price and performance of road bicycles.

<p align="center" style="text-align: center">
<img src="sample_output/sample_output.png?raw=true" alt="Bike price infographic">
</p>


## Background

In May 2015, I was interested in buying a road bike but didn't know very much about them.

There are a lot of road bikes. In this project, I look at four well-known bicycle brands, each which get a column in the table below. Each brand has several models, and each model has several versions. In total, there are 190 versions to consider.

| Cannondale | Giant | Specialized | Trek |
|------------|-------|-------------|------|
| <table><tr><td>Model</td><td>Versions</td></tr><tr><td>Synapse Carbon</td><td>11</td></tr><tr><td>Synapse</td><td>5</td></tr><tr><td>CAAD12</td><td>7</td></tr><tr><td>CAAD8</td><td>4</td></tr><tr><td>SuperSix EVO Hi-Mod</td><td>5</td></tr><tr><td>SuperSix EVO</td><td>5</td></tr></table> | <table><tr><td>Model</td><td>Versions</td></tr><tr><td>Propel Advanced SL</td><td>4</td></tr><tr><td>Propel Advanced Pro</td><td>1</td></tr><tr><td>Propel Advanced</td><td>3</td></tr><tr><td>TCR Advanced SL</td><td>3</td></tr><tr><td>TCR Advanced Pro</td><td>2</td></tr><tr><td>TCR Advanced</td><td>3</td></tr><tr><td>Defy Advanced SL</td><td>2</td></tr><tr><td>Defy Advanced Pro</td><td>2</td></tr><tr><td>Defy Advanced</td><td>3</td></tr><tr><td>Defy Disc</td><td>2</td></tr><tr><td>Defy</td><td>2</td></tr></table> | <table><tr><td>Model</td><td>Versions</td></tr><tr><td>Tarmac</td><td>13</td></tr><tr><td>Ruby</td><td>10</td></tr><tr><td>Diverge</td><td>10</td></tr><tr><td>Dolce</td><td>7</td></tr><tr><td>Roubaix</td><td>13</td></tr><tr><td>Alez</td><td>9</td></tr><tr><td>Venge</td><td>5</td></tr><tr><td>Amira</td><td>7</td></tr></table> | <table><tr><td>Model</td><td>Versions</td></tr><tr><td>Domane</td><td>19</td></tr><tr><td>Lexa</td><td>4</td></tr><tr><td>Slique</td><td>6</td></tr><tr><td>1 Series</td><td>2</td></tr><tr><td>Emonda ALR</td><td>3</td></tr><tr><td>Emonda</td><td>14</td></tr><tr><td>Madone</td><td>4</td></tr></table> |

### Variables and terminology

 Each version has three variables. 

**Price:** the MSRP price as stated on their website. MSRP isn't what you would actually pay in a bike shop, but hopefully the difference is the same across brands.

**Material:** whether the frame and/or fork are [carbon-fiber](https://en.wikipedia.org/wiki/Carbon_fiber_reinforced_polymer). Carbon is lightweight and stiff but expensive.

<p align="center" style="text-align: center">
<img src="img/frame-fork.png?raw=true" alt="Bicycle frame and fork" width="400px">
</p>

**[Groupset](https://en.wikipedia.org/wiki/Groupset).**  Drivetrain components comes in sets

<p align="center" style="text-align: center">
<img src="img/drivetrain.png?raw=true" alt="Bicycle drivetrain" width="400px">
</p>

| Shimano | SRAM |
|---------|------|
| Dura-Ace Di2 <br> Dura-Ace <br> Ultegra Di2 <br> Ultegra <br> 105 <br> Tiagra <br> Sora <br> Claris | Red <br> Force <br> Rival <br> Apex |


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

