/* These functions fake adding any object to Storage. http://stackoverflow.com/a/2010994 */
Storage.prototype.setObject = function (key, value) {
	this.setItem(key, JSON.stringify(value));
};

var specTags = $$("div.cell h4");
var specsOveriew = $$("div.textBox-sm");

var data = {
	version:     $$("h1.txtBlu")[0].innerHTML.trim(),
	price:       $$("li.price")[0].innerHTML,
	frame:       getSpecOverview("Frame"),
	fork:        getSpec("Fork"),
	drivetrain:  getSpecOverview("DriveTrain"),
	
};


var id = window.location.href.split("=")[1];
localStorage.setObject(id, data);



function getSpecOverview(specName){
	var currentSpec;
	for(var i=0; i<specsOveriew.length; i++){
		currentSpec = specsOveriew[i];
		if(currentSpec.firstElementChild.innerHTML == specName){
			return currentSpec.firstElementChild.nextElementSibling.innerHTML.trim();
		}
	}
	return "["+specName+" not found!]";
}

function getSpec(specName){
	var currentSpec;
	for(var i=0; i<specTags.length; i++){
		currentSpec = specTags[i];
		if(currentSpec.innerHTML == specName){
			return currentSpec.nextElementSibling.innerHTML.trim();
		}
	}
	return "["+specName+" not found!]";
}