(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        //********************** ADDED
        vm.hasComp = null;
        $(function () {
            $("#start_btn").on("click", function () {
                $("#start_main").load("app/home/ccm.html");
            });

        });
        $(function () {
            $("#projectload_btn").on("click", function () {
                $(document).ready(function() {
                    $("#project_main").load("app/entities/project/projects.html");
                });
            });

        });
       //***********
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
