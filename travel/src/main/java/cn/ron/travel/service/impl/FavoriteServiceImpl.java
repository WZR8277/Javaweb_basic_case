package cn.ron.travel.service.impl;

import cn.ron.travel.dao.FavoriteDao;
import cn.ron.travel.dao.impl.FavoriteDaoImpl;
import cn.ron.travel.domain.Favorite;
import cn.ron.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {

        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite!=null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
