'use strict';

angular.module('customLoginApp')
    .controller('signupController', ['$scope', '$http', function ($scope, $http) {

        $scope.formData = {"firstName": "jose"};

        $scope.submitForm = function(){

            var dataToPost = $.param($scope.formData);

            console.log("dataToPost: " + dataToPost);

            $http({
                method: 'POST',
                url: '/signup',
                data: $scope.formData,
                headers: {'Content-Type': 'application/json'}
            })
                .success(function(data){
                    console.log(data);
                });
        }
    }]);