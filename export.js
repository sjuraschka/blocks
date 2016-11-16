var page = require('webpage').create();
var system = require('system');
var fs = require('fs');

var appDomain = system.args[1];
var siteDomain = system.args[2];
var sitePath = system.args[3];
var assetFolderName = system.args[4];
var directory = system.args[5];

if (system.args.length !== 6) {
  console.log('Usage: phantomjs export.js <app-domain> <site-domain> <site-path> <asset-folder-name> <directory>');
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
fs.makeDirectory(siteDirectory + "/js/");

console.log("Copying public files");
fs.copyTree("./resources/data/assets/"+assetFolderName, siteDirectory+assetFolderName);
fs.copy("./resources/public/js/blocks.min.js", siteDirectory + "/js/blocks.min.js");

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

