app.config(function($routeProvider,$locationProvider,HOME,CHAT,MAP,HISTORY){
    $locationProvider.hashPrefix('');
    $routeProvider.when(HOME,{
        templateUrl:'views/home.html'
    }).when(CHAT,{
        templateUrl:'views/chat.html',
        // controller:'aboutusctrl'
    }).when(MAP,{
        templateUrl:'views/map.html',
        controller:'MyCtrl' 
    }).when(HISTORY,{
        templateUrl:'views/history.html',
        // controller:'newsctrl' 
    }).otherwise({template:'OOPS!!! URL ENTERED IS NOT AVAILABLE'});
});