/*
var myList = [
	{"first_name": "John","last_name": "Doe","age": 30},
	{"first_name": "Steven","last_name": "Williams","gender": "male"},
	{"first_name": "Mary","last_name": "Troy","age": 19},
	{"first_name": "Natalia","last_name": "Will","age": 44,"gender": "female"}
];
*/

function buildHtmlTable(selector, myList) {
	
  var columns = addAllColumnHeaders(selector, myList);

  for (var i = 0; i < myList.length; i++) {
    var row$ = $('<tr/>');
    for (var colIndex = 0; colIndex < columns.length; colIndex++) {
      var cellValue = myList[i][columns[colIndex]];
      if (cellValue == null) cellValue = "";
      row$.append($('<td/>').html(cellValue));
    }
    $(selector).append(row$);
  }
}

function addAllColumnHeaders(selector, myList) {
  var columnSet = [];
  var headerTr$ = $('<tr/>');

  for (var i = 0; i < myList.length; i++) {
    var rowHash = myList[i];
    for (var key in rowHash) {
      if ($.inArray(key, columnSet) == -1) {
        columnSet.push(key);
        headerTr$.append($('<th/>').html(key));
      }
    }
  }
  $(selector).append(headerTr$);

  return columnSet;
}
 