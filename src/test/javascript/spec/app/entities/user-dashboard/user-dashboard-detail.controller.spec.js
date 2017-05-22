'use strict';

describe('Controller Tests', function() {

    describe('UserDashboard Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserDashboard, MockUser, MockProject, MockSuggestion, MockCompetence;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserDashboard = jasmine.createSpy('MockUserDashboard');
            MockUser = jasmine.createSpy('MockUser');
            MockProject = jasmine.createSpy('MockProject');
            MockSuggestion = jasmine.createSpy('MockSuggestion');
            MockCompetence = jasmine.createSpy('MockCompetence');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserDashboard': MockUserDashboard,
                'User': MockUser,
                'Project': MockProject,
                'Suggestion': MockSuggestion,
                'Competence': MockCompetence
            };
            createController = function() {
                $injector.get('$controller')("UserDashboardDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterElasticsearchSampleApplicationApp:userDashboardUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
