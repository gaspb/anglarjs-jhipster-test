(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state','Project'];

    function HomeController ($scope, Principal, LoginService, $state, Project) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        //********************** ADDED
        vm.projects = [];
        vm.hasComp = null;
        $(function () {
            $("#start_btn").on("click", function () {
                $("#start_main").load("app/home/ccm.html");
            });

        });
        $(function () {
            $("#start_pjct").on("click", function () {
                $("#section2").slideToggle(600,function(){
                    Project.query(function(result) {
                        vm.projects = result;
                    });
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
