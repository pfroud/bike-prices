/**
 * Gathers data about a Cannondale bike version from its page on the Cannondale website,
 * and writes the data to localStorage.
 *
 * Use with cannondale_models.js.
 */

"use strict";


var specsInOverview = $$("div.textBox-sm");

/**
 * Gets a bike specification from the Component Overview section.
 *
 * @param {string} specName - Case-sensitive name of the spec to read.
 * @returns {string} The value of the spec.
 */
function getSpecFromOverview(specName) {
    var currentSpec;
    for (var i = 0; i < specsInOverview.length; i++) {
        currentSpec = specsInOverview[i];
        if (currentSpec.firstElementChild.innerHTML == specName) {
            return currentSpec.firstElementChild.nextElementSibling.innerHTML.trim();
        }
    }
    return "[" + specName + " not found!]";
}


var specsInAll = $$("div.cell h4");

/**
 * Gets a bike specification from the View All Components dropdown.
 *
 * @param {string} specName - Case-sensitive name of the spec to read.
 * @returns {string} The value of the spec.
 */
function getSpecFromAll(specName) {
    var currentSpec;
    for (var i = 0; i < specsInAll.length; i++) {
        currentSpec = specsInAll[i];
        if (currentSpec.innerHTML == specName) {
            return currentSpec.nextElementSibling.innerHTML.trim();
        }
    }
    return "[" + specName + " not found!]";
}


var data = {
    version: $$("h1.txtBlu")[0].innerHTML.trim(),
    price: $$("li.price")[0].innerHTML,
    frame: getSpecFromOverview("Frame"),
    fork: getSpecFromAll("Fork"),
    drivetrain: getSpecFromOverview("DriveTrain")
};

var versionId = window.location.href.split("=")[1];
localStorage.setItem(versionId, JSON.stringify(data));