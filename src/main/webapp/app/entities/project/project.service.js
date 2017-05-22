(function() {
    'use strict';
    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('Project', Project);

    Project.$inject = ['$resource', 'DateUtils'];

    function Project ($resource, DateUtils) {
        var resourceUrl =  'api/projects/:title';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creationDate = DateUtils.convertLocalDateFromServer(data.creationDate);
                        data.completionDate = DateUtils.convertLocalDateFromServer(data.completionDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.creationDate = DateUtils.convertLocalDateToServer(copy.creationDate);
                    copy.completionDate = DateUtils.convertLocalDateToServer(copy.completionDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.creationDate = DateUtils.convertLocalDateToServer(copy.creationDate);
                    copy.completionDate = DateUtils.convertLocalDateToServer(copy.completionDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
