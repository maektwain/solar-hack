'use strict';

describe('Controller Tests', function() {

    describe('Investor Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInvestor, MockUser, MockSolarprojects;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInvestor = jasmine.createSpy('MockInvestor');
            MockUser = jasmine.createSpy('MockUser');
            MockSolarprojects = jasmine.createSpy('MockSolarprojects');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Investor': MockInvestor,
                'User': MockUser,
                'Solarprojects': MockSolarprojects
            };
            createController = function() {
                $injector.get('$controller')("InvestorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'solarhackApp:investorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
