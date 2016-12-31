/**
 * Gathers data about a Cannondale bike version from its page on the Cannondale website,
 * and writes the data to localStorage.
 *
 * Use with cannondale_models.js.
 */

"use strict";

var specTags = $$("dt.details-list__title");

/**
 * Gets a bike specification from the page.
 *
 * @param {string} specName - Case-sensitive name of the spec to read.
 * @returns {string} The value of the spec.
 */
function getSpec(specName) {
    specTags.forEach(function (spec) {
        if (spec.innerHTML.trim() == specName) {
            return spec.nextElementSibling.innerHTML;
        }
    });
    return "[" + specName + " not found!]";
}


var data = {
    version: $$("h1.hero-product__title")[0].innerHTML,
    price: $$("span.price-bundle")[0].firstElementChild.innerHTML.trim(),
    frame: getSpec("Frame"),
    fork: getSpec("Fork"),
    der_front: getSpec("Front derailleur"),
    der_rear: getSpec("Rear derailleur")
};

var versionId = window.location.href.split("/").slice(-1)[0];
localStorage.setItem(versionId, JSON.stringify(data));