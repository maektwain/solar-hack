(function() {
    'use strict';

    angular
        .module('solarhackApp')
        .controller('InvestorDeleteController',InvestorDeleteController);

    InvestorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Investor'];

    function InvestorDeleteController($uibModalInstance, entity, Investor) {
        var vm = this;

        vm.investor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Investor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
