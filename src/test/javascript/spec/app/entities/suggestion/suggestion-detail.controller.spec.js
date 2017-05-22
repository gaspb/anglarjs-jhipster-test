'use strict';

describe('Controller Tests', function() {

    describe('Suggestion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSuggestion, MockUserDashboard;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSuggestion = jasmine.createSpy('MockSuggestion');
            MockUserDashboard = jasmine.createSpy('MockUserDashboard');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Suggestion': MockSuggestion,
                'UserDashboard': MockUserDashboard
            };
            createController = function() {
                $injector.get('$controller')("SuggestionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterElasticsearchSampleApplicationApp:suggestionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
