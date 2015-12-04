# Bicycle cost infographic generator

Reads plaintext bicycle cost information and generates a PDF showing cost range and distribution.

All the prices are from the 2015 model year and are now outdated.

Used more reasonable software to do this: https://public.tableau.com/profile/peter.froud#!/vizhome/roadBikes/prices

**Update:** in September 2015, I purchased a [Giant Defy 1](http://www.giant-bicycles.com/en-us/bikes/model/defy.1/18733/76108/) on sale for \$1,000. The frame is aluminum with a 105 groupset and is lighter than a low-end carbon bike. I didn't include Giant in my graphic because they don't follow the same model/version patterns, but if I had, it would not have contributed to my choice.


## Motivation

The original purpose was to visualize bicycle pricing, which would help me think about buying a road bicycle.

The generated diagrams are exactly what I had in mind, and do reveal some price patterns. However, they don't include enough information to be the only guideline in a decision to buy a bicycle.

###Problem

There are several bicycle *manufacturers*. Specialized, Trek, Cannondale, ...
Each manufacturer makes several *models*. Roubiax, Tarmac, Domane, Madone, CAAD10, Supersix, ...
Each model has several *versions*. Double, Triple, Comp, Elite, 2.0, 2.3, ...
That multiplies to a lot of bikes to choose from:


<!-- need to use html to center -->
<p align="center">
<a href="graph_for_readme/bikes-graph-sidebyside-orig.png?raw=true"><img src="graph_for_readme/bikes-graph-sidebyside-small.png"></a>
</p>

I want to a graphic representation of:

- price *range* for each model, and which versions are price outliers
- price *distribution*, i.e. whether versions are clustered by price or if there are significant price jumps
- how the amount of carbon fiber affects a model/version's price
- general sense of road bike price range, and how each manufacturer prices theirs


More generally, I seek to answer whether given a price point,
**is it better to get an expensive version of a cheap model, or a cheap version of an expensive model?**



### Results

#### Range

The overall price range of the bikes I sampled is from \$770 (both Specialized Allez and Trek 1 Series) to \$15,750 (Trek Emonda). The most expensive 1 Series version is $1100 (1.4x increase) while the most expensive Allez is a remarkable $8,000 (10.4x increase)


#### Distribution

#### Material

#### Discussion

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

## Input and output

### Input file

Currently called `bikesInput.txt`.

I assembled information by hand from the websites of these three bicycle manufacturers:

- [Specialized](http://www.specialized.com/us/en/home/)
- [Trek](http://www.trekbikes.com/us/en/)
- [Cannondale](http://www.cannondale.com/)

Thus prices are MSRP.

#### Format

An input entry begins with a header, which has the name of the model, a space, and the number of versions for that model. For example, `Specialized_Diverge 7`.

Following the header are lines for version names, version costs, and version materials.

For example, if a model has 7 versions, there are seven lines with one name on each line, then seven lines with one cost on each line, etc.

One blank line separates input entries.


### Output PDF

Check out `Sample output/`.

The page size is currently 74.81 inches by 37.01 inches.


## Known issues
- Model versions with the same price are drawn on top of each other.
- When cost range override is on, colored bars go off the edge of the page.
