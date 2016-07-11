/**
 * Scrapes information about Cannondale bikes.
 *
 * Instructions:
 *
 * (1) In a browser, open a page on Cannondale.com with a list of bike models. (See readme.md.)
 * (2) Repeatedly scroll to the bottom of the page to load all models.
 * (3) Paste this file into browser's javascript console and run it.
 * (4) The page will be replaced with a list of links for each model. Open all of them.
 * (5) On each page with a bike model, run cannondale_versions.js.
 * (6) After running cannondale_versions.js each time, the page you ran this script on will be updated.
 */

"use strict";

localStorage.clear();
console.warn("This version does not distinguish between models SuperSix EVO and SuperSix EVO Hi-Mod");

var linkElements = $$("a.relatedProducts"), versionId, currentLink;
for (var i = 0; i < linkElements.length; i++) {
    currentLink = linkElements[i].href;
    versionId = currentLink.split("=")[1];
    document.write("<p id=\"" + versionId + "\"><a href=\"" + currentLink + "\">" + currentLink + "</a></p>");
}
document.write("<pre>");


window.addEventListener("storage", readNewData);

/**
 * Called when data about a bike version is written to localStorage by cannondale_versions.js.
 * Marks which URL the version is from, and appends the data to the page.
 *
 * @param {Event} event - The Event fired from storage listener.
 */
function readNewData(event) {
    /*
     Using $$() here gives a ReferenceError for some reason. Also, $() returns an array even through is shouldn't.
     https://developer.chrome.com/devtools/docs/commandline-api#selector
     */
    $("p#" + event.key)[0].innerHTML += " - found!";

    var data = JSON.parse(event.newValue);
    for (var spec in data) {
        if (data.hasOwnProperty(spec)) { //http://stackoverflow.com/a/16735184
            document.write(spec + "\t" + data[spec] + "\n");
        }
    }
    document.write("\n\n");
}