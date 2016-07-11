/**
 * Gathers data about a Giant bike model (not version) from its page on the Giant website,
 * and writes the data to localStorage.
 *
 * What I call a model, Giant calls a series; what I call a version, Giant calls a model.
 * File naming is unchanged for ease of use.
 *
 * Use with giant_models.js.
 */

"use strict";

/**
 * Reads an <article> tag and returns an object with info about that bike model.
 *
 * @param {object} article - Reference to the article tag to parse.
 * @returns {object} An object with model info.
 */
function parseArticle(article) {
    var versionName = article.querySelector("div.name a").innerHTML; //actually currentModel name
    if (versionName.indexOf("Frameset") != -1) return undefined; // skip frame-only models
    var features = article.querySelectorAll("li");
    return {
        version: versionName,
        price: article.querySelector("div.price").innerHTML,
        frame: features[0].innerHTML,
        fork: features[1].innerHTML,
        drivetrain: features[3].innerHTML
    };
}

var articleTags = $$("article"); // each currentModel is conveniently in an <article> tag
var models = [], currentModel;
for (var i = 0; i < articleTags.length; i++) {
    if (currentModel = parseArticle(articleTags[i])) models.push(currentModel);
}

var modelId = window.location.href.split("/").slice(-2, -1)[0];
localStorage.setItem(modelId, JSON.stringify(models));