$("document").ready(function(){
	
	var location = [];
	initialize();
	var intflag;
		//-------------------------------------------------------------
	// initialize map 
	function initialize() {
		
		var myLatlng = new google.maps.LatLng(40.7127, -74.0059);		//40.5200, 34.3400 - Center of World; NY:40.7127, -74.0059
																		// 47.0000, 2.0000 - France
		var mapOptions = {
			zoom: 1,
			center: myLatlng
		}
		var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

		
		// all current markers		
		var markcluster = [];
		var clusterhub = [];
/******************RESET***********************/		
		function resetMap(){
			map.setZoom(1);
			map.setCenter(myLatlng);			
		}
		
/*****************CLEAR***********************/	
		function clearMap(){
	
			if (markcluster.length > 0){
				for (var i = 0; i < markcluster.length; i++){
					markcluster[i].setMap(null);				// removes all from map.	
				} 
				markcluster = [];							// deletes all markers.
			}
			
			if(clusterhub.length > 0){
				$.each(clusterhub, function(i, clust){	
					clust.clearMarkers();
				});
				clusterhub = [];
			}
	
		}	
/*****************SET_MARKER***********************/	
		function setMarkers(map){	
			
			//clearMap();
			//resetMap();
			
			//console.log(hh);
			//plots multiple markers.
			//$.each(locations, function(i, tweet){
			for (var i=0;i<location.length;i++){
			
				var tweet = location[i];
				console.log("setMarkers   "+tweet);
				var lat = tweet['Lat'];  
				var longi = tweet['Long'];
				var username = tweet['Id'];
				var tweet_message = tweet['name'];
				console.log("Get the lattitude"+lat);
				console.log("Get the longitude"+longi);
				var contentString = "<p><h5>@"+ username + "  :  " + tweet_message+"</p>"
				
				// set latlong location for marker;
				marker_location = new google.maps.LatLng(lat, longi);
				
				// set marker
				var marker = new google.maps.Marker({
					map : map,
					title : username,
					position: marker_location
				});
				console.log("Set marker");
				markcluster.push(marker);
				
				// set InfoWindow on click
				var infowindow = new google.maps.InfoWindow({
					
					content: contentString
					
				});
				
				// add eventlistener for click
				google.maps.event.addListener(marker, 'click', function(){
					infowindow.open(map,marker);
					
				});
			} //end of for
			var cluster = new MarkerClusterer(map, markcluster);
			clusterhub.push(cluster);
		}// end of setMarkers.

/*****************LOAD_MAP***********************/			
		google.maps.event.addDomListener(window, 'load', initialize);
		
		
// END OF GOOGLE MAPS JAVASCRIPT.
	
/*****************dropdown***********************/				
	//$("body").html("JQuery is working");	
	$(".dropdown-menu li a").unbind("click").click(function(e){					//dropdown selected value;
		e.preventDefault();
		e.stopPropagation;
		
		var topic = $(this).text();
		console.log("selected value: "+ topic);
		var data1 = {'tag': topic};
		clearInterval(intflag);
		clearMap();
		resetMap();
		location = [];
		setInterval(ajaxCall, 2000); 
		
		function ajaxCall() {
			$.ajax({			
				url: 'http://localhost:8080/tweetmap/back',
				type: 'POST',
				dataType: 'json',	
				data: data1,
			
				success: function(data){
					console.log("Get the data!");
					console.log(data);
					location.push(data);
					
					setMarkers(map);
					//setMarkers(map,data);
					console.log(location);
					setMarkers(map);		
				},
				error: function (xhr, ajaxOptions, thrownError) {
					alert("Ooops! Something went bad!");
					//alert(thrownError);
					console.log(thrownError);
				
				},	// end of error
				timeout: 1000
				//return false;
			});	// end of ajax
		}
		
		return false;
		
	});		// end of click
	
//	var thisInterval;	
	$("#mapbutton").unbind("click").click(function(e){
		
		e.preventDefault();
		e.stopPropagation;
		var data2 = {'tag': 'All'};
		clearInterval(intflag);
		clearMap();
		resetMap();
		location = [];
		
        intflag = setInterval(ajaxCall2, 2000); 
	
		function ajaxCall2() {
			$.ajax({			
				url: 'http://localhost:8080/tweetmap/back',
				type: 'POST',
				dataType: 'json',	
				data: data2,
			
				success: function(data){
					console.log("Get the data!");
					location.push(data);
					setMarkers(map);
					console.log(location);	
				},
				error: function (xhr, ajaxOptions, thrownError) {
					alert("Ooops! Something went bad!");
					console.log(thrownError);				
				},	// end of error
			});	// end of ajax
		}	
	
		return false;

	});	// end of click
  // Stop button
$("#stopbutton").unbind("click").click(function(e){
	  clearInterval(intflag);
	  clearMap();
	  resetMap();
	  location = [];
});
	
	}// end of function initialize.
	
	// Developer info.
	$(function(){
            $('[data-toggle="info"]').tooltip();
	})
	
});	// end of ready
