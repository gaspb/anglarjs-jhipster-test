(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('CompDomainDialogController', CompDomainDialogController);

    CompDomainDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CompDomain', 'Competence', 'Project'];

    function CompDomainDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CompDomain, Competence, Project) {
        var vm = this;

        vm.compDomain = entity;
        vm.clear = clear;
        vm.save = save;
        vm.competences = Competence.query();
        vm.projects = Project.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.compDomain.id !== null) {
                CompDomain.update(vm.compDomain, onSaveSuccess, onSaveError);
            } else {
                CompDomain.save(vm.compDomain, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterElasticsearchSampleApplicationApp:compDomainUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
