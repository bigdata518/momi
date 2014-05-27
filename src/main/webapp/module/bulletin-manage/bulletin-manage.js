define(function(require) {
    var _yy = require('yy');
    require('yy/label');
    require('yy/form');
    require('yy/button');
    var self = {};
    var _event = _yy.getEvent();
    var _message = _yy.getMessage();
    var _utils = _yy.getUtils();
    self.init = function(thisModule) {
        var receptionForm = thisModule.findByKey('reception-form');
        var receptionCreateTime = thisModule.findByKey('reception-create-time');
        //
        var updateReceptionButton = thisModule.findByKey('update-reception-button');
        _event.bind(updateReceptionButton, 'click', function(thisCom) {
            var msg = receptionForm.getData();
            msg.act = 'UPDATE_BULLETIN';
            _message.send(msg);
        });
        //
        var customerForm = thisModule.findByKey('customer-form');
        var customerCreateTime = thisModule.findByKey('customer-create-time');
        //
        var updateCustomerButton = thisModule.findByKey('update-customer-button');
        _event.bind(updateCustomerButton, 'click', function(thisCom) {
            var msg = customerForm.getData();
            msg.act = 'UPDATE_BULLETIN';
            _message.send(msg);
        });
        //
        var updateReceptionInfo = thisModule.findByKey('update-reception-info');
        var updateCustomerInfo = thisModule.findByKey('update-customer-info');
        _message.listen(thisModule, 'UPDATE_BULLETIN', function(thisCom, msg) {
            var data = msg.data;
            if (data.bulletinType === 'RECEPTION') {
                //客服公告
                if (msg.flag === 'SUCCESS') {
                    updateReceptionInfo.setLabel('保存成功');
                } else {
                    updateReceptionInfo.setLabel('保存失败');
                }
            } else if (data.bulletinType === 'CUSTOMER') {
                //玩家公告
                if (msg.flag === 'SUCCESS') {
                    updateCustomerInfo.setLabel('保存成功');
                } else {
                    updateCustomerInfo.setLabel('保存失败');
                }
            }
        });
        //
        _message.listen(thisModule, 'INQUIRE_BULLETIN', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                var dataItem;
                for (var index = 0; index < 2 && index < data.length; index++) {
                    dataItem = data[index];
                    if (dataItem.bulletinType === 'RECEPTION') {
                        //客服公告
                        receptionForm.setData('content', dataItem.content);
                        receptionCreateTime.setLabel(dataItem.createTime);
                    } else if (dataItem.bulletinType === 'CUSTOMER') {
                        //玩家公告
                        customerForm.setData('content', dataItem.content);
                        customerCreateTime.setLabel(dataItem.createTime);
                    }
                }
            }
        });
        //
        _message.send({
            act: 'INQUIRE_BULLETIN'
        });
    };
    return self;
});