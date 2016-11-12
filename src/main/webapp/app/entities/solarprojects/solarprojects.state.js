(function() {
    'use strict';

    angular
        .module('solarhackApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('solarprojects', {
            parent: 'entity',
            url: '/solarprojects?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'solarhackApp.solarprojects.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solarprojects/solarprojects.html',
                    controller: 'SolarprojectsController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('solarprojects');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('solarprojects-detail', {
            parent: 'entity',
            url: '/solarprojects/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'solarhackApp.solarprojects.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solarprojects/solarprojects-detail.html',
                    controller: 'SolarprojectsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('solarprojects');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Solarprojects', function($stateParams, Solarprojects) {
                    return Solarprojects.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'solarprojects',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('solarprojects-detail.edit', {
            parent: 'solarprojects-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solarprojects/solarprojects-dialog.html',
                    controller: 'SolarprojectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Solarprojects', function(Solarprojects) {
                            return Solarprojects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solarprojects.new', {
            parent: 'solarprojects',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solarprojects/solarprojects-dialog.html',
                    controller: 'SolarprojectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                productname: null,
                                productType: null,
                                size: null,
                                cost: null,
                                riskscore: null,
                                vendor_id: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('solarprojects', null, { reload: 'solarprojects' });
                }, function() {
                    $state.go('solarprojects');
                });
            }]
        })
        .state('solarprojects.edit', {
            parent: 'solarprojects',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solarprojects/solarprojects-dialog.html',
                    controller: 'SolarprojectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Solarprojects', function(Solarprojects) {
                            return Solarprojects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solarprojects', null, { reload: 'solarprojects' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solarprojects.delete', {
            parent: 'solarprojects',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solarprojects/solarprojects-delete-dialog.html',
                    controller: 'SolarprojectsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Solarprojects', function(Solarprojects) {
                            return Solarprojects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solarprojects', null, { reload: 'solarprojects' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
