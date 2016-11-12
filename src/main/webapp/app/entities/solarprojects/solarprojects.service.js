(function() {
    'use strict';
    angular
        .module('solarhackApp')
        .factory('Solarprojects', Solarprojects);

    Solarprojects.$inject = ['$resource'];

    function Solarprojects ($resource) {
        var resourceUrl =  'api/solarprojects/:id';

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
