package cn.ron.travel.web.servlet;

import cn.ron.travel.domain.PageBean;
import cn.ron.travel.domain.Route;
import cn.ron.travel.domain.User;
import cn.ron.travel.service.FavoriteService;
import cn.ron.travel.service.RoutService;
import cn.ron.travel.service.impl.FavoriteServiceImpl;
import cn.ron.travel.service.impl.RoutServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;


@WebServlet(value = "/route/*")
public class RouteServlet extends BaseServlet{

    private RoutService routService = new RoutServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl() ;
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //request.setCharacterEncoding("utf-8");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        String rname = request.getParameter("rname");
        rname = URLDecoder.decode(rname,"UTF-8");
        //rname = new String(rname.getBytes(),"utf-8");
        int cid = 0;
        if(cidStr !=null && cidStr.length()>0 !="null".equals(cidStr)){
            cid  = Integer.parseInt(cidStr);
        }
        int currentPage = 0;
        if(currentPageStr !=null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;//默认值
        }
        int pageSize = 0;
        if(pageSizeStr !=null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize=5;//默认值
        }

        PageBean<Route> pb = routService.pageQuery(cid, currentPage, pageSize,rname);
        writeValue(pb,response);
    }
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = routService.findOne(rid);
        writeValue(route ,response);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if(user==null){
            uid=0;
        }else {
            uid=user.getUid();
        }

        boolean flag =favoriteService.isFavorite(rid,uid);
        writeValue(flag,response);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if(user==null){
            return;
        }else {
            uid=user.getUid();
        }

        favoriteService.add(rid,uid);

    }
}
