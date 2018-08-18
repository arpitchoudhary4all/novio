
    app.controller('MyCtrl', function($scope) {
      
      $scope.initialize = function() {
         var map = new google.maps.Map(document.getElementById('map_div'), {
            center: {lat: 28.537591, lng: 77.121809},
            zoom: 16
         });
         var marker = new google.maps.Marker({
           position: {lat: 28.537591, lng: 77.121809},
           map: map
         });
         var marker = new google.maps.Marker({
           position: {lat: 28.534187, lng: 77.121869},
           map: map
         });
         var contentString = '<div id="content">'+
           '<div id="siteNotice">'+
           '</div>'+
           '<h1 id="firstHeading" class="firstHeading">Dr.Neha Thapiyal</h1>'
           '<h3>(MD)</h3>'+
           '<div id="bodyContent">'+
           '<p><b>Therapist</b>, also referred to as <b>Ayers Rock</b>, is a large ' +
           'sandstone rock formation in the southern part of the '+
           'Northern Territory, central Australia. It lies 335&#160;km (208&#160;mi) '+
           'south west of the nearest large town, Alice Springs; 450&#160;km '+
           '(280&#160;mi) by road. Kata Tjuta and Uluru are the two major '+
           'features of the Uluru - Kata Tjuta National Park. Uluru is '+
           'sacred to the Pitjantjatjara and Yankunytjatjara, the '+
           'Aboriginal people of the area. It has many springs, waterholes, '+
           'rock caves and ancient paintings. Uluru is listed as a World '+
           'Heritage Site.</p>'+
           '<p>Attribution: Uluru, <a href="https://en.wikipedia.org/w/index.php?title=Uluru&oldid=297882194">'+
           'https://en.wikipedia.org/w/index.php?title=Uluru</a> '+
           '(last visited June 22, 2009).</p>'+
           '</div>'+
           '<div class="rating"><span></span></div>'+
           '</div>';
           var contentString1 = '<div id="content">'+
           '<div id="siteNotice">'+
           '</div>'+
           '<h1 id="firstHeading" class="firstHeading">Dr.Manoj Verma</h1>'
           '<h3>(MD)</h3>'+
           '<div id="bodyContent">'+
           '<p><b>Therapist</b>, also referred to as <b>Ayers Rock</b>, is a large ' +
           'sandstone rock formation in the southern part of the '+
           'Northern Territory, central Australia. It lies 335&#160;km (208&#160;mi) '+
           'south west of the nearest large town, Alice Springs; 450&#160;km '+
           '(280&#160;mi) by road. Kata Tjuta and Uluru are the two major '+
           'features of the Uluru - Kata Tjuta National Park. Uluru is '+
           'sacred to the Pitjantjatjara and Yankunytjatjara, the '+
           'Aboriginal people of the area. It has many springs, waterholes, '+
           'rock caves and ancient paintings. Uluru is listed as a World '+
           'Heritage Site.</p>'+
           '<p>Attribution: Uluru, <a href="https://en.wikipedia.org/w/index.php?title=Uluru&oldid=297882194">'+
           'https://en.wikipedia.org/w/index.php?title=Uluru</a> '+
           '(last visited June 22, 2009).</p>'+
           '</div>'+
           '<div class="rating"><span></span></div>'+
           '</div>';

       var infowindow = new google.maps.InfoWindow({
         content: contentString
       });

       var marker = new google.maps.Marker({
         position: {lat: 28.537591, lng: 77.121809},
         map: map,
         title: 'Uluru (Ayers Rock)'
       });
       marker.addListener('click', function() {
         infowindow.open(map, marker);
       });
      }    
      
      google.maps.event.addDomListener(window, 'load', $scope.initialize);   

   });