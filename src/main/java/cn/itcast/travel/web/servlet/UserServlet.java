package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();
    //用户注册
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String CHECKCODE_SERVER = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        ResultInfo info = new ResultInfo();
        response.setContentType("application/json;charset=utf-8");
        if (CHECKCODE_SERVER == null || !CHECKCODE_SERVER.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.getWriter().write(json);
            return;
        }

        Map<String,String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean flag = service.regist(user);

        if (flag){
            //用户已存在
            info.setFlag(false);
            info.setErrorMsg("用户已存在");
        }else {
            info.setFlag(true);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.getWriter().write(json);
    }

    //登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String CHECKCODE_SERVER = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        ResultInfo info = new ResultInfo();
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();

        if (CHECKCODE_SERVER == null || !CHECKCODE_SERVER.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            String json = mapper.writeValueAsString(info);
            response.getWriter().write(json);
            return;
        }

        Map<String,String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        User u = service.login(user);

        if (u == null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        if (u != null && u.getStatus().equals("N")){
            info.setFlag(false);
            info.setErrorMsg("用户未激活");
        }
        if(u != null && "Y".equals(u.getStatus())){
            String auto = request.getParameter("auto");
            if ("auto".equals(auto)){
                String username = u.getUsername();
                String password = u.getPassword();
                Cookie cook_user = new Cookie("username",username);
                Cookie cook_pass = new Cookie("password",password);
                cook_user.setMaxAge(24*60*60);
                cook_pass.setMaxAge(24*60*60);
                response.addCookie(cook_user);
                response.addCookie(cook_pass);
            }
            request.getSession().setAttribute("user",u);//登录成功标记
            info.setFlag(true);
        }
        mapper.writeValue(response.getOutputStream(),info);
    }

    //用于在header.html中显示用户名
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Object user = request.getSession().getAttribute("user");

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }

    //用户退出
    public void exist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getSession().removeAttribute("user");

        Cookie cook_user = new Cookie("username",null);
        Cookie cook_pass = new Cookie("password",null);
        cook_user.setMaxAge(0);
        cook_pass.setMaxAge(0);
        response.addCookie(cook_user);
        response.addCookie(cook_pass);

        response.sendRedirect(request.getContextPath()+"/index.html");
    }

    //激活
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String code = request.getParameter("code");
        if(code!=null){
            boolean flag = service.active(code);

            String msg;
            if (flag){
                //用户存在,成功激活
                msg = "激活成功，请<a href='http://localhost/graduate/index.html'>登录</a>";
            }else {
                msg="哈哈，你惨啦";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

}

