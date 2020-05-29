/*
 * 配置文件
 */
layui.define(function (exports) {
    var configure = {
        //TODO:例子
        EXAMPLE_SESSION__LIST_ITEM_DATA: 'exampleListItemData', //session存储当前行数据
        EXAMPLE_LIST_HTTP_LIST: BASE_API + '/json/list.json', //列表数据
        EXAMPLE_DELETE_HTTP: BASE_API + '/json/delete.json', //添加数据
        ADD_HTTP: BASE_API + '/json/add.json', //添加数据
        EDIT_HTTP: BASE_API + '/json/add.json', //修改数据
        EXAMPLE_DETAIL_HTML: '/pages/member/detail.html' //详情
    }
    exports('configure', configure);
});