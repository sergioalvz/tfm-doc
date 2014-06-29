$(document).ready(function(){
  $('body').on('dragover', 'section:first-child', function(event){
    event.stopPropagation();
    event.preventDefault();
    $('section:first-child').addClass('hover');
  });

  $('body').on('dragleave', 'section:first-child', function(event){
    event.stopPropagation();
    event.preventDefault();
    $('section:first-child').removeClass('hover');
  });

  $('body').on('drop', 'section:first-child', function(event){
    if(event.originalEvent.dataTransfer && event.originalEvent.dataTransfer.files.length > 0) {
        event.stopPropagation();
        event.preventDefault();
        var files = event.originalEvent.dataTransfer.files;
        MapModule.loadFiles(files);
    }
    $('section:first-child').removeClass('hover');
  });

  google.maps.event.addDomListener(window, 'load', MapModule.install());
});
