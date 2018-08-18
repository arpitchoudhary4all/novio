
// //    var jq = jQuery.noConflict();
// //    app.controller('MyCtrl1', function($rootScope,$scope) {
        
//     //     // $rootScope.getBotResponse = 
//     //     jq("#textInput").keypress(function(e) {
//     //         if(e.which == 13) {
//     //             getBotResponse();
//     //         }
//     //     });
//     //     jq("#buttonInput").click(function() {
//     //       getBotResponse();
//     //     }); 
//     //  });
//     //  function getBotResponse() {
//     //     var rawText = jq("#textInput").val();
//     //     var userHtml = '<p class="userText"><span>' + rawText + '</span></p>';
//     //     jq("#textInput").val("");
//     //     jq("#chatbox").append(userHtml);
//     //     document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});
//     //     jq.get("/get", { msg: rawText }).done(function(data) {
//     //       var botHtml = '<p class="botText"><span>' + data + '</span></p>';
//     //       jq("#chatbox").append(botHtml);
//     //       document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});
//     //     });
//     //   }
//     //   <script>
//     //   function getBotResponse() {
//     //     var rawText = $("#textInput").val();
//     //     var userHtml = '<p class="userText"><span>' + rawText + '</span></p>';
//     //     $("#textInput").val("");
//     //     $("#chatbox").append(userHtml);
//     //     document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});
//     //     $.get("/get", { msg: rawText }).done(function(data) {
//     //       var botHtml = '<p class="botText"><span>' + data + '</span></p>';
//     //       $("#chatbox").append(botHtml);
//     //       document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});
//     //     });
//     //   }
//     //   $("#textInput").keypress(function(e) {
//     //       if(e.which == 13) {
//     //           getBotResponse();
//     //       }
//     //   });
//     //   $("#buttonInput").click(function() {
//     //     getBotResponse();
//     //   })
//     // </script>


    

//     app.controller("MyCtrl1", function($scope) {
//         // var textInput = document.querySelector("#textInput");
      
//         $scope.getBotResponse = function() {
//             var rawText = document.querySelector("#textInput").value;
//             var userHtml = '<p class="userText"><span>' + rawText + '</span></p>';
//             document.querySelector("#textInput").value ="";
//             document.querySelector("#chatbox").append(userHtml);
//             document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});  
//             console.log("mesage is "+rawText);
            
//             doAjax();
//             document.querySelector("#textInput").addEventListener("keypress",function(e){
//                 if(e.which == 13){
//                     getBotResponse();
//                 }
//             });
//             document.querySelector("#buttonInput").addEventListener("click",function(){
//                 getBotResponse();
//                 console.log("i m called");
//             });
//         }
//         $scope.doAjax =function(){
//         var xmlHttpReq = new XMLHttpRequest();
//         xmlHttpReq.open("GET","10.5.50.105:8008");
//         xmlHttpReq.onreadystatechange = function(){ //this binds the function
//             console.log("state is ",xmlHttpReq.readyState)  //to see what is the status
//              if(xmlHttpReq.readyState==4 && xmlHttpReq.status==200){
//               function print(data){
//                 var botHtml = '<p class="botText"><span>' + data + '</span></p>';  
//                 document.querySelector("#chatbox").append(botHtml);
//                 document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});
//               }
    
//             }
//         }
//     }
  
   
    
//     });


// // function getBotResponse(){
// //     var rawText = document.querySelector("#textinput").value;
// //     var userHtml = '<p class="userText"><span>' + rawText + '</span></p>';
// //     document.querySelector("#textInput").value ="";
// //     document.querySelector("#chatbox").append(userHtml);
// //     document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});
    
// // function doAjax(){
// //     var xmlHttpReq = new XMLHttpRequest();
// //     xmlHttpReq.open("GET","10.5.50.105:8008");
// //     xmlHttpReq.onreadystatechange = function(){ //this binds the function
// //         console.log("state is ",xmlHttpReq.readyState)  //to see what is the status
// //          if(xmlHttpReq.readyState==4 && xmlHttpReq.status==200){
// //           function print(data){
// //             var botHtml = '<p class="botText"><span>' + data + '</span></p>';  
// //             document.querySelector("#chatbox").append(botHtml);
// //             document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});
// //           }

// //         }
// //     }
// // }

// // }
// // document.querySelector("#textInput").keypress(function(e){
// //     if(e.which == 13){
// //         getBotResponse();
// //     }
// // });
// // document.querySelector("#buttonInput").addEventListener("click",function(){
// //     getBotResponse();
// // });

