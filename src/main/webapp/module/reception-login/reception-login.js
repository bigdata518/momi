define(function(require) {
    var _yy = require('yy');
    require('yy/form');
    require('yy/button');
    require('yy/label');
    require('crypto.md5');
    var _module = require('yy/module');
    var self = {};
    var _event = _yy.getEvent();
    var _message = _yy.getMessage();
    var _utils = _yy.getUtils();
    self.init = function(thisModule) {
        //新增帐号按钮
        var loginValidate = {
            receptionId: {
                success: function() {
                    var infoId = thisModule.findByKey('info-id');
                    infoId.setLabel('');
                },
                faliure: function() {
                    var infoId = thisModule.findByKey('info-id');
                    infoId.setLabel('帐号不能为空');
                }
            }
        };
        var loginButton = thisModule.findByKey('login-button');
        _event.bind(loginButton, 'click', function(thisCom) {
            var loginForm = thisModule.findByKey('login-form');
            var msg = loginForm.getData();
            //必填检测
            var validate = _utils.validate(msg, loginValidate);
            if (validate) {
                msg.act = 'RECEPTION_LOGIN';
                _message.send(msg);
            }
        });
        //
        _message.listen(loginButton, 'RECEPTION_LOGIN', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                //登录成功
                _yy.setSession(msg.data);
                thisModule.hide();
                thisModule.remove();
                if(msg.data.type === 'ADMIN') {
                    _module.loadModule('admin-home');
                } else {
                    _module.loadModule('reception-home');
                }
            } else {
                //登录失败
                var infoLogin = thisModule.findByKey('info-login');
                var info ='登录失败';
                if(msg.flag === 'FAILURE_ID_NOT_EXIST') {
                    info = '用户不存在';
                }
                infoLogin.setLabel(info);
            }
        });
    };
    return self;
});