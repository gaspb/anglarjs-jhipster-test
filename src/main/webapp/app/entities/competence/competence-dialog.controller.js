(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('CompetenceDialogController', CompetenceDialogController);

    CompetenceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Competence', 'CompDomain'];

    function CompetenceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Competence, CompDomain) {
        var vm = this;

        vm.competence = entity;
        vm.clear = clear;
        vm.save = save;
        vm.compdomains = CompDomain.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.competence.id !== null) {
                Competence.update(vm.competence, onSaveSuccess, onSaveError);
            } else {
                Competence.save(vm.competence, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterElasticsearchSampleApplicationApp:competenceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
