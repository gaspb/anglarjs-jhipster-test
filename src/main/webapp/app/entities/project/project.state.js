(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('project', {
            parent: 'entity',
            url: '/project',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterElasticsearchSampleApplicationApp.project.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/project/projects.html',
                    controller: 'ProjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('project');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('project-detail', {
            parent: 'project',
            url: '/project/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterElasticsearchSampleApplicationApp.project.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/project/project-detail.html',
                    controller: 'ProjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('project');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Project', function($stateParams, Project) {
                    return Project.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'project',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('project-detail.edit', {
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project/project-dialog.html',
                    controller: 'ProjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Project', function(Project) {
                            return Project.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('project.new', {
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project/project-dialog.html',
                    controller: 'ProjectDialogController',
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
                                completionDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('project', null, { reload: 'project' });
                }, function() {
                    $state.go('project');
                });
            }]
        })
        .state('project.edit', {
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project/project-dialog.html',
                    controller: 'ProjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Project', function(Project) {
                            return Project.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('project', null, { reload: 'project' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('project.delete', {
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project/project-delete-dialog.html',
                    controller: 'ProjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Project', function(Project) {
                            return Project.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('project', null, { reload: 'project' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
