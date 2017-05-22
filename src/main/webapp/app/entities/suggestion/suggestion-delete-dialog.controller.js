(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('SuggestionDeleteController',SuggestionDeleteController);

    SuggestionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Suggestion'];

    function SuggestionDeleteController($uibModalInstance, entity, Suggestion) {
        var vm = this;

        vm.suggestion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Suggestion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
