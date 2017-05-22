(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('UserDashboardDeleteController',UserDashboardDeleteController);

    UserDashboardDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserDashboard'];

    function UserDashboardDeleteController($uibModalInstance, entity, UserDashboard) {
        var vm = this;

        vm.userDashboard = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserDashboard.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
