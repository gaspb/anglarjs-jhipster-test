(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Project', 'Competence', 'CompDomain', 'UserDashboard'];

    function ProjectDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Project, Competence, CompDomain, UserDashboard) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterElasticsearchSampleApplicationApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
