(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('CompetenceDetailController', CompetenceDetailController);

    CompetenceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Competence', 'CompDomain'];

    function CompetenceDetailController($scope, $rootScope, $stateParams, previousState, entity, Competence, CompDomain) {
        var vm = this;

        vm.competence = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterElasticsearchSampleApplicationApp:competenceUpdate', function(event, result) {
            vm.competence = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
