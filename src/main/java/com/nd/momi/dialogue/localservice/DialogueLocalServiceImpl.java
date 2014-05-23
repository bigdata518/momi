/**
 *
 */
package com.nd.momi.dialogue.localservice;
import com.nd.momi.dialogue.entity.DialogueEntity;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.dao.condition.InquirePageContext;
import com.wolf.framework.local.LocalServiceConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Alex
 *
 */
@LocalServiceConfig(
        interfaceInfo =DialogueLocalService.class,
        description = "对话管理")

public class DialogueLocalServiceImpl implements DialogueLocalService {

    @InjectRDao(clazz = DialogueEntity.class)
    private REntityDao<DialogueEntity> dialogueEntityDao;

    /* 对话表初始化
     * @see com.wolf.framework.local.Local#init()
     */
    @Override
    public void init() {
        List<DialogueEntity> dialogueEntityList = this.getDialogues();
        if (dialogueEntityList == null) {
            Map<String, String> entityMap = new HashMap<String, String>(7, 1);
            entityMap.put("dialogueId", "");
            entityMap.put("customerId", "");
            entityMap.put("receptionId", "");
            entityMap.put("gameId", "");
            entityMap.put("createTime", "");
            entityMap.put("endTime", "");
            entityMap.put("state", "");
            this.dialogueEntityDao.insert(entityMap);
        }
    }

    /* 更新对话表某一指定字段
     * @see com.nd.momi.dialogue.localservice.DialogueLocalService#updateDailogue(java.util.Map)
     */
    @Override
    public void updateDailogue(Map<String, String> entityMap) {
        this.dialogueEntityDao.update(entityMap);
    }

    /* 根据对话 Id 查询对话
     * @see com.nd.momi.dialogue.localservice.DialogueLocalService#inquireDialogueById(java.lang.String)
     */
    @Override
    public DialogueEntity inquireDialogueById(String dialogueId) {
        return this.dialogueEntityDao.inquireByKey(dialogueId);
    }

    /* 获取所有的 DialogueEntity
     * @see com.nd.momi.dialogue.localservice.DialogueLocalService#getDialogues()
     */
    @Override
    public List<DialogueEntity> getDialogues() {
        InquirePageContext inquirePageContext = new InquirePageContext();
        return this.dialogueEntityDao.inquire(inquirePageContext);
    }
}
