(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('ProjectDialogController', ProjectDialogController);

    ProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Project', 'Competence', 'CompDomain', 'UserDashboard'];

    function ProjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Project, Competence, CompDomain, UserDashboard) {
        var vm = this;

        vm.project = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.competences = Competence.query();
        vm.compdomains = CompDomain.query();
        vm.userdashboards = UserDashboard.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.project.id !== null) {
                Project.update(vm.project, onSaveSuccess, onSaveError);
            } else {
                Project.save(vm.project, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterElasticsearchSampleApplicationApp:projectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, project) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        project.image = base64Data;
                        project.imageContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.creationDate = false;
        vm.datePickerOpenStatus.completionDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
