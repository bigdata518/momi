define(function(require) {
    var _yy = require('yy');
    require('yy/panel');
    require('yy/button');
    require('yy/list');
    var _module = require('yy/module');
    var self = {};
    var _event = _yy.getEvent();
    var _message = _yy.getMessage();
    var _utils = _yy.getUtils();
    self.init = function(thisModule) {
        var waitPanel = thisModule.findByKey('wait-panel');
        var chatPanel = thisModule.findByKey('chat-panel');
        var customerName = _yy.getSession('customerName');
        var chatForm = thisModule.findByKey('chat-form');
        document.title = customerName;
        var chatMessageList = thisModule.findByKey('chat-message-list');
        chatMessageList.init({
            key: 'messageId',
            itemClazz: '',
            itemDataToHtml: function(itemData) {
                var result;
                if (itemData.from === 's') {
                    result = '<div class="chat_message_friend">';
                } else {
                    result = '<div class="chat_message_me">';
                }
                var createTime = _utils.shortDate(itemData.createTime);
                result += '<div class="chat_message_time">' + createTime + '</div>';
                result += '<div class="chat_message">' + itemData.message + '</div>';
                result += '<div class="team_arrows chat_message_arrows"></div>';
                result += '</div>';
                return result;
            }
        });
        _message.listen(chatMessageList, 'SEND_MESSAGE', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                thisCom.addItemData(data);
                thisCom.scrollBottom();
            }
        });
        //
        var welcomeInfo = thisModule.findByKey('welcome-info');
        welcomeInfo.setLabel(customerName);
        //
        var waitInfo = thisModule.findByKey('wait-info');
        _message.listen(waitInfo, 'CUSTOMER_WAIT', function(thisCom, msg) {
            var info;
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                info = '前面有' + data.waitNum + '位玩家等候中...';
                thisModule.setContext({
                    waitNum: data.waitNum,
                    waitOrder: data.waitOrder
                });
            } else {
                //登录失败
                info = '系统异常，非常遗憾！';
                if (msg.flag === 'FAILURE_WAIT') {
                    info = '服务器爆满，请耐心等待……';
                }
            }
            thisCom.setLabel(info);
        });
        _message.listen(waitInfo, 'CUSTOMER_LOGOUT', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                var waitOrder = thisModule.getContext('waitOrder');
                if (waitOrder && data.waitOrder > -1 && waitOrder > data.waitOrder) {
                    var waitNum = thisModule.getContext('waitNum');
                    waitNum--;
                    thisModule.setContext({
                        waitNum: waitNum
                    });
                    var info = '前面有' + waitNum + '玩家等候中...';
                    thisCom.setLabel(info);
                }
            }
        });
        //
        _message.listen(waitInfo, 'ALLOT_WAIT_CUSTOMER', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                var customerId = _yy.getSession('customerId');
                if (customerId === data.customerId) {
                    waitPanel.hide();
                    chatPanel.show();
                    chatForm.setData('receptionId', data.receptionId);
                    //添加欢迎消息
                    var message = {
                        messageId: 1,
                        message: '工号:' + data.receptionId + ' ' + data.receptionName + '为您服务！有什么可以帮助您?',
                        receptionId: data.receptionId,
                        customerId: data.customerId,
                        type: 'text',
                        from: 's',
                        createTime: _utils.getDateTime()
                    };
                    chatMessageList.addItemData(message);
                    chatMessageList.scrollBottom();
                } else {
                    var waitOrder = thisModule.getContext('waitOrder');
                    if (waitOrder && data.waitOrder > -1 && waitOrder > data.waitOrder) {
                        var waitNum = thisModule.getContext('waitNum');
                        waitNum--;
                        thisModule.setContext({
                            waitNum: waitNum
                        });
                        var info = '前面有' + waitNum + '玩家等候中...';
                        thisCom.setLabel(info);
                    }
                }
            }
        });
        //
        _message.listen(chatMessageList, 'SEND_MESSAGE_FROM_CUSTOMER', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                thisCom.addItemData(data);
                thisCom.scrollBottom();
                chatForm.setData('message', '');
            }
        });
        //
        _message.listen(chatMessageList, 'SEND_MESSAGE_FROM_RECEPTION', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                thisCom.addItemData(data);
                thisCom.scrollBottom();
            }
        });
        //
        var sendButton = thisModule.findByKey('send-button');
        _event.bind(sendButton, 'click', function(thisCom) {
            var msg = chatForm.getData();
            msg.act = 'SEND_MESSAGE_FROM_CUSTOMER';
            _message.send(msg);
        });
        //结束对话
        var finishButton = thisModule.findByKey('finish-button');
        _event.bind(finishButton, 'click', function(thisCom) {
            var charForm = thisModule.findByKey('chat-form');
            var msg = charForm.getData();
            msg.act = 'CUSTOMER_FINISH_DIALOGUE';
            _message.send(msg);
        });
        _message.listen(thisModule, 'CUSTOMER_FINISH_DIALOGUE', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var msg = chatForm.getData();
                msg.act = 'CUSTOMER_LOGOUT';
                msg.waitOrder = -1;
                _message.send(msg);
            }
        });
        //客服强制关闭聊天消息
        _message.listen(thisModule, 'RECEPTION_FINISH_DIALOGUE', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var msg = chatForm.getData();
                msg.act = 'CUSTOMER_LOGOUT';
                msg.waitOrder = -1;
                _message.send(msg);
            }
        });
        //客户退出消息
        _message.listen(thisModule, 'CUSTOMER_LOGOUT', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                _yy.clearSession();
                thisModule.hide();
                thisModule.remove();
                document.title = 'im-玩家';
                _module.loadModule('customer-login');
            }
        });
        //关闭浏览器事件
        $(window).unload(function() {
            var msg = chatForm.getData();
            if (msg.receptionId != '-1') {
                //已分配客服，正常结束对话
                msg.act = 'CUSTOMER_FINISH_DIALOGUE';
                _message.send(msg);
            }
            //玩家正常退出
            var waitOrder = thisModule.getContext('waitOrder');
            if (waitOrder) {
                msg.waitOrder = waitOrder;
            }
            msg.act = 'CUSTOMER_LOGOUT';
            _message.send(msg);
        });
        //初始化客户公告
        var customerBulletin = thisModule.findByKey('customer-bulletin');
        _message.listen(customerBulletin, 'CUSTOMER_BULLETIN_DISPLAY', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                thisCom.setLabel(msg.data.content);
            }
        });
        _message.send({act: 'CUSTOMER_BULLETIN_DISPLAY'});
        //连接
        _message.send({act: 'CUSTOMER_WAIT', gameId: '10000'});
    };
    return self;
});