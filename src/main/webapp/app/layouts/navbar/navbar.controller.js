(function() {
    'use strict';

   var app= angular
        .module('jhipsterElasticsearchSampleApplicationApp');
       app .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', '$rootScope', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function NavbarController ($state, $rootScope, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;


        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;


        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();

            $state.go('newbie').then(Auth.logout());
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
