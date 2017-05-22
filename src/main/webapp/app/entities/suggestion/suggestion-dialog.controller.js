(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('SuggestionDialogController', SuggestionDialogController);

    SuggestionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Suggestion', 'UserDashboard'];

    function SuggestionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Suggestion, UserDashboard) {
        var vm = this;

        vm.suggestion = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.userdashboards = UserDashboard.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.suggestion.id !== null) {
                Suggestion.update(vm.suggestion, onSaveSuccess, onSaveError);
            } else {
                Suggestion.save(vm.suggestion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterElasticsearchSampleApplicationApp:suggestionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, suggestion) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        suggestion.image = base64Data;
                        suggestion.imageContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.creationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
