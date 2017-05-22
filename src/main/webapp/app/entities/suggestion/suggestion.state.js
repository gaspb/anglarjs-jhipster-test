(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('suggestion', {
            parent: 'entity',
            url: '/suggestion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterElasticsearchSampleApplicationApp.suggestion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/suggestion/suggestions.html',
                    controller: 'SuggestionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('suggestion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('suggestion-detail', {
            parent: 'suggestion',
            url: '/suggestion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterElasticsearchSampleApplicationApp.suggestion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/suggestion/suggestion-detail.html',
                    controller: 'SuggestionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('suggestion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Suggestion', function($stateParams, Suggestion) {
                    return Suggestion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'suggestion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('suggestion-detail.edit', {
            parent: 'suggestion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suggestion/suggestion-dialog.html',
                    controller: 'SuggestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Suggestion', function(Suggestion) {
                            return Suggestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('suggestion.new', {
            parent: 'suggestion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suggestion/suggestion-dialog.html',
                    controller: 'SuggestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                description: null,
                                image: null,
                                imageContentType: null,
                                creationDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('suggestion', null, { reload: 'suggestion' });
                }, function() {
                    $state.go('suggestion');
                });
            }]
        })
        .state('suggestion.edit', {
            parent: 'suggestion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suggestion/suggestion-dialog.html',
                    controller: 'SuggestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Suggestion', function(Suggestion) {
                            return Suggestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('suggestion', null, { reload: 'suggestion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('suggestion.delete', {
            parent: 'suggestion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suggestion/suggestion-delete-dialog.html',
                    controller: 'SuggestionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Suggestion', function(Suggestion) {
                            return Suggestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('suggestion', null, { reload: 'suggestion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
