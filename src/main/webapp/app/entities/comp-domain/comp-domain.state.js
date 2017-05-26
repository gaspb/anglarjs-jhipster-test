(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comp-domain', {
            parent: 'entity',
            url: '/comp-domain?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterElasticsearchSampleApplicationApp.compDomain.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comp-domain/comp-domains.html',
                    controller: 'CompDomainController',
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
                    $translatePartialLoader.addPart('compDomain');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('comp-domain-detail', {
            parent: 'comp-domain',
            url: '/comp-domain/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterElasticsearchSampleApplicationApp.compDomain.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comp-domain/comp-domain-detail.html',
                    controller: 'CompDomainDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('compDomain');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CompDomain', function($stateParams, CompDomain) {
                    return CompDomain.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comp-domain',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comp-domain-detail.edit', {
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comp-domain/comp-domain-dialog.html',
                    controller: 'CompDomainDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CompDomain', function(CompDomain) {
                            return CompDomain.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comp-domain.new', {
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comp-domain/comp-domain-dialog.html',
                    controller: 'CompDomainDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('comp-domain', null, { reload: 'comp-domain' });
                }, function() {
                    $state.go('comp-domain');
                });
            }]
        })
        .state('comp-domain.edit', {
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comp-domain/comp-domain-dialog.html',
                    controller: 'CompDomainDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CompDomain', function(CompDomain) {
                            return CompDomain.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comp-domain', null, { reload: 'comp-domain' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comp-domain.delete', {
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comp-domain/comp-domain-delete-dialog.html',
                    controller: 'CompDomainDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CompDomain', function(CompDomain) {
                            return CompDomain.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comp-domain', null, { reload: 'comp-domain' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
