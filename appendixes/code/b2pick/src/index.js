$(document).ready(function(){
  salvarezsuar.MapModule.install();

  $('button').click(function() {
    var xml = salvarezsuar.BoundingBoxesManager.toXML();
    $xml = $('#xml');
    $xml.empty();

    $output = $('<pre></pre>').text(xml);
    $xml.append($output);

    $('a#inline').click();
  });

  $('a#inline').fancybox({
    'width': 500,
    'autoDimensions': false,
    'autoSize': false
  });
});

var salvarezsuar = salvarezsuar || {};
salvarezsuar.BoundingBoxesManager = (function() {
  var boundingBoxes = [];

  var add = function(boundingBox) {
    boundingBoxes.push(boundingBox);
  };

  var clear = function() {
    boundingBoxes.length = 0;
  };

  var toXML = function() {
    var boundingBoxes2xml = boundingBoxes.map(function(boundingBox){
      var xml = "";
      xml += "\n\t<boundingBox>\n";
      xml += "\t\t<sw>\n";
      xml += "\t\t\t<latitude>" + boundingBox.sw.latitude + "</latitude>\n";
      xml += "\t\t\t<longitude>" + boundingBox.sw.longitude + "</longitude>\n";
      xml += "\t\t</sw>\n";
      xml += "\t\t<ne>\n";
      xml += "\t\t\t<latitude>" + boundingBox.ne.latitude + "</latitude>\n";
      xml += "\t\t\t<longitude>" + boundingBox.ne.longitude + "</longitude>\n";
      xml += "\t\t</ne>\n";
      xml += "\t</boundingBox>";

      return xml;
    });

    var xml = "<boundingBoxes>";
    xml += boundingBoxes2xml.join("");
    xml += "\n</boundingBoxes>";

    return xml;
  };

  return {
    add: add,
    toXML: toXML,
    clear: clear
  };
})();

salvarezsuar.MapModule = (function(){
  var rectangles = [];
  var currentRectangle = null;
  var isMovingRectangle = false;

  var createMap = function(){
    var centerPoint = new google.maps.LatLng(43.354810, -5.851805); // Oviedo, Asturias, Spain.
    var options = {
      zoom : 3,
      heading : 90,
      tilt : 45,
      center : centerPoint,
      mapTypeControl : false
    };

    // Initialize variables
    var map = new google.maps.Map($('#map_canvas')[0], options);
    var drawingManager = new google.maps.drawing.DrawingManager({
      drawingControl: true,
      drawingControlOptions: {
        position: google.maps.ControlPosition.TOP_CENTER,
        drawingModes: [google.maps.drawing.OverlayType.RECTANGLE]
      },
      rectangleOptions : {
        draggable: true,
        clickable: true,
        editable: true
      }
    });
    drawingManager.setMap(map);

    google.maps.event.addListener(drawingManager, 'rectanglecomplete', function(rectangle){
      appendBoundingBoxInfo(rectangle);
      rectangles.push(rectangle);

      google.maps.event.addListener(rectangle, 'bounds_changed', function(){
        $('section.boxes').empty();
        salvarezsuar.BoundingBoxesManager.clear();

        $(rectangles).each(function(index, rectangle){
          appendBoundingBoxInfo(rectangle);
        });
      });
    });
  };

  var appendBoundingBoxInfo = function (rectangle){
    //Obtainig bouding boxes
    var swLong = rectangle.getBounds().getSouthWest().lng().toFixed(2);
    var swLat  = rectangle.getBounds().getSouthWest().lat().toFixed(2);
    var neLong = rectangle.getBounds().getNorthEast().lng().toFixed(2);
    var neLat  = rectangle.getBounds().getNorthEast().lat().toFixed(2);

    salvarezsuar.BoundingBoxesManager.add({
      sw: {
        latitude: swLat,
        longitude: swLong
      },
      ne: {
        latitude: neLat,
        longitude: neLong
      }
    });

    //Creating element for the info panel
    var newBox = $(document.createElement('div'));
    $(newBox).addClass('box');
    $(newBox).append('<h3>Bounding Box</h3>');
    $(newBox).append('<p>Coordinates:</p>');
    $(newBox).append('<p>' + swLong + ', ' + swLat + ', ' + neLong + ', ' + neLat + '</p>');
    $('section.boxes').append(newBox);
  };

  return {
    install: createMap
  };
})();
