'use strict';

angular.module('customLoginApp')
    .controller('authenticationController', ['$scope', '$window', 'Facebook', function ($scope, $window, Facebook) {
        $scope.loginToFacebook = function () {
            Facebook.login(function (response) {
                // Do something with response. Don't forget here you are on Facebook scope so use $scope.$apply
                if (response.authResponse) {
                  var accessToken = response.authResponse.accessToken;
                  console.log('Access Token = '+ accessToken);
                }
              });
          };
        $scope.loginToGoogle = function () {
            $window.location = "https://accounts.google.com/o/oauth2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile&redirect_uri=http%3A%2F%2Flocalhost:8088%2Fauthorization%2Fgoogle&response_type=code&client_id=802083911831-6u67sbbs2fi1kf08nq63n0ivrmhvooqo.apps.googleusercontent.com&access_type=offline";
            //, 'popUpWindow','height=700,width=800,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes'
          };
        $scope.submitForm = function(){
            $("#loginForm").submit();
        }
      }]);