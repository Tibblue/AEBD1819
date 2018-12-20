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
  var headerTR$ = $('<tr/>');

  // TODO verificar se é preciso verificar todos os elementos
  // problema elementos vazios no primeiro elemento
  for (var i = 0; i < myList.length; i++) {
    var elem = myList[i];
      for (var attrib in elem) {
        // se o attrib nao estiver no columnSet adiciona
        if ($.inArray(attrib, columnSet) == -1) {
          columnSet.push(attrib);
          headerTR$.append($('<th/>').html(attrib));
        }
      }
  }
  $(selector).append(headerTR$);

  return columnSet;
}
 