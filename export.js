var page = require('webpage').create();
var system = require('system');

var address = system.args[1];

page.open(address, function(status) {
  if (status !== 'success') {
    phantom.exit(1);
  } else {
    console.log(page.content);
    phantom.exit(0);
  }
});

