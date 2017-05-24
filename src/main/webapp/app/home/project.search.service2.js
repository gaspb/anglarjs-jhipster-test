(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('ProjectSearch2', ProjectSearch2);

    ProjectSearch2.$inject = ['$resource'];

    function ProjectSearch2($resource) {
        var resourceUrl =  'api/_search2/projects/:title';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
