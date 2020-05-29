window.formVerifyFlag = true;
window.formVerifyFun = function () {
    $('.layui-colla-content').addClass('layui-show');//默认展开所有折叠
    formVerifyFlag = true;
    $('#submit').click();
    return formVerifyFlag;
};
layui.define([
    'jquery',
    'table',
    'form'
], function (exports) {
    var $ = layui.jquery;
    var table = layui.table;
    var form = layui.form;
    var token = '';
    //表单验证
    form.verify({
        isEmpty: function (value, item) {
            var $parentTr = $(item).closest('tr');
            if (!$parentTr.is(':hidden')) {
                if ($(item).attr('type') === 'checkbox') {
                    var checkName = $(item).attr('name');
                    if ($('input[name="' + checkName + '"]:checked').length === 0) {
                        window.formVerifyFlag = false;
                        return '多选框不能为空';
                    }
                }
                if (!($.trim(value))) {
                    window.formVerifyFlag = false;
                    return '必填项不能为空';
                }
            }
        },
        uname: function (value, item) {
            if (value) {
                if (new RegExp('[`~!@$%^&*()+=|{}\':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，、？]').test(value)) {
                    window.formVerifyFlag = false;
                    return '不能有特殊字符';
                }
            }
        }, cn: function (value, item) {
            if (value) {
                if (!new RegExp('^[\u4e00-\u9fa5]+$').test(value)) {
                    window.formVerifyFlag = false;
                    return '只能输入中文';
                }
            }
        }, znNum: function (value, item) {
            if (!new RegExp('^[0-9a-zA_Z]+$').test(value)) {
                window.formVerifyFlag = false;
                return '只能输入英文和数字';
            }
        }, upass: function (value, item) {
            if (value) {
                if (new RegExp('[/^[\\S]{6,12}$/]').test(value)) {
                    window.formVerifyFlag = false;
                    return '密码必须6到12位，且不能出现空格';
                }
            }
        }, identity: function (value, item) {
            if (value) {
                // 加权因子
                var weight_factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                // 校验码
                var check_code = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'];
                var code = value + "";
                var last = value[17]; //最后一个
                var seventeen = code.substring(0, 17);
                // ISO 7064:1983.MOD 11-2
                // 判断最后一位校验码是否正确
                var arr = seventeen.split("");
                var len = arr.length;
                var num = 0;
                for (var i = 0; i < len; i++) {
                    num = num + arr[i] * weight_factor[i];
                }

                // 获取余数
                var resisue = num % 11;
                var last_no = check_code[resisue];

                // 格式的正则
                // 正则思路
                /*
                第一位不可能是0
                第二位到第六位可以是0-9
                第七位到第十位是年份，所以七八位为19或者20
                十一位和十二位是月份，这两位是01-12之间的数值
                十三位和十四位是日期，是从01-31之间的数值
                十五，十六，十七都是数字0-9
                十八位可能是数字0-9，也可能是X
                */
                var idcard_patter = /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X])$/;
                // 判断格式是否正确
                var format = idcard_patter.test(value);
                // 返回验证结果，校验码和格式同时正确才算是合法的身份证号码
                if (!(last === last_no && format)) {
                    window.formVerifyFlag = false;
                    return '身份证号格式有误，请重新填写！';
                }
            }
        }, linkPhone: function (value, item) {
            if (value) {
                var phone = value;
                var regMobilePhone = new RegExp(/^1[34578]\d{9}$/);
                var regTelephone = new RegExp(/^((0\d{2,3})-?)(\d{7,8})(-(\d{3,}))?$/);
                if (!(regMobilePhone.test(phone) || regTelephone.test(phone))) {
                    window.formVerifyFlag = false;
                    return '电话号码格式有误,请重新输入!';
                }
            }
        }, email: function (value, item) {
            if (value) {
                var regEmail = new RegExp(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/);
                if (!regEmail.test(value)) {
                    window.formVerifyFlag = false;
                    return '邮箱格式错误';
                }
            }
        }
    });

    var configureCom = {
        errFun: function () {

        },
        /**
         * TODO:公有方法
         */
        putDataAjax: function (url, data) {
            return new Promise(function (resolve, reject) {
                data = $.extend(data, {token: token});
                var index = top.layer.load(1, {shade: 0.01});
                $.ajax({
                    url: url,
                    type: 'put',
                    cache: false,
                    headers: {
                        //Bearer是我的项目需要的,你可以按你的需求规范格式
                        'Authorization': token
                    },
                    data: data,
                    dataType: 'json',
                    success: function (data) {
                        top.layer.close(index);
                        configureCom.errFun(data);
                        if (data.code === 0) {
                            resolve(data);
                        } else {
                            return false;
                        }
                    },
                    error: function (data) {
                        configureCom.errFun(data);
                        reject(data);
                        top.layer.close(index);
                    }
                });
            });
        },
        putAjax: function (url, data) {
            return new Promise(function (resolve, reject) {
                data = $.extend(data, {token: token});
                var index = top.layer.load(1, {shade: 0.01});
                $.ajax({
                    url: url,
                    type: 'put',
                    cache: false,
                    headers: {
                        //Bearer是我的项目需要的,你可以按你的需求规范格式
                        'Authorization': token
                    },
                    contentType: 'application/json',
                    data: data,
                    dataType: 'json',
                    success: function (data) {
                        top.layer.close(index);
                        configureCom.errFun(data);
                        if (data.code == 0) {
                            resolve(data);
                        } else {
                            return false;
                        }
                    },
                    error: function (data) {
                        configureCom.errFun(data);
                        reject(data);
                        top.layer.close(index);
                    }
                });
            });
        },
        postAjax: function (url, data) {
            return new Promise(function (resolve, reject) {
                // data = $.extend(data, {token: token});
                data = JSON.stringify(data)
                var index = top.layer.load(1, {shade: 0.01});
                $.ajax({
                    url: url,
                    type: 'post',
                    cache: false,
                    headers: {
                        'token': token
                    },
                    contentType: 'application/json',
                    data: data,
                    dataType: 'json',
                    success: function (data) {
                        top.layer.close(index);
                        configureCom.errFun(data);
                        if (data.code == 0) {
                            resolve(data);
                        } else {
                            return false;
                        }
                    },
                    error: function (data) {
                        reject(data);
                        top.layer.close(index);
                    }
                });
            });
        },
        postDataAjax: function (url, data) {
            return new Promise(function (resolve, reject) {
                data = $.extend(data, {token: token});
                var index = top.layer.load(1, {shade: 0.01});
                $.ajax({
                    url: url,
                    type: 'post',
                    cache: false,
                    headers: {
                        //Bearer是我的项目需要的,你可以按你的需求规范格式
                        'token': token
                    },
                    // contentType: 'application/json',
                    data: data,
                    dataType: 'json',
                    success: function (data) {
                        top.layer.close(index);
                        configureCom.errFun(data);
                        if (data.code == 0) {
                            resolve(data);
                        } else {
                            return false;
                        }
                    },
                    error: function (data) {
                        top.layer.close(index);
                        configureCom.errFun(data);
                        reject(data);
                    }
                });
            });
        },
        getAjax: function (url, data) {
            return new Promise(function (resolve, reject) {
                data = $.extend(data, {token: token});
                var index = top.layer.load(1, {shade: 0.01});
                $.ajax({
                    url: url,
                    type: 'get',
                    cache: false,
                    headers: {
                        //Bearer是我的项目需要的,你可以按你的需求规范格式
                        'Authorization': token
                    },
                    data: data,
                    dataType: 'json',
                    success: function (data) {
                        top.layer.close(index);
                        configureCom.errFun(data);
                        if (data.code == 0) {
                            resolve(data);
                        } else {
                            return false;
                        }
                    },
                    error: function (data) {
                        configureCom.errFun(data);
                        top.layer.close(index);
                        reject(data);
                    }
                });
            });
        },
        deleteAjax: function (url, data) {
            return new Promise(function (resolve, reject) {
                data = $.extend(data, {token: token});
                var index = top.layer.load(1, {shade: 0.01});
                $.ajax({
                    url: url,
                    type: 'delete',
                    cache: false,
                    headers: {
                        //Bearer是我的项目需要的,你可以按你的需求规范格式
                        'Authorization': token
                    },
                    data: data,
                    dataType: 'json',
                    success: function (data) {
                        top.layer.close(index);
                        configureCom.errFun(data);
                        if (data.code === 0) {
                            resolve(data);
                        } else {
                            return false;
                        }
                    },
                    error: function (data) {
                        configureCom.errFun(data);
                        top.layer.close(index);
                        reject(data);
                    }
                });
            });
        },
        //表格重载
        tableReload: function (isrefrash, data, url) {
            if (isrefrash) {
                //刷新（留在当前页）
                table.reload('dataList', {
                    url: url,
                    where: data
                });
            } else {
                //刷新（并跳转到第一页）
                table.reload('dataList', {
                    url: url,
                    where: data,
                    page: {
                        curr: 1
                    }
                });
            }
        },
        //获取url地址中的page参数方法
        GetQueryString: function (name) {
            var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)');
            var r = window.location.search.substr(1).match(reg);
            if (r !== null) {
                return r[2];
            } else {
                return null;
            }
        },
        nocache: function (url, iframeId, layerIndex) {//链接上加时间戳，取消缓存
            if (iframeId) {
                iframeId = 'iframeId=' + iframeId + '&';
            } else {
                iframeId = '';
            }
            var time = new Date().getTime();
            var res = '';
            if (url.indexOf('nocache') === -1) {
                if (url.indexOf('?') === -1) {
                    res = url + '?';
                } else {
                    res = url + '&';
                }
                res += iframeId + 'layerIndex=' + layerIndex + '&nocache=' + time;
                return res;
            } else {
                var oldTime = configureCom.GetQueryString('nocache');
                res = url.replace(oldTime, time);
                return res;
            }
        },
        //表格搜索
        tableSearch: function (addData) {
            form.on('submit(formDemo)', function (data) {
                var fields = configureCom.inptHolder(data.field);
                fields = $.extend(fields, addData);
                table.reload('dataList', {
                    where: fields,
                    page: {
                        curr: 1
                    }
                });
                return false;
            });
        },
        // 判断表单input值是否等于他的placeholder
        inptHolder: function (fields) {
            $.each(fields, function (s, k) {
                if ($('input[name=' + s + ']')) {
                    var thatInpt = $('input[name=' + s + ']');
                    var text = thatInpt.attr('placeholder');
                    if (text) {
                        if (text === thatInpt.val()) {
                            fields[s] = '';
                            // delete fields[s];
                        }
                    }
                }
            });
            return fields;
        },
        tableRender: function (url, data, cols, tableId, size, height, toolbar) {
            var _height = height ? height : 'full-200';
            var _elem = tableId ? '#' + tableId : '#dataList';
            var _tableId = tableId ? tableId : 'dataList';
            var _toolbar = toolbar ? '#' + toolbar : false;
            var _limit = size || 10;
            return new Promise(function (resolve, reject) {
                //表格实例
                table.render({
                    elem: _elem,
                    url: url, //数据接口
                    method: 'post',
                    toolbar: _toolbar,
                    cellMinWidth: 80,
                    loading: true,
                    page: {
                        prev: '上一页',
                        next: '下一页',
                        layout: ['prev', 'page', 'next']
                    }, //是否开启分页
                    height: _height,
                    headers: {
                        'Authorization': token
                    },
                    request: {
                        limitName: 'size' //每页数据量的参数名，默认：limit
                    },
                    limit: _limit,
                    limits: configureCom.limits,
                    where: data,
                    parseData: function (res) { //res 即为原始返回的数据
                        return {
                            'success': res.success, //解析接口状态
                            'msg': res.msg, //解析提示文本
                            'total': res.data.total, //解析数据长度
                            'data': res.data.rows //解析数据列表
                        };
                    },
                    response: {
                        statusName: 'success', //数据状态的字段名称，默认：code
                        statusCode: 1, //成功的状态码，默认：0
                        msgName: 'msg', //状态信息的字段名称，默认：msg
                        countName: 'total', //数据总数的字段名称，默认：count
                        dataName: 'data' //数据列表的字段名称，默认：data
                    },
                    cols: cols,
                    id: _tableId
                });
            });
        },
    }
    exports('configureCom', configureCom);
});