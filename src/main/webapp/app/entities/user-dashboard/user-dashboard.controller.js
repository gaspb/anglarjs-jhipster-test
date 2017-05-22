(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('UserDashboardController', UserDashboardController);

    UserDashboardController.$inject = ['UserDashboard', 'UserDashboardSearch'];

    function UserDashboardController(UserDashboard, UserDashboardSearch) {

        var vm = this;

        vm.userDashboards = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            UserDashboard.query(function(result) {
                vm.userDashboards = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            UserDashboardSearch.query({query: vm.searchQuery}, function(result) {
                vm.userDashboards = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
