var element = "%s";
var startTime = new Date().getTime();
var timeout = 30000;
var clickButton = document.evaluate(element, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
(function checkIfElemExists() {
    if (clickButton == null) {
        if (new Date().getTime() - startTime > timeout) {
            console.log('Timed out waiting for element');
        } else {
            (checkIfElemExists, 500);
            console.log('Element not found, waiting...');
        }
    } else {
        console.log('Element found, clicking...');
        clickButton.click();
    }
})();