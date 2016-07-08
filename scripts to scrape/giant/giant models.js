var links = $$("article div.text a");
var currentLink, href;
var linksToUse = [];

localStorage.clear();

for(var i=0; i<links.length; i++){
	
	linksToUse.push(links[i].href);		
}



var id;
var ids = [];
for(var i=0; i<linksToUse.length; i++){
	currentLink = linksToUse[i];
	id = currentLink.split("/").slice(-2,-1)[0];
	document.write("<p id=\""+id+"\"><a href=\""+currentLink+"\">"+currentLink+"</a></p>");
	ids.push(id);
}

window.addEventListener("storage", storageCallback);

function storageCallback(event) {
	var key = event.key;
	if(key=="modernizr") return;
	$("p#"+key)[0].innerHTML += " - found!";
	
	var data = JSON.parse(event.newValue); // http://stackoverflow.com/a/2010994
	
	document.write("<pre>");
	
	var model;
	for(var i=0; i<data.length; i++){
		model = data[i];
		for (var spec in model) {
			if (model.hasOwnProperty(spec)) {
				document.write(spec + "\t" + model[spec] + "\n");
			}
		}
		document.write("\n\n");
	}
	
	
	
}