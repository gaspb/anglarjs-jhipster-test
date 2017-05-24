(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('UserDashboardDialogController', UserDashboardDialogController);

    UserDashboardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'UserDashboard', 'User', 'Project', 'Suggestion', 'Competence'];

    function UserDashboardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, UserDashboard, User, Project, Suggestion, Competence) {
        var vm = this;

        vm.userDashboard = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.projects = Project.query();
        vm.suggestions = Suggestion.query();
        vm.competences = Competence.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;

            if (vm.userDashboard.id !== null  ) {
               // if (vm.userDashboard.id !==   )
                UserDashboard.update(vm.userDashboard, onSaveSuccess, onSaveError);
            } else {
                UserDashboard.save(vm.userDashboard, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterElasticsearchSampleApplicationApp:userDashboardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
