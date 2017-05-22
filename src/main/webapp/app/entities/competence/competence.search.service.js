(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('CompetenceSearch', CompetenceSearch);

    CompetenceSearch.$inject = ['$resource'];

    function CompetenceSearch($resource) {
        var resourceUrl =  'api/_search/competences/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
