/**
 * Scrapes information about Trek bikes.
 *
 * Instructions:
 *
 * (1) In a browser, open http://www.trekbikes.com/us/en_US/bikes/road-bikes/performance-road/c/B260.
 * (2) Paste this file into browser's javascript console and run it.
 * (3) The page will be replaced with a list of links for each model. Open all of them.
 * (4) On each page with a bike model, run trek_versions.js.
 * (5) After running trek_versions.js each time, the page you ran this script on will be updated.
 */


"use strict";

localStorage.clear();

var linkElements = $$("a.product-tile__link"), versionId, currentLink;
for (var i = 0; i < linkElements.length; i++) {
    currentLink = linkElements[i].href;
    if (currentLink.includes("frameset")) continue; // skip frame-only versions
    versionId = currentLink.split("/").slice(-1)[0];
    document.write("<p id=\"" + versionId + "\"><a href=\"" + currentLink + "\">" + currentLink + "</a></p>");

}
document.write("<pre>");

window.addEventListener("storage", readNewData);

/**
 * Called when data about a bike version is written to localStorage by trek_versions.js.
 * Marks which URL the version is from, and appends the data to the page.
 *
 * @param event - The Event fired from storage listener.
 */
function readNewData(event) {
    var key = event.key;
    if (key == "__storejs__" || key == "__zlcstore") return; // Trek website uses storage for some other stuff
    $("p#" + key)[0].innerHTML += " - found!";

    var data = JSON.parse(event.newValue);
    for (var spec in data) {
        if (data.hasOwnProperty(spec)) { // http://stackoverflow.com/a/16735184
            document.write(spec + "\t" + data[spec] + "\n");
        }
    }
    document.write("\n\n");
}