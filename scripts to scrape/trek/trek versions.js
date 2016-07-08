/* These functions fake adding any object to Storage. http://stackoverflow.com/a/2010994 */
Storage.prototype.setObject = function (key, value) {
	this.setItem(key, JSON.stringify(value));
};

var specTags = $$("dt.details-list__title");

var data = {
	version:     $$("h1.buying-zone__title")[0].innerHTML,
	price:       $$("span.price-bundle")[0].firstElementChild.innerHTML,
	frame:       getSpec("Frame"),
	fork:        getSpec("Fork"),
	der_front:   getSpec("Front derailleur"),
	der_rear:    getSpec("Rear derailleur"),
};


var id = window.location.href.split("/").slice(-1)[0];
localStorage.setObject(id, data);


function getSpec(specName){
	var currentSpec;
	
	for(var i=0; i<specTags.length; i++){
		currentSpec = specTags[i];
		if(currentSpec.innerHTML.trim() == specName){
			return currentSpec.nextSibling.nextSibling.innerHTML;
		}
	}
	return "["+specName+" not found!]";
}