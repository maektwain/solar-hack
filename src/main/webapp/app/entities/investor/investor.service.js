(function() {
    'use strict';
    angular
        .module('solarhackApp')
        .factory('Investor', Investor);

    Investor.$inject = ['$resource'];

    function Investor ($resource) {
        var resourceUrl =  'api/investors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
