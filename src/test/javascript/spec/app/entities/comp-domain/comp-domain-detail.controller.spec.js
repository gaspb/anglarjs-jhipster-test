'use strict';

describe('Controller Tests', function() {

    describe('CompDomain Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCompDomain, MockCompetence, MockProject;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCompDomain = jasmine.createSpy('MockCompDomain');
            MockCompetence = jasmine.createSpy('MockCompetence');
            MockProject = jasmine.createSpy('MockProject');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CompDomain': MockCompDomain,
                'Competence': MockCompetence,
                'Project': MockProject
            };
            createController = function() {
                $injector.get('$controller')("CompDomainDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterElasticsearchSampleApplicationApp:compDomainUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
