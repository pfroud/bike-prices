/**
 * Gathers data about a Specialized bike version from its page on the Cannondale website,
 * and writes the data to localStorage.
 *
 * Use with specialized_models.js.
 */

"use strict";


var specTags = $$("h2.specs__subheading");

/**
 * Gets a bike specification from the View All Components dropdown.
 *
 * @param {string} specName - Case-sensitive name of the spec to read.
 * @returns {string} The value of the spec.
 */
function getSpec(specName) {
    var caps = specName.toUpperCase();

    specTags.forEach(function (spec) {
        if (spec.innerHTML == caps) {
            return spec.parentElement.nextElementSibling.firstElementChild.innerHTML;
        }
    });
    return "[" + specName + " not found!]";
}

var data = {
    version: $$("h2.pdp-hero__heading")[0].innerHTML,
    price: $$("div.pdp-hero__price")[0].innerHTML,
    frame: getSpec("frame"),
    fork: getSpec("fork"),
    der_front: getSpec("front derailleur"),
    der_rear: getSpec("rear derailleur")
};


var id = window.location.href.split("/").slice(-1)[0];
localStorage.setItem(id, JSON.stringify(data));