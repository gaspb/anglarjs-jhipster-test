(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('SuggestionSearch', SuggestionSearch);

    SuggestionSearch.$inject = ['$resource'];

    function SuggestionSearch($resource) {
        var resourceUrl =  'api/_search/suggestions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
