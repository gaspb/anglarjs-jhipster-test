(function () {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('JhiLanguageService', JhiLanguageService);

    JhiLanguageService.$inject = ['$q', '$http', '$translate', 'LANGUAGES'];

    function JhiLanguageService ($q, $http, $translate, LANGUAGES) {
        var service = {
            getAll: getAll,
            getCurrent: getCurrent
        };

        return service;

        function getAll () {
            var deferred = $q.defer();
            deferred.resolve(LANGUAGES);
            return deferred.promise;
        }

        function getCurrent () {
            var deferred = $q.defer();
            var language = 'fr';

            deferred.resolve(language);

            return deferred.promise;
        }
    }
})();
