var MapModule = (function(){
  var heatpoints = new google.maps.MVCArray([]);

  var addPointToHeatmap = function(lat, lng) {
    var point = new google.maps.LatLng(lat, lng);
    heatpoints.push(point);
  };

  var processTsvFile = function(event){
    var tsv = event.target.result;
    tsv.split("\n").forEach(function(row){
      var columns = row.split('\t');
      var lat = parseFloat(columns[1]);
      var lng = parseFloat(columns[2]);
      addPointToHeatmap(lat, lng);
    });
  };

  var install = function(){
    var mapOptions = { zoom: 2, center: new google.maps.LatLng(43.354810, -5.851805)};
    var map = new google.maps.Map($('.map')[0], mapOptions);
    var heatmap = new google.maps.visualization.HeatmapLayer({
      data: heatpoints
    });
    heatmap.setMap(map);
  };

  var loadFiles = function(files) {
    for(var i = 0; i < files.length; i++) {
      var currentFile = files[i];
      var reader = new FileReader();
      reader.onload = processTsvFile;
      reader.readAsText(currentFile);
    }
  };

  return {
    install: install,
    loadFiles: loadFiles
  };
})();
