(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('viewCtrl', ViewCtrl);

    ViewCtrl.$inject = ['$scope', '$state', 'Principal'];
    function  ViewCtrl ($scope, $state, Principal) {
            $scope.$on('$stateChangeSuccess', function (event, toState) {
                var isAuthenticated = Principal.isAuthenticated();
                if (toState.name === 'home' && !isAuthenticated) $state.go('newbie');
                if (toState.name === 'newbie' && isAuthenticated) $state.go('home');

                    //mastering uiview classes for ng.enter ng.leave transitions between views
                    $scope.uiregister = (toState.name === 'register');
                    $scope.uinewbie = (toState.name === 'newbie');



            });
        }
})();
