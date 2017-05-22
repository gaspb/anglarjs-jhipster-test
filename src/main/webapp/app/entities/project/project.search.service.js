(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('ProjectSearch', ProjectSearch);

    ProjectSearch.$inject = ['$resource'];

    function ProjectSearch($resource) {
        var resourceUrl =  'api/_search/projects/:title';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
