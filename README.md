# Bicycle price infographic generator

Visualizes price and performance of road bicycles.

<p align="center" style="text-align: center">
<img src="sample_output/sample_output.png?raw=true" alt="Bike price infographic">
</p>

**This readme is not done yet.**

In September 2015, I purchased a [Giant Defy 1](http://www.giant-bicycles.com/en-us/bikes/model/defy.1/18733/76108/) on sale for $1,000. The frame is aluminum with a 105 groupset and is lighter than a low-end carbon bike. I didn't include Giant in my graphic because they don't follow the same model/version patterns, but if I had, it would not have contributed to my choice.

## Motivation

In May 2015, I was interested in a road bike but didn't have a sense for the market. 

There are many bicycle brands. Each brand makes many models. Each model has many versions.  That multiplies to a lot of bikes to choose from: the are currently 190 versions in the project.

I figured it was easier to make a shitty proof-of-concept program to make the diagram I wanted than it was to find and learn a data viz library. I made the first chart to see if the style I had in mind would be useful. It was, and I have been developing the project since then.

I have still not been able to find a data viz library to this this for me, mostly because I'm not sure what to call this kind of chart. I did do this though: https://public.tableau.com/profile/peter.froud#!/vizhome/roadBikes/prices [old data!!!]

It turned out I didn't have enough information to come close to answer for the last question. I had thought of adding a variable for groupset (the drivetrain components) but it would be a lot of work.

It also turns out that this question cannot be answered by a diagram.

## Preliminary questions

In the first version, I manually entered data from four bike brands (Specialized, Cannondale, Trek, Giant) with only price

Specifically, I wanted to learn:

* do brands have different pricing styles?
* are versions clustered by price or spread out?
* what is the price range of different models?

### Results

Answers to those questions are still the same, so I'll show results using current bike data and diagram.

**Do brands have different pricing styles?**
No.

**Are versions clustered by price or spread out?**
Spread out.

**What is the price range of different models?**
Mostly very big.


## Expansion to material

I soon expanded to another variable for material to try to answer:

- how does the amount of carbon fiber affects a model/version's price? (it's really expensive)
- is there an obvious best value? (no)

The holy grail was to determine whether, at a given price point, **is it better to get an expensive version of a cheap model, or a cheap version of an expensive model?**

### Results




## Expansion to groupset

I was offered a chance to present at Gunn High School's Engineering Night, and decided to bring the bike diagram to full fruitition ith groupset information. 


### Results

## Old content here

The overall price range of the bikes I sampled is from $770 (both Specialized Allez and Trek 1 Series) to $15,750 (Trek Emonda). The most expensive 1 Series version is $1100 (1.4x increase) while the most expensive Allez is a remarkable $8,000 (10.4x increase)


Through internet research, I learned that frame material has little to do with a bicycle's overall performance. While a major classification like frame material 

more important than frame: groupset

 with how "good" a bicycle is. A more meaningful view would include key components like groupset and wheels.

## Implementation

I wrote this in Java because it's already installed and I already know how to use it.
It would make several hundred times more sense to use a data visualization library to do this,
but I started with this proof-of-concept.  

A `Bike` object is a *model* of bike, and holds set of *versions* of the model. A model could be 'Specialized Diverge', and a version of that model could be 'Elite A1'.


All the Java code is in `src/`.


I'm using the [`VectorGraphics2D`](http://trac.erichseifert.de/vectorgraphics2d/) library to make a PDF. It's already in the repository.
 
The first version I made ran a `JApplet` because it required the least amount of tying and thought to get a graphics window going.

### Input

Currently called `bikesInput.txt`.

I assembled information by hand from the websites of these three bicycle manufacturers:

- [Specialized](http://www.specialized.com/us/en/home/)
- [Trek](http://www.trekbikes.com/us/en/)
- [Cannondale](http://www.cannondale.com/)

Thus prices are MSRP.

An input entry begins with a header, which has the name of the model, a space, and the number of versions for that model. For example, `Specialized_Diverge 7`.

Following the header are lines for version names, version costs, and version materials.

For example, if a model has 7 versions, there are seven lines with one name on each line, then seven lines with one cost on each line, etc.

One blank line separates input entries.


### Output

Check out `Sample output/`.

The page size is currently 74.81 inches by 37.01 inches, because fuck you. It's vector anyway.


## Issues & future work
- Model versions with the same price are drawn on top of each other.
- When cost range override is on, colored bars go off the edge of the page.

Obviously, a static pdf diagram is really shitty.
