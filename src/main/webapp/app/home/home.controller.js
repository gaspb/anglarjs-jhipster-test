(function() {
    'use strict';

    angular
        .module('jhipsterElasticsearchSampleApplicationApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Project', 'paginationConstants', 'DataUtils'];

    function HomeController ($scope, Principal, LoginService, $state, Project, paginationConstants, DataUtils) {
        var vm = this;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        //********************** ADDED
        vm.projects=[];
        vm.hasComp = null;
        //test
        $scope.isCollapsed = [true, true, true, true, true];
        vm.pjctIsLoaded = false;




      /**
        $(function () {
            $("#start_btn").on("click", function () {
                $("#start_main").slideDown(600);
            });
        });
        $(function () {
            $(".start-btn").on("click", function () {
                $(".start-main").each(function() {
                    if (this.hasClass("visible")){
                        this.slideUp(600);
                        this.removeClass("visible");
                    };
                });

            });

        });
       **/



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
        //**COLLAPSE FUNCTION
        vm.collapseThis = function(i){
            if(i==4&&!vm.pjctIsLoaded) { // LOAD PROJECTS
                vm.pjctIsLoaded=true;
                Project.query(function(result) {
                    vm.projects = result;
                });
            };
            //collapse all when uncollapsing one
            for( var j=0; j<$scope.isCollapsed.length;j++) {
                if (j == i) {
                    $scope.isCollapsed[j]=!$scope.isCollapsed[j]
                } else {
                    $scope.isCollapsed[j]=true;
                }
            }}; //**/
//TEST --- COPIED FROM PROJECT.CONTROLLER.JS --- //
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.clear = clear;
        vm.loadAll = loadAll;
        vm.search = search;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;



        function loadAll () {


                Project.query({
                    page: vm.page,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data) {
                for (var i = 0; i < data.length; i++) {
                    vm.projects.push(data[i]);
                }
            }

            function onError(error) {
                console.log(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.projects = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        function clear () {
            vm.projects = [];
            vm.links = {
                last: 0
            };
            vm.page = 0;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.searchQuery = null;
            vm.currentSearch = null;
            vm.loadAll();
        }

        function search (searchQuery) {
            if (!searchQuery){
                return vm.clear();
            }
            vm.projects = [];
            vm.links = {
                last: 0
            };
            vm.page = 0;
            vm.predicate = '_score';
            vm.reverse = false;
            vm.currentSearch = searchQuery;
            vm.loadAll();
        }










    }



})();
