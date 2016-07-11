/**
 * Scrapes information about Giant bikes.
 *
 * What I call a model, Giant calls a series; what I call a version, Giant calls a model.
 * File naming is unchanged for ease of use.
 *
 * Instructions:
 *
 * (1) In a browser, open
 *     https://www.giant-bicycles.com/en-us/bike-catalogue/series-for-block/?block_id=7&level=performance.
 * (2) Paste this file into browser's javascript console and run it.
 * (3) The page will be replaced with a list of links for each series. Open all of them.
 * (4) On each page with a bike series, run giant_versions.js.
 * (5) After running giant_versions.js each time, the page you ran this script on will be updated.
 */
"use strict";

localStorage.clear();

var linkElements = $$("article div.text a"), modelId, currentLink;
for (var i = 0; i < linkElements.length; i++) {
    currentLink = linkElements[i].href;
    modelId = currentLink.split("/").slice(-2, -1)[0];
    document.write("<p id=\"" + modelId + "\"><a href=\"" + currentLink + "\">" + currentLink + "</a></p>");
}
document.write("<pre>");


window.addEventListener("storage", readNewData);

/**
 *
 * @param event
 */
function readNewData(event) {
    var key = event.key;
    if (key == "modernizr") return; // artifact from Giant website
    $("p#" + key)[0].innerHTML += " - found!";

    var data = JSON.parse(event.newValue);

    var model;
    for (var i = 0; i < data.length; i++) {
        model = data[i];
        for (var spec in model) {
            if (model.hasOwnProperty(spec)) { //http://stackoverflow.com/a/16735184
                document.write(spec + "\t" + model[spec] + "\n");
            }
        }
        document.write("\n\n");
    }


}