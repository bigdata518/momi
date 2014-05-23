/**
 *
 */
package com.nd.momi.dialogue.localservice;

import com.nd.momi.dialogue.entity.DialogueEntity;
import com.wolf.framework.local.Local;

import java.util.List;
import java.util.Map;

/**
 * @author cy
 *
 */
public interface DialogueLocalService extends Local {

    public DialogueEntity inquireDialogueById(String dialogueId);

    public void updateDailogue(Map<String, String> entityMap);

    public List<DialogueEntity> getDialogues();
}