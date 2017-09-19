mainApp.controller("eventOrderCtl",function($scope, mineGrid){
    $scope.rootUrl = rootUrl();
    $("#eventTimeBegin").datetimepicker({format: 'Y-m-d H:i:s'});
    $("#eventTimeEnd").datetimepicker({format: 'Y-m-d H:i:s'});
    $scope.orderQuery = {};
    $scope.orderQuery.eventTimeBegin = today();
    mineGrid.gridPageInit("gridOptions", $scope, {
            data: 'myData',
            multiSelect: false,
            enableCellSelection: true,
            enableRowSelection: false,
            enableCellEdit: true,
            requestMethod: "POST",
            requestUrl: fullPath("view/order/events"),
            columnDefs: [
                {field: 'mark', displayName: '标记', width:"150"},
                {field: 'eventId', displayName: 'eventId', width:"100"},
                {field: 'orderNumber', displayName: 'orderNumber'},
                {field: 'erpNumber', displayName: 'erpNumber'},
                {field: 'eventType', displayName: 'eventType'},
                {field: 'eventTime', displayName: 'eventTime'},
                {field: 'latitude', displayName: 'latitude'},
                {field: 'longitude', displayName: 'longitude'},
                {field: 'loss', displayName: 'loss',width:"80"},
                {field: 'damage', displayName: 'damage',width:"80"},
                {field: 'fileNames', displayName: 'fileNames'},
                {field: 'truckPlate', displayName: 'truckPlate'},
                {field: 'remark', displayName: 'remark'}
            ]
        });
    $scope.gridPageQueryCallback = function (data) {
            return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.orderQuery);
    };
    $scope.query();
});
mainApp.controller("eventBillingCtl",function($scope, mineGrid){
    $scope.rootUrl = rootUrl();
    $("#createdOnBegin").datetimepicker({format: 'Y-m-d H:i:s'});
    $scope.billingQuery = {};
    $scope.billingQuery.createdOnBegin = today();
    $("#createdOnEnd").datetimepicker({format: 'Y-m-d H:i:s'});
    mineGrid.gridPageInit("gridOptions", $scope, {
            data: 'myData',
            multiSelect: false,
            enableCellSelection: true,
            enableRowSelection: false,
            enableCellEdit: true,
            requestMethod: "POST",
            requestUrl: fullPath("view/billing/events"),
            columnDefs: [
                {field: 'mark', displayName: '标记', width:"150"},
                {field: 'createdOn', displayName: '接收时间', width:"150"},
                {field: 'message', displayName: '推送内容'}
            ]
        });
    $scope.gridPageQueryCallback = function (data) {
            return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.billingQuery);
    };
    $scope.query();
});
mainApp.controller("eventJobSheetCtl",function($scope, mineGrid){
    $scope.rootUrl = rootUrl();
    $("#eventTimeBegin").datetimepicker({format: 'Y-m-d H:i:s'});
    $("#eventTimeEnd").datetimepicker({format: 'Y-m-d H:i:s'});
    $scope.jobSheetQuery = {};
    $scope.jobSheetQuery.eventTimeBegin = today();
    mineGrid.gridPageInit("gridOptions", $scope, {
            data: 'myData',
            multiSelect: false,
            enableCellSelection: true,
            enableRowSelection: false,
            enableCellEdit: true,
            requestMethod: "POST",
            requestUrl: fullPath("view/jobsheet/events"),
            columnDefs: [
                {field: 'mark', displayName: '标记', width:"200", sortable: false},
                {field: 'eventId', displayName: 'eventId', width:"150"},
                {field: 'eventType', displayName: 'eventType', width:"150"},
                {field: 'jobSheetNumber', displayName: 'jobSheetNumber', sortable: false},
                {field: 'externalShipmentId', displayName: 'externalShipmentId', sortable: false},
                {field: 'eventTime', displayName: 'eventTime'}
            ]
        });
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.jobSheetQuery, 1);
    };

    $scope.query();
});

mainApp.controller("eventInboundCtl",function($scope, mineGrid){
    $scope.rootUrl = rootUrl();
    $("#createdOnBegin").datetimepicker({format: 'Y-m-d H:i:s'});
    $("#createdOnEnd").datetimepicker({format: 'Y-m-d H:i:s'});

    $scope.inboundQuery = {};
    $scope.inboundQuery.createdOnBegin = today();

    mineGrid.gridPageInit("gridOptions", $scope, {
            data: 'myData',
            multiSelect: false,
            enableCellSelection: true,
            enableRowSelection: false,
            enableCellEdit: true,
            requestMethod: "POST",
            requestUrl: fullPath("view/inbound/events"),
            columnDefs: [
                {field: 'createdOn', displayName: '请求时间', width:"150"},
                {field: 'payload', displayName: '内容'}
            ]
        });
    $scope.gridPageQueryCallback = function (data) {
            return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.inboundQuery);
    };
    $scope.query();
});