/* These functions fake adding any object to Storage. http://stackoverflow.com/a/2010994 */
Storage.prototype.setObject = function (key, value) {
	this.setItem(key, JSON.stringify(value));
};

var specTags = $$("h2.specs__subheading");

var data = {
	version:     $$("h2.pdp-hero__heading")[0].innerHTML,
	price:       $$("div.pdp-hero__price")[0].innerHTML,
	frame:       getSpec("frame"),
	fork:        getSpec("fork"),
	der_front:   getSpec("front derailleur"),
	der_rear:    getSpec("rear derailleur"),
};


var id = window.location.href.split("/").slice(-1)[0];
localStorage.setObject(id, data);


function getSpec(specName){
	var currentSpec;
	var caps = specName.toUpperCase();
	
	for(var i=0; i<specTags.length; i++){
		currentSpec = specTags[i];
		if(currentSpec.innerHTML == caps){
			return currentSpec.parentElement.nextSibling.nextSibling.firstElementChild.innerHTML;
		}
	}
	return "["+specName+" not found!]";
}