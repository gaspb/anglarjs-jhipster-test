(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('CompDomainDeleteController',CompDomainDeleteController);

    CompDomainDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompDomain'];

    function CompDomainDeleteController($uibModalInstance, entity, CompDomain) {
        var vm = this;

        vm.compDomain = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompDomain.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
