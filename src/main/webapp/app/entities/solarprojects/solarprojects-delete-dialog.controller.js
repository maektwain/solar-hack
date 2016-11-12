(function() {
    'use strict';

    angular
        .module('solarhackApp')
        .controller('SolarprojectsDeleteController',SolarprojectsDeleteController);

    SolarprojectsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Solarprojects'];

    function SolarprojectsDeleteController($uibModalInstance, entity, Solarprojects) {
        var vm = this;

        vm.solarprojects = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Solarprojects.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
