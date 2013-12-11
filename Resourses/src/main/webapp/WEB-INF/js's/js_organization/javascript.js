
var map;
var marker;
var lat;
var lng;
var state=true;

function initialize() {
    if ( lat === "" || lat === undefined) { lat = 20}
    if ( lng === "" || lng === undefined ) { lng = 20}

  var mapOptions = {
    zoom: 8,
    center: new google.maps.LatLng(lat, lng),
    disableDefaultUI: true,
      panControl: true,
      zoomControl: true,
      mapTypeControl: true,
      scaleControl: true,
      streetViewControl: true,
      overviewMapControl: true,

    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
   var mapDiv = document.getElementById('map-canvas');
   map = new google.maps.Map(mapDiv, mapOptions);

   marker = new google.maps.Marker({
       position: map.getCenter(),
       map: map

   });
    /*google.maps.event.addDomListener(map, 'click', showAlert(event));*/
      google.maps.event.addDomListener(map, 'click', function(event) {
                                                         marker.setPosition(event.latLng);
                                                         document.getElementById('lat').value= event.latLng.lat();
                                                         document.getElementById('lng').value= event.latLng.lng();
                                                         // populate yor box/field with lat, lng
                                                         /*alert("Lat=" + lat + "; Lng=" + lng);*/
                                                     });
}


/*function showAlert(event) {
  marker.setPosition(new google.maps.LatLng(event.latLng.lat(),event.latLng.lng())) ;

  console.error(map.getCenter());
  console.error($('#latInput'));
}*/

google.maps.event.addDomListener(window, 'load', initialize);


