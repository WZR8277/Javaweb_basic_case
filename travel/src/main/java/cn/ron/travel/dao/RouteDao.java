package cn.ron.travel.dao;

import cn.ron.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    public int findTotalCount(int cid, String rname);

    public List<Route> findByPage(int cid, int start, int pageSize, String rname);

    public Route findOne(int rid);
}
