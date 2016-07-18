<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<title>Rectangle Events</title>
<style>
html, body, #map-canvas {
	height: 100%;
	margin: 0px;
	padding: 0px
}
</style>

<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
<script>
	// This example adds a user-editable rectangle to the map.
	// When the user changes the bounds of the rectangle,
	// an info window pops up displaying the new bounds.
	
    
	var rectangle;
	var map;
	var infoWindow;
	var Qmin;
	var Qmax;
	var donen="at";

	
    
	function initialize() {
		var mapOptions = {
			center : new google.maps.LatLng(35.063035,33.220011),
			zoom : 7
		};
		map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);

		var bounds = new google.maps.LatLngBounds(new google.maps.LatLng(
				 34.46553287679265, 31.912085937500024), new google.maps.LatLng(35.78095013997835, 34.94491601562504));

		// Define the rectangle and set its editable property to true.
		rectangle = new google.maps.Rectangle({
			bounds : bounds,
			editable : true,
			draggable : true
		});

		rectangle.setMap(map);

		// Add an event listener on the rectangle.
		google.maps.event.addListener(rectangle, 'bounds_changed', showNewRect);

		// Define an info window on the map.
		
		infoWindow = new google.maps.InfoWindow();		
		
		
		
	}
	// Show the new coordinates for the rectangle in an info window.

	/** @this {google.maps.Rectangle} */

	function showNewRect(event) {

		var ne = rectangle.getBounds().getNorthEast();
		var sw = rectangle.getBounds().getSouthWest();
		var contentString = '<form action="WriteToFile.jsp" method="get" style="color:red"><b>Rectangle moved.</b><br>'
				+ 'New north-east corner:<input type="text" name="txt_nelat" value="'+ne.lat()+'"/> <input type="text" name="txt_nelng" value="'+ne.lng()+'"/>' 
				+ '<br>' + 'New south-west corner:<input type="text" name="txt_swlat" value="'+sw.lat()+'"/> <input type="text" name="txt_swlng" value="'+sw.lng()+'"/>'
				+ '<input type="submit" value="Verileri Al"/></form>';
					

		// Set the info window's content and position.
		
		infoWindow.setContent(contentString);
		infoWindow.setPosition(ne);

		//infoWindow.open(map);
		donen=contentString;
		document.getElementById('latlnginfo').innerHTML = donen;		
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);

</script>
</head>
<body>
	<div id="map-canvas" style="width:800px; height:600px; border:1px solid #000; "></div>
	<div id="latlnginfo" class="latlnginfo" style="z-index: 999 !important; background: fff; color: #000  width:200px; height: 100px; position: absolute; top=-20px;">  Move Rectangele !</div>
	
    
    
	
	
	
</body>
</html>
