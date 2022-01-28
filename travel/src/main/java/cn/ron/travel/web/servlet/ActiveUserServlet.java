package cn.ron.travel.web.servlet;

import cn.ron.travel.service.UserService;
import cn.ron.travel.service.impl.UserSeviceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(value = "/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code!=null){
            UserService sevice = new UserSeviceImpl();
            boolean flag = sevice.active(code);

            String msg= null;
            if (flag){
                msg="激活成功，请<a href='login.html'>登录</a>";
            }else {
                msg="激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
