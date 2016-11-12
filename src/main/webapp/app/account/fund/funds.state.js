(function() {
    'use strict';

    angular
        .module('solarhackApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('funds', {
            parent: 'account',
            url: '/funds',
            data: {
                authorities: ['ROLE_INVESTOR'],
                pageTitle: 'Invest Funds'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/fund/funds.html',
                    controller: 'FundsController',
                    controllerAs: 'vm'
                }
            },
         });
    }
})();
