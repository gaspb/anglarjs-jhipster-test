(function() {
    'use strict';
    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('UserDashboard', UserDashboard);

    UserDashboard.$inject = ['$resource'];

    function UserDashboard ($resource) {
        var resourceUrl =  'api/user-dashboards/:id';

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
