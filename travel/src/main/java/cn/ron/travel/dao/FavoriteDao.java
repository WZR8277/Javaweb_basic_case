package cn.ron.travel.dao;

import cn.ron.travel.domain.Favorite;

public interface FavoriteDao {

    public Favorite findByRidAndUid(int rid, int uid);

    public int findCountByRid(int rid);

    void add(int rid, int uid);
}
