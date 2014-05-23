package com.nd.momi.customer.localservice;

import com.nd.momi.customer.entity.GameEntity;
import com.wolf.framework.local.Local;

import java.util.List;

/**
 * Created by cy on 2014/5/20.
 */
public interface GameLocalService extends Local{

    public List<GameEntity> getGames();

    public void insertGame(String gameName);

    public void deleteGame(String gameId);
}
