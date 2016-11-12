(function() {
    'use strict';

    angular
        .module('solarhackApp')
        .controller('SolarprojectsDetailController', SolarprojectsDetailController);

    SolarprojectsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Solarprojects'];

    function SolarprojectsDetailController($scope, $rootScope, $stateParams, previousState, entity, Solarprojects) {
        var vm = this;

        vm.solarprojects = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('solarhackApp:solarprojectsUpdate', function(event, result) {
            vm.solarprojects = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
