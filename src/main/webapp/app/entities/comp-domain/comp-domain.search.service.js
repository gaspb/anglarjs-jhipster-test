(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('CompDomainSearch', CompDomainSearch);

    CompDomainSearch.$inject = ['$resource'];

    function CompDomainSearch($resource) {
        var resourceUrl =  'api/_search/comp-domains/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
