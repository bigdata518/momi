define(function(require) {
    var _yy = require('yy');
    var _index = _yy.getIndex();
    require('yy/panel');
    require('yy/list');
    require('yy/label');
    require('yy/button');
    var _module = require('yy/module');
    var self = {};
    var _event = _yy.getEvent();
    var _message = _yy.getMessage();
    var _utils = _yy.getUtils();
    self.init = function(thisModule) {
        var receptionName = _yy.getSession('receptionName');
        document.title = receptionName;
        //获取提交表单
        var chatForm = thisModule.findByKey('chat-form');
        //初始化聊天列表
        var messageList = thisModule.findByKey('message-list');
        messageList.init({
            key: 'customerId',
            itemClazz: '',
            itemDataToHtml: function(itemData) {
                var result = '<div class="chat_title">' + itemData.customerName + '</div>'
                        + '<div id="' + itemData.customerId + '-chat-message-list" class="list chat_message_list" scroll="true"></div>';
                return result;
            },
            itemCompleted: function(itemCom) {
                var chatListId = itemCom.key + '-chat-message-list';
                var chatList = itemCom.findChildByKey(chatListId);
                chatList.init({
                    key: 'messageId',
                    itemClazz: '',
                    itemDataToHtml: function(itemData) {
                        var result;
                        if (itemData.from === 'c') {
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
            }
        });
        //初始化玩家列表
        var customerList = thisModule.findByKey('customer-list');
        customerList.init({
            key: 'customerId',
            itemClazz: 'online',
            itemDataToHtml: function(itemData) {
                var result = '<div>' + itemData.customerName + '</div>'
                        + '<div class="customer_state"></div>'
                        + '<div id="' + itemData.customerId + '-close-button"class="button customer_close"></div>';
                return result;
            },
            itemCompleted: function(itemCom) {
                //玩家列表item点击事件
                _event.bind(itemCom, 'click', function(thisCom) {
                    var customerId = thisCom.key;
                    thisCom.selected();
                    //切换聊天窗口
                    var messageItem = messageList.getItemByKey(customerId);
                    var zIndex = _index.nextZIndex();
                    messageItem.$this.css({zIndex: zIndex});
                    //
                    var chatListId = customerId + '-chat-message-list';
                    var chatList = messageItem.findChildByKey(chatListId);
                    chatList.initScroll();
                    //
                    chatForm.setData('customerId', customerId);
                });
                //item的close按钮点击事件
                var closeButtonId = itemCom.key + '-close-button';
                var closeButton = itemCom.findChildByKey(closeButtonId);
                _event.bind(closeButton, 'click', function(thisCom) {
                    var customerItem = thisCom.parent;
                    customerList.removeItem(customerItem.key);
                    messageList.removeItem(customerItem.key);
                    if (customerList.size() > 0) {
                        customerList.firstChild.$this.click();
                    }
                });
            }
        });
        //
        _message.listen(customerList, 'ALLOT_WAIT_CUSTOMER', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                thisCom.addItemData(data);
                messageList.addItemData(data);
                var message = {
                    messageId: 1,
                    message: '工号:' + data.receptionId + ' ' + data.receptionName + '为您服务！有什么可以帮助您?',
                    customerId: data.customerId,
                    receptionId: data.receptionId,
                    from: 's',
                    type: 'text',
                    createTime: _utils.getDateTime()
                };
                var messageItem = messageList.getItemByKey(data.customerId);
                if (messageItem) {
                    var chatListId = data.customerId + '-chat-message-list';
                    var chatList = messageItem.findChildByKey(chatListId);
                    if (chatList) {
                        chatList.addItemData(message);
                        chatList.scrollBottom();
                    }
                }
                if (thisCom.size() === 1) {
                    thisCom.firstChild.$this.click();
                }
            }
        });
        _message.listen(customerList, 'CUSTOMER_LOGOUT', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                var customerItem = thisCom.getItemByKey(data.customerId);
                customerItem.$this.removeClass('online');
                var message = {
                    messageId: -1,
                    message: '玩家已离开',
                    customerId: data.customerId,
                    receptionId: data.receptionId,
                    from: 'c',
                    type: 'text',
                    createTime: _utils.getDateTime()
                };
                var messageItem = messageList.getItemByKey(data.customerId);
                if (messageItem) {
                    var chatListId = data.customerId + '-chat-message-list';
                    var chatList = messageItem.findChildByKey(chatListId);
                    if (chatList) {
                        chatList.addItemData(message);
                        chatList.scrollBottom();
                    }
                }
            }
        });
        //
        _message.listen(messageList, 'SEND_MESSAGE_FROM_RECEPTION', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                var customerId = data.customerId;
                var messageItem = thisCom.getItemByKey(customerId);
                if (messageItem) {
                    var chatListId = customerId + '-chat-message-list';
                    var chatList = messageItem.findChildByKey(chatListId);
                    if (chatList) {
                        chatList.addItemData(data);
                        chatList.scrollBottom();
                    }
                }
            }
        });
        //
        _message.listen(messageList, 'SEND_MESSAGE_FROM_CUSTOMER', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                var customerId = data.customerId;
                var messageItem = thisCom.getItemByKey(customerId);
                if (messageItem) {
                    var chatListId = customerId + '-chat-message-list';
                    var chatList = messageItem.findChildByKey(chatListId);
                    if (chatList) {
                        chatList.addItemData(data);
                        chatList.scrollBottom();
                    }
                }
            }
        });
        //状态按钮
        //登录后默认状态为离开状态
        thisModule.setContext({state: 'off'});
        var stateButton = thisModule.findByKey('state-button');
        _event.bind(stateButton, 'click', function(thisCom) {
            var state = thisModule.getContext('state');
            if (state === 'off') {
                _message.send({act: 'RECEPTION_COMEBACK'});
            } else {
                _message.send({act: 'RECEPTION_LEAVE', offMessage: '临时有事'});
            }
        });
        _message.listen(stateButton, 'RECEPTION_LEAVE', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                thisModule.setContext({state: 'off'});
                thisCom.setLabel('回  岗');
            }
        });
        _message.listen(stateButton, 'RECEPTION_COMEBACK', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                thisModule.setContext({state: 'on'});
                thisCom.setLabel('离  开');
            }
        });
        //退出按钮
        var logoutButton = thisModule.findByKey('logout-button');
        _event.bind(logoutButton, 'click', function(thisCom) {
            var isOnline = false;
            for (var id in customerList.children) {
                if (customerList.children[id].$this.hasClass('online')) {
                    isOnline = true;
                    break;
                }
            }
            if (isOnline) {
                var operateInfo = thisModule.findByKey('operate-info');
                operateInfo.setLabel('正在与玩家通话中,不能退出.');
            } else {
                var msg = {
                    act: 'RECEPTION_LOGOUT'
                };
                _message.send(msg);
                //
                _yy.clearSession();
                thisModule.hide();
                thisModule.remove();
                document.title = 'im-客服';
                _module.loadModule('reception-login');
            }
        });
        //
        var sendButton = thisModule.findByKey('send-button');
        _event.bind(sendButton, 'click', function(thisCom) {
            var msg = chatForm.getData();
            msg.act = 'SEND_MESSAGE_FROM_RECEPTION';
            _message.send(msg);
            chatForm.setData('message', '');
        });
        //强制关闭与客户的对话
        var forceFinishButton = thisModule.findByKey('force-finish-button');
        _event.bind(forceFinishButton, 'click', function(thisCom) {
            var data = chatForm.getData();
            _message.send({
                act: 'RECEPTION_FINISH_DIALOGUE',
                customerId: data.customerId
            });
        });
        _message.listen(forceFinishButton, 'RECEPTION_FINISH_DIALOGUE', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                var data = msg.data;
                customerList.removeItem(data.customerId);
                messageList.removeItem(data.customerId);
                if (customerList.size() > 0) {
                    customerList.firstChild.$this.click();
                }
            }
        });
        //
        window.onbeforeunload = function(e) {
            var isOnline = false;
            for (var id in customerList.children) {
                if (customerList.children[id].$this.hasClass('online')) {
                    isOnline = true;
                    break;
                }
            }
            if (isOnline) {
                var operateInfo = thisModule.findByKey('operate-info');
                operateInfo.setLabel('正在与玩家通话中,不能退出.');
                //阻止默认浏览器动作(W3C)
                if (e && e.preventDefault)
                    e.preventDefault();
//IE中阻止函数器默认动作的方式
                else
                    window.event.returnValue = '正在与玩家通话中,不能退出.';
                return '正在与玩家通话中,不能退出.';
            }
        };
        $(window).unload(function() {
            var msg = {
                act: 'RECEPTION_LOGOUT'
            };
            _message.send(msg);
            //
            _yy.clearSession();
            thisModule.hide();
            thisModule.remove();
            document.title = 'im-客服';
            _module.loadModule('', 'reception-login');
        });
        //初始化客服公告
        var receptionBulletin = thisModule.findByKey('reception-bulletin');
        _message.listen(receptionBulletin, 'RECEPTION_BULLETIN_DISPLAY', function(thisCom, msg) {
            if (msg.flag === 'SUCCESS') {
                thisCom.setLabel(msg.data.content);
            }
        });
        _message.send({act: 'RECEPTION_BULLETIN_DISPLAY'});
    };
    return self;
});