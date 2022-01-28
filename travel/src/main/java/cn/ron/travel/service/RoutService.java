package cn.ron.travel.service;

import cn.ron.travel.domain.PageBean;
import cn.ron.travel.domain.Route;

public interface RoutService {

    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

    Route findOne(String rid);
}
