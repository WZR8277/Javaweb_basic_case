package cn.ron.travel.web.servlet;

import cn.ron.travel.domain.ResultInfo;
import cn.ron.travel.domain.User;
import cn.ron.travel.service.UserService;
import cn.ron.travel.service.impl.UserSeviceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(value = "/user/*")
public class UserServlet extends BaseServlet {

    private UserService service = new UserSeviceImpl();

    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");

        session.removeAttribute("CHECKCODE_SERVER");

        if(checkcode_server == null ||!checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        Map<String, String[]> map = request.getParameterMap();

        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

       // UserSeviceImpl sevice = new UserSeviceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        if(flag){
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }


    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();

        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

       // UserService service = new UserSeviceImpl();
        User u = service.login(user);

        ResultInfo info = new ResultInfo();
        if (u==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码错误");
        }
        if (u!=null && !"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活！");
        }
        if (u!=null && "Y".equals(u.getStatus())){
            request.getSession().setAttribute("user",u);//登录成功标记
            info.setFlag(true);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
/*        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code!=null){
            //UserService sevice = new UserSeviceImpl();
            boolean flag = service.active(code);

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
