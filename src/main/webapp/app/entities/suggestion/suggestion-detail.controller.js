(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('SuggestionDetailController', SuggestionDetailController);

    SuggestionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Suggestion', 'UserDashboard'];

    function SuggestionDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Suggestion, UserDashboard) {
        var vm = this;

        vm.suggestion = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterElasticsearchSampleApplicationApp:suggestionUpdate', function(event, result) {
            vm.suggestion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
