(function() {
    'use strict';

    angular
        .module('solarhackApp')
        .controller('InvestorDialogController', InvestorDialogController);

    InvestorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Investor', 'User', 'Solarprojects'];

    function InvestorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Investor, User, Solarprojects) {
        var vm = this;

        vm.investor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.solarprojects = Solarprojects.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.investor.id !== null) {
                Investor.update(vm.investor, onSaveSuccess, onSaveError);
            } else {
                Investor.save(vm.investor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('solarhackApp:investorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
