/*
 * @Author: qin
 * @Date:   2018-08-28
 * +----------------------------------------------------------------------
 * | admin [ 后台管理系统公共配置信息 ]
 * | 存储session
 * +----------------------------------------------------------------------
 */
layui.define(['storage'], function (exports) {
    var storage = layui.storage;
    var sessionFun = {
        get: function (TokenKey) {
            return storage.get(TokenKey);
        },
        set: function (TokenKey, token) {
            return storage.set(TokenKey, token);
        },
        remove: function (TokenKey) {
            return storage.remove(TokenKey);
        },
        clear: function () {
            return storage.clear();
        }
    };
    exports('sessionFun', sessionFun);
});