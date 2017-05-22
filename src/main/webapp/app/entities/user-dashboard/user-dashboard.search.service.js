(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('UserDashboardSearch', UserDashboardSearch);

    UserDashboardSearch.$inject = ['$resource'];

    function UserDashboardSearch($resource) {
        var resourceUrl =  'api/_search/user-dashboards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
