package cn.ron.travel.service.impl;

import cn.ron.travel.dao.FavoriteDao;
import cn.ron.travel.dao.RouteDao;
import cn.ron.travel.dao.RouteImgDao;
import cn.ron.travel.dao.SellerDao;
import cn.ron.travel.dao.impl.FavoriteDaoImpl;
import cn.ron.travel.dao.impl.RouteDaoImpl;
import cn.ron.travel.dao.impl.RouteImgDaoImpl;
import cn.ron.travel.dao.impl.SellerDaoImpl;
import cn.ron.travel.domain.PageBean;
import cn.ron.travel.domain.Route;
import cn.ron.travel.domain.RouteImg;
import cn.ron.travel.domain.Seller;
import cn.ron.travel.service.RoutService;

import java.util.List;

public class RoutServiceImpl implements RoutService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {

        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        int start = (currentPage-1)*pageSize;
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);

        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public Route findOne(String rid) {

        Route route = routeDao.findOne(Integer.parseInt(rid));
        List<RouteImg> routeImgList = routeImgDao.findByRid(Integer.parseInt(rid));
        route.setRouteImgList(routeImgList);
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);

        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);

        return route;
    }
}
