(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('JhiLanguageController', JhiLanguageController);

    JhiLanguageController.$inject = ['$translate', 'JhiLanguageService', 'tmhDynamicLocale'];

    function JhiLanguageController ($translate, JhiLanguageService, tmhDynamicLocale) {
        var vm = this;

        vm.changeLanguage = changeLanguage;
        vm.languages = null;

        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        function changeLanguage (languageKey) {
            $translate.use('fr');
            tmhDynamicLocale.set('fr');
        }
    }
})();
