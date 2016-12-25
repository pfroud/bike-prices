# Bicycle price infographic generator

Visualizes price and performance of road bicycles.

<p align="center" style="text-align: center">
<img src="sample_output/sample_output.png?raw=true" alt="Bike price infographic">
</p>


## Background

In May 2015, I was interested in buying a road bike but didn't know very much about them.

Here's the problem:  there are many bicycle brands. Each brand makes many models. Each model has many versions.  That multiplies to a lot of bikes to choose from. There are currently 190 versions in the project.

### Terminology

A bicycle **brand** is self explanatory. Some brands are Cannondale, Giant, Specialized, and Trek.

A **model** is something. Here are some models from each of the brands I mentioned:

| Cannondale | Giant | Specialized | Trek |
|-----------------------------------|------------------------------|----------------------------|----------------------------|
| <ul> <li>CAAD8</li> <li>CAAD12</li> <li>SuperSix EVO</li> <li>SuperSix EVO Hi-Mod</li> <li>Synapse</li> <li>Synapse Carbon</li> </ul> | <ul> <li>Defy</li> <li>Defy Disc</li> <li>Defy Advanced</li> <li>Defy Advanced Pro</li> <li>Defy Advanced SL</li> <li>TCR Advanced</li> <li>TCR Advanced Pro</li> <li>TCR Advanced SL</li> <li>Propel Advanced</li> <li>Propel Advanced Pro</li> <li>Propel Advanced SL</li> </ul> | <ul> <li>Allez</li> <li>Amira</li> <li>Diverge</li> <li>Dolce</li> <li>Roubaix</li> <li>Ruby</li> <li>Tarmac</li> <li>Venge</li>  </ul> | <ul> <li>Emonda</li> <li>Emonda ALR</li> <li>Madone</li> <li>1 Series</li> <li>Domane</li> </ul> |

Let's get an idea how many options there are.


### Preliminary questions

In the first version, I manually entered data from four bike brands (Specialized, Cannondale, Trek, Giant) with only price

Specifically, I wanted to learn:

* do brands have different pricing styles?
* are versions clustered by price or spread out?
* what is the price range of different models?

**is it better to get an expensive version of a cheap model, or a cheap version of an expensive model?**


### Early versions

I figured it was easier to make a shitty proof-of-concept program to make the diagram I wanted than it was to find and learn a data viz library. I made the first chart to see if the style I had in mind would be useful. It was, and I have been developing the project since then.

I have still not been able to find a data viz library to this this for me, mostly because I'm not sure what to call this kind of chart. I did do this though: https://public.tableau.com/profile/peter.froud#!/vizhome/roadBikes/prices [old data!!!]

It turned out I didn't have enough information to come close to answer for the last question. I had thought of adding a variable for groupset (the drivetrain components) but it would be a lot of work.

It also turns out that this question cannot be answered by a diagram.

## Expansion 

I soon expanded to another variable for material to try to answer:

- how does the amount of carbon fiber affects a model/version's price? (it's really expensive)
- is there an obvious best value? (no)

The holy grail was to determine whether, at a given price point, 

###Expansion to material

I soon expanded to another variable for material to try to answer:

how does the amount of carbon fiber affects a model/version's price? (it's really expensive)
is there an obvious best value? (no)


### Expansion to groupset

I was offered a chance to present at Gunn High School's Engineering Night, and decided to bring the bike diagram to full fruitition ith groupset information. 



## Implementation

### Scraping data

### Drawing diagram

## Results

Answers to those questions are still the same, so I'll show results using current bike data and diagram.

**Do brands have different pricing styles?**
No.

**Are versions clustered by price or spread out?**
Spread out.

**What is the price range of different models?**
Mostly very big.
