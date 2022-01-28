package cn.ron.travel.dao;

import cn.ron.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    public List<RouteImg> findByRid(int rid);
}
