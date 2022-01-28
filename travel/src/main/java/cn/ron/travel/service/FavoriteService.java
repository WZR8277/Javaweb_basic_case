package cn.ron.travel.service;

public interface FavoriteService {

    public boolean isFavorite(String rid ,int uid);

    void add(String rid, int uid);
}
