/**
 *
 */
package com.nd.momi.dialogue.localservice;

import com.nd.momi.dialogue.entity.DialogueEntity;
import com.nd.momi.dialogue.entity.DialogueHistoryEntity;
import com.wolf.framework.local.Local;

import java.util.List;
import java.util.Map;

/**
 * 对话服务
 * @author cy
 *
 */
public interface DialogueLocalService extends Local {

    public DialogueHistoryEntity inquireDialogueHistoryById(String dialogueId);

    public void updateDialogueHistory(Map<String, String> entityMap);

    public List<DialogueHistoryEntity> getDialogueHistories();

    public void insertDialogueHistory(Map<String,String> entityMap);

    public void insertDialogue(String customerId,String receptionId,String gameId);

    public void deleteDialogue(String customerId);

    public DialogueEntity inquireDialogueByCustomerId(String customerId);

    public List<DialogueEntity> inquireDialoguesByReceptionId(String receptionId);
}