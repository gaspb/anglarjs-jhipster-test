(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .factory('NewbieService', NewbieService);

    NewbieService.$inject = [];

    function NewbieService () {
        var service = {
        };

        var modalInstance = null;
        var resetModal = function () {
            modalInstance = null;
        };

        return service;


    }
})();
