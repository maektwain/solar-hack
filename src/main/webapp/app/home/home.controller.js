(function() {
    'use strict';

    angular
        .module('solarhackApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'Solarprojects','$state' , 'AlertService'];

    function HomeController ($scope, Principal, LoginService,Solarprojects, $state, AlertService ) {
        var vm = this;

        Solarprojects.query({

                    }, onSuccess, onError);

        function onSuccess(data, headers) {

                        vm.solarprojects = data;

                    }
                    function onError(error) {
                        AlertService.error(error.data.message);
                    }

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
