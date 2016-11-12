(function() {
    'use strict';

    angular
        .module('solarhackApp')
        .controller('InvestorDetailController', InvestorDetailController);

    InvestorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Investor', 'User', 'Solarprojects'];

    function InvestorDetailController($scope, $rootScope, $stateParams, previousState, entity, Investor, User, Solarprojects) {
        var vm = this;

        vm.investor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('solarhackApp:investorUpdate', function(event, result) {
            vm.investor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
