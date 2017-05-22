(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('CompDomainDetailController', CompDomainDetailController);

    CompDomainDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CompDomain', 'Competence', 'Project'];

    function CompDomainDetailController($scope, $rootScope, $stateParams, previousState, entity, CompDomain, Competence, Project) {
        var vm = this;

        vm.compDomain = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterElasticsearchSampleApplicationApp:compDomainUpdate', function(event, result) {
            vm.compDomain = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
