var links = $$("a.colorway-tile__anchor-link");
var currentLink, href;
var linksToUse = [];

localStorage.clear();

for(var i=0; i<links.length; i++){
	href = links[i].href;
	if(href.indexOf("frameset") == -1 && href.indexOf("module") == -1)
		linksToUse.push(href);
		
}
linksToUse = Array.from(new Set(linksToUse)); //remove duplicates



var id;
var ids = [];
for(var i=0; i<linksToUse.length; i++){
	currentLink = linksToUse[i];
	id = currentLink.split("/").slice(-1)[0];
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