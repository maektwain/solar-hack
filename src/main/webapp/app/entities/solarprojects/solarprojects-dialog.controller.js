(function() {
    'use strict';

    angular
        .module('solarhackApp')
        .controller('SolarprojectsDialogController', SolarprojectsDialogController);

    SolarprojectsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Solarprojects'];

    function SolarprojectsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Solarprojects) {
        var vm = this;

        vm.solarprojects = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.solarprojects.id !== null) {
                Solarprojects.update(vm.solarprojects, onSaveSuccess, onSaveError);
            } else {
                Solarprojects.save(vm.solarprojects, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('solarhackApp:solarprojectsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
