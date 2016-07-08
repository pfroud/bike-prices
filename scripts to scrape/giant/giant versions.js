/* These functions fake adding any object to Storage. http://stackoverflow.com/a/2010994 */
Storage.prototype.setObject = function (key, value) {
	this.setItem(key, JSON.stringify(value));
};



var articles = $$("article");
var bikes = [];

var currentArt, b;
for(var i=0; i<articles.length; i++){
	b = parseArticle(articles[i]);
	if(b != undefined) bikes.push(b);	
}


var id = window.location.href.split("/").slice(-2,-1)[0];
localStorage.setObject(id, bikes);


function parseArticle(art){
	var features = art.querySelectorAll("li");
	var version = art.querySelector("div.name a").innerHTML;
	if(version.indexOf("Frameset") != -1) return;
	var data = {
		version:     version,
		price:       art.querySelector("div.price").innerHTML,
		frame:       features[0].innerHTML,
		fork:        features[1].innerHTML,
		drivetrain:  features[3].innerHTML,
	};
	return data;
}