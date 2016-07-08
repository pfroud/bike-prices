var links = $$("a.relatedProducts");
var currentLink, href;
var linksToUse = [];

localStorage.clear();

console.warn("This does does distinguish between the SuperSix EVO and SuperSix EVO Hi-Mod!!");

for(var i=0; i<links.length; i++){
	linksToUse.push(links[i].href);		
}



var id;
var ids = [];
for(var i=0; i<linksToUse.length; i++){
	currentLink = linksToUse[i];
	id = currentLink.split("=")[1];
	document.write("<p id=\""+id+"\"><a href=\""+currentLink+"\">"+currentLink+"</a></p>");
	ids.push(id);
}

window.addEventListener("storage", storageCallback);

function storageCallback(event) {
	var key = event.key;
	$("p#"+key)[0].innerHTML += " - found!";
	var data = JSON.parse(event.newValue); // http://stackoverflow.com/a/2010994
	document.write("<pre>");
	for (var spec in data) {
		if (data.hasOwnProperty(spec)) {
			document.write(spec + "\t" + data[spec] + "\n");
		}
	}
	document.write("\n\n");
}