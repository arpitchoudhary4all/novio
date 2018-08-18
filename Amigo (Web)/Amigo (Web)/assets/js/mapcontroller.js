
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
       }    
       
       google.maps.event.addDomListener(window, 'load', $scope.initialize);   

    });