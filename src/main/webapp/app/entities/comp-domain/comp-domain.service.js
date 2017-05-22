(function() {
    'use strict';
    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('CompDomain', CompDomain);

    CompDomain.$inject = ['$resource'];

    function CompDomain ($resource) {
        var resourceUrl =  'api/comp-domains/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
