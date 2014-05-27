package com.nd.momi.customer.localservice;


import com.nd.momi.config.TableNames;
import com.nd.momi.customer.entity.GameEntity;
import com.nd.momi.key.localservice.KeyLocalService;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.dao.condition.InquirePageContext;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.local.LocalServiceConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cy on 2014/5/20.
 */
@LocalServiceConfig(
        interfaceInfo = GameLocalService.class,
        description = "游戏管理")

public class GameLocalServiceImpl implements GameLocalService {
    @InjectLocalService()
    private KeyLocalService keyLocalService;

    @InjectRDao(clazz = GameEntity.class)
    private REntityDao<GameEntity> gameEntityDao;


    private long nextGameId() {
        return this.keyLocalService.nextKeyValue(TableNames.GAME);
    }

    @Override
    public void insertGame(String gameName) {
        final long gameId = this.nextGameId();
        Map<String,String> entityMap = new HashMap<String, String>(2,1);
        entityMap.put("gameId",Long.toString(gameId));
        entityMap.put("gameName",gameName);
        this.gameEntityDao.insert(entityMap);
    }

    @Override
    public void deleteGame(String gameId) {
        this.gameEntityDao.delete(gameId);
    }


    @Override
    public List<GameEntity> getGames() {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageIndex(1);
        inquirePageContext.setPageSize(10);
        return this.gameEntityDao.inquire(inquirePageContext);
    }

    @Override
    public void init() {

        }
    }