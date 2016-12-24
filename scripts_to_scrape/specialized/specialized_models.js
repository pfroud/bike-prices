/**
 * Scrapes information about Specialized bikes.
 *
 * Instructions:
 *
 * (1) In a browser, open a page on Specialized.com with a list of bike models. (See readme.md.)
 * (2) Paste this file into browser's javascript console and run it.
 * (3) The page will be replaced with a list of links for each model. Open all of them.
 * (4) On each page with a bike model, run specialized_versions.js.
 * (5) After running specialized_versions.js each time, the page you ran this script on will be updated.
 */

"use strict";

localStorage.clear();

var links = $$("a.colorway-tile__anchor-link"), versionId;

// A bunch of models are listed twice. It's really weird. This removed duplicates. Set() is from ECMAScript 6.
var hrefs = links.map(function (value, index, array) {return value.href});
hrefs = Array.from(new Set(hrefs));

hrefs.forEach(function (link) {
    if (!(link.includes("frameset") || link.includes("module") || link.includes("sbuild"))) {
        versionId = link.split("/").slice(-1)[0];
        document.write("<p id=\"" + versionId + "\"><a href=\"" + link + "\">" + link + "</a></p>");
    }

});


document.write("<pre>");

window.addEventListener("storage", readNewData);

/**
 * Called when data about a bike version is written to localStorage by cannondale_versions.js.
 * Marks which URL the version is from, and appends the data to the page.
 *
 * @param event - The Event fired from storage listener.
 */
function readNewData(event) {
    $("p#" + event.key)[0].innerHTML += " - found!";
    var data = JSON.parse(event.newValue); // http://stackoverflow.com/a/2010994
    for (var spec in data) {
        if (data.hasOwnProperty(spec)) { // http://stackoverflow.com/a/16735184
            document.write(spec + "\t" + data[spec] + "\n");
        }
    }
    document.write("\n\n");
}