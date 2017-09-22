var mainApp = angular.module("mainApp", ["angular-loading-bar", "ui.router", "ngAnimate", "ngGrid"]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
    routeConfig($stateProvider, $urlRouterProvider);
});
// route config
function routeConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider.state("event", {url: "/event", templateUrl: './htm/event.htm'})
    .state("event.all", {url: "/all", templateUrl: './htm/event/inbound.htm',controller: "eventInboundCtl"})
    .state("event.order", {url: "/order", templateUrl: './htm/event/order.htm',controller: "eventOrderCtl"})
    .state("event.jobSheet", {url: "/jobSheet", templateUrl: './htm/event/jobSheet.htm',controller: "eventJobSheetCtl"})
    .state("event.billing", {url: "/billing", templateUrl: './htm/event/billing.htm',controller: "eventBillingCtl"});
}
mainApp.factory("mineMessage", function ($rootScope) {
    return {
        publish: function (name, parameters) {
            $rootScope.$emit(name, parameters);
        },
        subscribe: function (name, listener) {
            $rootScope.$on(name, listener);
        }
    };
});
mainApp.service("mineHttp", function ($http, Upload) {
    this.send = function (method, url, params, callback) {
        var setting = {
            method: method,
            url: fullPath(url),
        };
        $.extend(setting, params);
        $http(setting).then(function successCallback(response) {
            callback(response.data);
        }, function errorCallback(response) {
            alert("request error");
        });
    };
});
mainApp.service("mineGrid", function ($http, $parse) {
    var defaultSetting = {
        requestUrl: undefined,
        requestMethod: "GET",
        multiSelect: false,
        enablePaging: true,
        enableSorting: true,
        useExternalSorting: true,
        showFooter: true,
        i18n: 'zh-cn',
        sortInfo: {fields: [], columns: [], directions: []}
    };

    this.gridInit = function (target, scope, options) {
        if (!angular.isString(target)) {
            console.error("[target] is not string object");
            return;
        }
        if (!angular.isString(options.requestUrl)) {
            console.error("[options.url] require is string");
            return;
        }
        scope.gridSelectedItems = [];
        var scopeSetting = {
            enablePaging: false,
            enableSorting: false,
            useExternalSorting: false,
            showFooter: false,
            selectedItems: scope.gridSelectedItems
        };
        // init angular grid
        var setting = {};
        $.extend(setting, defaultSetting, scopeSetting, options);
        $parse(target).assign(scope, setting);
        scope.gridQuery = function (params) {
            $http({
                method: setting.requestMethod,
                url: setting.requestUrl,
                params: params
            }).then(function successCallback(response) {
                var result = {data: []};
                if (angular.isFunction(scope.gridQueryCallback)) {
                    var callBackResult = scope.gridQueryCallback(response.data);
                    if (angular.isObject(callBackResult)) {
                        $.extend(result, callBackResult);
                    }
                }
                $parse(setting.data).assign(scope, result.data);
            }, function errorCallback(response) {
                console.error("Request failed");
            });
        };
    };
    this.gridPageInit = function (target, scope, options) {
        if (!angular.isString(target)) {
            console.error("[target] is not string object");
            return;
        }
        if (!angular.isString(options.requestUrl)) {
            console.error("[options.url] require is string");
            return;
        }
        scope.gridSelectedItems = [];
        scope.gridTotalServerItems = 0;
        scope.gridPagingOptions = {
            pageSizes: [20, 50, 100],
            pageSize: "20",
            currentPage: 1,
            totalServerItems: 0
        };
        var scopeSetting = {
            pagingOptions: scope.gridPagingOptions,
            selectedItems: scope.gridSelectedItems,
            totalServerItems: 'gridTotalServerItems'
        };
        // init angular grid
        var setting = {};
        $.extend(setting, defaultSetting, scopeSetting, options);
        $parse(target).assign(scope, setting);

        var queryFlag = false;
        var tempParams = {};
        var tempData = {};
        scope.gridPageQuery = function (params, data) {
            tempParams = params;
            tempData = data;
            if(scope.gridPagingOptions.currentPage != 1){
               queryFlag = true;
            }
            scope.gridPagingOptions.currentPage = 1;
            scope.gridPageLoadDataByAsync(scope.gridPagingOptions, scope.sortInfo, params, data);
        };

        scope.gridPageLoadDataByAsync = function (pageInfo, sortInfo, customParams, data) {
            var headers = {
                page_size: pageInfo.pageSize,
                page_number: pageInfo.currentPage
            };

            var params = {};
            if (angular.isObject(customParams)) {
                $.extend(params, customParams)
            }
            if (angular.isObject(sortInfo)) {
                $.extend(headers, {sort_field: sortInfo.field, sort_direction: sortInfo.direction});
            }
            if (!angular.isObject(data)) {
                data = {};
            }
            $http({
                method: setting.requestMethod,
                url: setting.requestUrl,
                headers: headers,
                params: params,
                data: data
            }).then(function successCallback(response) {
                var result = {data: [], total: 0};
                if (angular.isFunction(scope.gridPageQueryCallback)) {
                    var callBackResult = scope.gridPageQueryCallback(response.data);
                    if (angular.isObject(callBackResult)) {
                        $.extend(result, callBackResult);
                    }
                }
                $parse(setting.data).assign(scope, result.data);
                scope.gridTotalServerItems = result.total;
            }, function errorCallback(response) {
                console.error("Request failed");
            });
        };
        // binding event when page changed
        scope.$watch('gridPagingOptions', function (newVal, oldVal) {
           if(queryFlag){
               queryFlag = false;
               return;
            }
            if (newVal !== oldVal && (newVal.currentPage != oldVal.currentPage || newVal.pageSize != oldVal.pageSize)) {
                scope.gridPageLoadDataByAsync(scope.gridPagingOptions, scope.gridSortInfo, tempParams, tempData);
            }
        }, true);
        scope.$on('ngGridEventSorted', function (event, sortInfo) {
            if (sortInfo.fields[0] && sortInfo.directions[0]) {
                var sortInfoTemp = {field: sortInfo.fields[0], direction: sortInfo.directions[0]};
                if (scope.gridSortInfo && scope.gridSortInfo.field == sortInfoTemp.field && scope.gridSortInfo.direction == sortInfoTemp.direction) {
                    return;
                }
                scope.gridSortInfo = sortInfoTemp;
                scope.gridPageLoadDataByAsync(scope.gridPagingOptions, sortInfoTemp, tempParams, tempData);
            }
        });
    };
});
mainApp.directive("mineDate", function () {
    return {
        restrict: 'E',
        require: 'ngModel',
        template: "<div class='input-group date' style='float: left'>" +
        "<input style='width: 140px;' type='text'class='form-control input-sm' />" +
        "<span class='input-group-addon' style='cursor:pointer;'><span class='glyphicon glyphicon-calendar'></span></span></div>",
        link: function (scope, element, attrs, ngModel) {
            var dateText = $(element).children("input");
            $(dateText).datetimepicker({format: 'Y-m-d H:i:s'});
            $(dateText).change(function () {
                ngModel.$setViewValue($(this).val());
                console.log($(this).val());
            });
            ngModel.$render = function () {
                $(dateText).val(ngModel.$viewValue);
            }
            $(element).find("span[class='input-group-addon']").click(function(){
                $(dateText).trigger("focus");
            });
        },
        replace: true
    }
});