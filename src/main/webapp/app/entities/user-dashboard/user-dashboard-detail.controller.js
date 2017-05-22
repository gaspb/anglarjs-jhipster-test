(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('UserDashboardDetailController', UserDashboardDetailController);

    UserDashboardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserDashboard', 'User', 'Project', 'Suggestion', 'Competence'];

    function UserDashboardDetailController($scope, $rootScope, $stateParams, previousState, entity, UserDashboard, User, Project, Suggestion, Competence) {
        var vm = this;

        vm.userDashboard = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterElasticsearchSampleApplicationApp:userDashboardUpdate', function(event, result) {
            vm.userDashboard = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
