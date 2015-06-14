# Bicycle cost infographic generator

Reads plaintext bicycle cost information and generates a PDF showing cost range and distribution.

## Motivation

I'm considering purchasing a road bicycle.

There are several bicycle manufacturers. Each manufacturer makes several models. Each model has several versions.

I wanted to visualize:

- what are the ranges in costs?
- what are the distribution / gradiation in costs?
- are versions clustered in price?
- how does material (amount of carbon fiber) affect price?

More generally, I seek to answer whether given a price point,
**is it better to get an expensive version of a cheap model, or a cheap version of an expensive model?**
(I'm not sure if this project will help answer this.)

I wrote this in Java because it's already installed and I already know how to use it.
It would make several hundred times more sense to use a data visualization library to do this,
but I started with this proof-of-concept.  


## Implementation


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