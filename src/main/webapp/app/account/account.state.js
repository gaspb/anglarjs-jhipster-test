(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$qProvider'];

    function stateConfig($stateProvider, $qProvider) {
        $stateProvider.state('account', {
            abstract: true,
            parent: 'app'
        });
        $qProvider.errorOnUnhandledRejections(false);
    }
})();
