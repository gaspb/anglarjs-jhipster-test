(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-dashboard', {
            parent: 'entity',
            url: '/user-dashboard',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterElasticsearchSampleApplicationApp.userDashboard.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-dashboard/user-dashboards.html',
                    controller: 'UserDashboardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userDashboard');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-dashboard-detail', {
            parent: 'user-dashboard',
            url: '/user-dashboard/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterElasticsearchSampleApplicationApp.userDashboard.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-dashboard/user-dashboard-detail.html',
                    controller: 'UserDashboardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userDashboard');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserDashboard', function($stateParams, UserDashboard) {
                    return UserDashboard.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-dashboard',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-dashboard-detail.edit', {
            parent: 'user-dashboard-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-dashboard/user-dashboard-dialog.html',
                    controller: 'UserDashboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserDashboard', function(UserDashboard) {
                            return UserDashboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-dashboard.new', {
            parent: 'user-dashboard',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-dashboard/user-dashboard-dialog.html',
                    controller: 'UserDashboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                phone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-dashboard', null, { reload: 'user-dashboard' });
                }, function() {
                    $state.go('user-dashboard');
                });
            }]
        })
        .state('user-dashboard.edit', {
            parent: 'user-dashboard',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-dashboard/user-dashboard-dialog.html',
                    controller: 'UserDashboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserDashboard', function(UserDashboard) {
                            return UserDashboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-dashboard', null, { reload: 'user-dashboard' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-dashboard.delete', {
            parent: 'user-dashboard',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-dashboard/user-dashboard-delete-dialog.html',
                    controller: 'UserDashboardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserDashboard', function(UserDashboard) {
                            return UserDashboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-dashboard', null, { reload: 'user-dashboard' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
