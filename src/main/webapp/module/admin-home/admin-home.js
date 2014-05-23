define(function(require) {
    var _yy = require('yy');
    var _index = _yy.getIndex();
    require('yy/panel');
    require('yy/button');
    require('yy/list');
    var _module = require('yy/module');
    var self = {};
    var _event = _yy.getEvent();
    var _message = _yy.getMessage();
    self.init = function(thisModule) {
        var panelList = thisModule.findByKey('panel-list');
        panelList.init({
            key: 'moduleId',
            itemClazz: '',
            itemDataToHtml: function(itemData) {
                return '';
            }
        });
        //
        var navigationList = thisModule.findByKey('navigation-list');
        navigationList.init({
            key: 'moduleId',
            itemClazz: '',
            itemDataToHtml: function(itemData) {
                return itemData.navigation;
            },
            itemCompleted: function(itemCom) {
                _event.bind(itemCom, 'click', function(thisCom) {
                    thisCom.selected();
                    //
                    var panelItem = panelList.getItemByKey(thisCom.key);
                    if(!panelItem) {
                        panelItem = panelList.addItemData({moduleId: thisCom.key});
                    }
                    var zIndex = _index.nextZIndex();
                    panelItem.$this.css({zIndex: zIndex});
                    _module.loadModule(itemCom.key, function() {}, panelItem);
                });
            }
        });
        navigationList.loadData([
            {navigation: '客服帐号', moduleId: 'reception-manage'}
        ]);
        navigationList.firstChild.$this.click();
        //
        //默认加载userPanel
//        var receptionManagePanel = thisModule.findByKey('reception-manage-panel');
//        var toReceptionManageButton = thisModule.findByKey('to-reception-manage-button');
        //导航集合
//        var navs = [
//            {
//                panel: receptionManagePanel,
//                btn: toReceptionManageButton,
//                module: 'reception-manage'
//            }
//        ];
        //默认页面
//        module.loadModule(receptionManagePanel.id, 'reception-manage', function() {
//        });
//        thisModule.setContext({
//            visiblePanel: receptionManagePanel,
//            visibleButton: toReceptionManageButton
//        });
        //绑定导航按钮事件
//        var panel;
//        var btn;
//        for (var index = 0; index < navs.length; index++) {
//            btn = navs[index].btn;
//            _event.bind(btn, 'click', function(thisCom) {
//                var visiblePanel = thisModule.getContext('visiblePanel');
//                var visibleButton = thisModule.getContext('visibleButton');
//                var thisPanel;
//                var thisBtn;
//                var moduleName;
//                if (thisCom !== visibleButton) {
//                    visiblePanel.hide();
//                    visibleButton.$this.removeClass('selected');
//                    for (var index = 0; index < navs.length; index++) {
//                        thisPanel = navs[index].panel;
//                        thisBtn = navs[index].btn;
//                        moduleName = navs[index].module;
//                        if (thisBtn === thisCom) {
//                            thisPanel.show();
//                            thisBtn.$this.addClass('selected');
//                            thisModule.setContext({
//                                visiblePanel: thisPanel,
//                                visibleButton: thisBtn
//                            });
//                            module.loadModule(thisPanel.id, moduleName, function() {
//                            });
//                            break;
//                        }
//                    }
//                }
//            });
//        }
    };
    return self;
});