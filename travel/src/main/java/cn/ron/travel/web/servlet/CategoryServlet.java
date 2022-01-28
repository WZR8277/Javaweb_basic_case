package cn.ron.travel.web.servlet;

import cn.ron.travel.domain.Category;
import cn.ron.travel.service.CategoryService;
import cn.ron.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/category/*")
public class CategoryServlet extends BaseServlet{

    private CategoryService service = new CategoryServiceImpl();
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> cs = service.findAll();
/*        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),cs);*/
        writeValue(cs,response);
    }

}
