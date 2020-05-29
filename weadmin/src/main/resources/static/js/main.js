const BASE_API = '/api';

layui.config({
    base: '/js/', //假设这是你存放拓展模块的根目录
    version: true
    // version: '20191128'
}).extend({
    admin: 'admin', //平台初始化模块
    configure: 'config/configure', //公共配置信息
    configureCom: 'config/configureCom', //公共配置方法
    sessionFun: 'utils/sessionFun', //存取session模块（依赖storage）
    storage: 'utils/storage', //封装storage底层模块
    json: 'utils/json2', //json格式转换
});
