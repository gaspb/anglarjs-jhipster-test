(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('ProjectCurrent', ProjectCurrent);

    ProjectCurrent.$inject = ['$resource'];

    function ProjectCurrent($resource) {
        var resourceUrl =  'api/projects/my/:title';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
