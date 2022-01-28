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

@WebServlet(value = "/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();

        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UserService service = new UserSeviceImpl();
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
}
