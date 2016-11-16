var page = require('webpage').create();
var system = require('system');
var fs = require('fs');

var appDomain = system.args[1];
var siteDomain = system.args[2];
var sitePath = system.args[3];
var directory = system.args[4];

if (system.args.length !== 5) {
  console.log('Usage: phantomjs export.js <app-domain> <site-domain> <site-path> <directory>');
  phantom.exit();
}
console.log("Starting...");

var address = appDomain + "export/" + siteDomain + sitePath;
var siteDirectory = directory + siteDomain + "/";

if(fs.exists(siteDirectory)){
  console.log("Removing directory: ", siteDirectory);
  fs.removeTree(directory);
}

console.log("Creating directories");
fs.makeTree(siteDirectory);
fs.makeDirectory(siteDirectory + "/css/");
fs.makeDirectory(siteDirectory + "/images/");
fs.makeDirectory(siteDirectory + "/js/");

console.log("Copying public files");
fs.copyTree("./resources/public/css/", siteDirectory + "/css/");
fs.copyTree("./resources/public/images/", siteDirectory + "/images/");
fs.copy("./resources/public/js/blocks.js", siteDirectory + "/js/blocks.js");

console.log("Saving page:", address);
page.open(address, function(status) {
  if (status !== 'success') {
    console.log('FAIL to load the address');
  } else {
    fs.write(siteDirectory + sitePath + "index.html", page.content, 'w');
    console.log("Page saved");
  }
  phantom.exit();
});

