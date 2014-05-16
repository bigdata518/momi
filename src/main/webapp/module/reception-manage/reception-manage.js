define(function(require) {
    var _yy = require('yy');
    require('yy/form');
    require('yy/label');
    require('yy/panel');
    require('yy/button');
    require('yy/list');
    var self = {};
    var _event = _yy.getEvent();
    var _message = _yy.getMessage();
    var _utils = _yy.getUtils();
    self.init = function(thisModule) {
        //初始化列表
        var receptionList = thisModule.findChildByKey('reception-list');
        receptionList.init({
            key: 'receptionId',
            itemClazz: '',
            itemDataToHtml: function(itemData) {
                var result = '<div class="inline_block w200">' + itemData.receptionId + '</div>'
                        + '<div class="inline_block w200">' + itemData.receptionName + '</div>'
                        + '<div class="inline_block w200">' + itemData.type + '</div>';
                if (itemData.type !== 'ADMIN') {
                    result += '<div id="' + itemData.receptionId + '-delete-button" class="button link float_right">删除</div>';
                }
                return result;
            },
            itemCompleted: function(itemCom) {
                var data = itemCom.getData();
                if (data.type !== 'ADMIN') {
                    var deleteButtonId = itemCom.key + '-delete-button';
                    var deleteButton = itemCom.findChildByKey(deleteButtonId);
                    _event.bind(deleteButton, 'click', function(thisCom) {
                        var msg = {
                            act: 'DELETE_RECEPTION',
                            receptionId: itemCom.key
                        };
                        _message.send(msg);
                    });
                }
            }
        });
        //帐号列表消息事件
        _message.listen(receptionList, 'INSERT_RECEPTION', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                thisCom.addItemDataFirst(data);
            }
        });
        _message.listen(receptionList, 'DELETE_RECEPTION', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var receptionId = msg.data.receptionId;
                thisCom.removeItem(receptionId);
            }
        });
        _message.listen(receptionList, 'INQUIRE_RECEPTION', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                if (data.length > 0) {
                    //有记录
                    thisCom.setPageIndex(msg.pageIndex);
                    thisCom.setPageSize(msg.pageSize);
                    thisCom.loadData(data);
                    //
                    var moreButton = thisModule.findChildByKey('more-button');
                    if (msg.pageNum > msg.pageIndex) {
                        moreButton.show();
                    } else {
                        moreButton.hide();
                    }
                } else {
                    var userForm = thisModule.findChildByKey('user-form');
                    userForm.show();
                }
            }
        });
        //添加帐号表单按钮
        var toServiceFormButton = thisModule.findChildByKey('to-reception-form-button');
        _event.bind(toServiceFormButton, 'click', function(thisCom) {
            var receptionForm = thisModule.findChildByKey('reception-form');
            if (receptionForm.isVisible()) {
                receptionForm.hide();
            } else {
                receptionForm.show();
            }
        });
        //新增帐号按钮
        var userValidate = {
            receptionId: {
                success: function() {
                    var infoId = thisModule.findChildByKey('info-id');
                    infoId.setLabel('');
                },
                faliure: function() {
                    var infoId = thisModule.findChildByKey('info-id');
                    infoId.setLabel('帐号不能为空');
                }
            },
            receptionName: {
                success: function() {
                    var infoName = thisModule.findChildByKey('info-name');
                    infoName.setLabel('');
                },
                faliure: function() {
                    var infoName = thisModule.findChildByKey('info-name');
                    infoName.setLabel('姓名不能为空');
                }
            },
            type: {
                success: function() {
                    var infoType = thisModule.findChildByKey('info-type');
                    infoType.setLabel('');
                },
                faliure: function() {
                    var infoType = thisModule.findChildByKey('info-type');
                    infoType.setLabel('类型不能为空');
                }
            }
        };
        var insertServiceButton = thisModule.findChildByKey('insert-reception-button');
        _event.bind(insertServiceButton, 'click', function(thisCom) {
            var receptionForm = thisModule.findChildByKey('reception-form');
            var msg = receptionForm.getData();
            //必填检测
            var validate = _utils.validate(msg, userValidate);
            if (validate) {
                msg.act = 'INSERT_RECEPTION';
                _message.send(msg);
            }
        });
        //页面初始化
        _message.send({
            act: 'INQUIRE_RECEPTION',
            pageIndex: 1,
            pageSize: 10
        });
    };
    return self;
});