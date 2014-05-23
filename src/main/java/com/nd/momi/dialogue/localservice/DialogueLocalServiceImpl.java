/**
 *
 */
package com.nd.momi.dialogue.localservice;
import com.nd.momi.config.TableNames;
import com.nd.momi.dialogue.entity.DialogueEntity;
import com.nd.momi.dialogue.entity.DialogueHistoryEntity;
import com.nd.momi.key.localservice.KeyLocalService;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.dao.condition.InquireIndexPageContext;
import com.wolf.framework.dao.condition.InquirePageContext;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.local.LocalServiceConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author cy
 *
 */
@LocalServiceConfig(
        interfaceInfo =DialogueLocalService.class,
        description = "对话管理")

public class DialogueLocalServiceImpl implements DialogueLocalService {

    @InjectLocalService()
    private KeyLocalService keyLocalService;

    @InjectRDao(clazz = DialogueEntity.class)
    private REntityDao<DialogueEntity> dialogueEntityDao;

    @InjectRDao(clazz = DialogueHistoryEntity.class)
    private REntityDao<DialogueHistoryEntity> dialogueHistoryEntityDao;
    /* 对话表初始化
     * @see com.wolf.framework.local.Local#init()
     */
    @Override
    public void init() {
        //初始化历史对话表的主键值
        long maxKeyValue = this.keyLocalService.getMaxKeyValue(TableNames.DIALOGUE_HISTORY);
        if (maxKeyValue < 100000) {
            this.keyLocalService.updateMaxKeyValue(TableNames.DIALOGUE_HISTORY, 100000);
        }
    }

    private long nextDialogueHistoryId() {
        return this.keyLocalService.nextKeyValue(TableNames.DIALOGUE_HISTORY);
    }
    /* 更新对话表某一指定字段
     * @see com.nd.momi.dialogue.localservice.DialogueLocalService#updateDailogue(java.util.Map)
     */
    @Override
    public void updateDialogueHistory(Map<String, String> entityMap) {
        this.dialogueHistoryEntityDao.update(entityMap);
    }

    /* 根据对话 Id 查询对话
     * @see com.nd.momi.dialogue.localservice.DialogueLocalService#inquireDialogueById(java.lang.String)
     */
    @Override
    public DialogueHistoryEntity inquireDialogueHistoryById(String dialogueId) {
        return this.dialogueHistoryEntityDao.inquireByKey(dialogueId);
    }

    /* 获取所有的 DialogueEntity
     * @see com.nd.momi.dialogue.localservice.DialogueLocalService#getDialogues()
     */
    @Override
    public List<DialogueHistoryEntity> getDialogueHistories() {
        InquirePageContext inquirePageContext = new InquirePageContext();
        return this.dialogueHistoryEntityDao.inquire(inquirePageContext);
    }

    @Override
    public void insertDialogueHistory(Map<String, String> entityMap) {
        final long dialogueId = this.nextDialogueHistoryId();
        final long endTime = System.currentTimeMillis();
        entityMap.put("dialogueId", Long.toString(dialogueId));
        entityMap.put("endTime", Long.toString(endTime));
        this.dialogueHistoryEntityDao.insert(entityMap);
    }

    @Override
    public void insertDialogue(String customerId, String receptionId, String gameId) {
        Map<String, String> entityMap = new HashMap<String, String>(4, 1);
        final long createTime = System.currentTimeMillis();
        entityMap.put("customerId", customerId);
        entityMap.put("receptionId", receptionId);
        entityMap.put("gameId", gameId);
        entityMap.put("createTime", Long.toString(createTime));
        this.dialogueEntityDao.insert(entityMap);
    }

    @Override
    public void deleteDialogue(String customerId) {
        this.dialogueEntityDao.delete(customerId);
    }

    @Override
    public DialogueEntity inquireDialogueByCustomerId(String customerId) {
        return this.dialogueEntityDao.inquireByKey(customerId);
    }

    @Override
    public List<DialogueEntity> inquireDialoguesByReceptionId(String receptionId) {
        InquireIndexPageContext inquirePageContext = new InquireIndexPageContext("receptionId", receptionId);
        return this.dialogueEntityDao.inquireByIndexDESC(inquirePageContext);
    }
}
