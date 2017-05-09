package com.jiangshuaa.controller;

import com.jiangshuaa.aspect.LogAspect;
import com.jiangshuaa.model.User;
import com.jiangshuaa.service.ToutiaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.RedirectViewControllerRegistration;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by jiangshuhua on 2017/5/9.
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping(path = {"/", "/index"}) // path = value 且可省略，127.0.0.1:8080/
    @ResponseBody                           //                        127.0.0.1:8080/index 都是主页
    public String index(HttpSession session){
        logger.info("Visit Index");
        return "hello jiangshuaa," + session.getAttribute("msg")
                + "<br> Say:" + toutiaoService.say();
    }

    @RequestMapping("/profile/{groupId}/{userId}")
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type",defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "jiangshuaa") String key){
        return String.format("GID:%s, UID:%d, TYPE:%d, KEY:%s", groupId, userId, type, key);
    }

    @RequestMapping(value = {"/vm"})
    public String news(Model model){
        model.addAttribute("value1", "vv1");
        List<String> colors = Arrays.asList("RED", "GREEN","BLUE");

        Map<String, String> map = new HashMap<String, String>();
        for(int i = 0; i < 4; ++i){
            map.put(String.valueOf(i), String.valueOf(i*i));
        }

        model.addAttribute("colors", colors);
        model.addAttribute("map", map);
        model.addAttribute("user", new User("Jim"));


        return "news";
    }

    @RequestMapping(value = {"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }

        for(Cookie cookie : request.getCookies()){
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(":");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }

        sb.append("getMethod:" + request.getMethod() + "<br>");          //得到客户机请求方式
        sb.append("getPathInfo:" + request.getPathInfo() + "<br>");
        sb.append("getQueryString:" + request.getQueryString() + "<br>");//返回请求行中的参数部分
        sb.append("getRequestURI:" + request.getRequestURI() + "<br>");  //返回请求行中的资源名部分
        sb.append("getRequestURL:" + request.getRequestURL() + "<br>");  //客户端发出请求时的完整URL
        sb.append("getRemoteAddr:" + request.getRemoteAddr() + "<br>");  //返回发出请求的客户机的IP地址
        sb.append("getRemoteHost:" + request.getRemoteHost() + "<br>");  //返回发出请求的客户机的完整主机名
        sb.append("getRemotePort:" + request.getRemotePort() + "<br>");  //返回客户机所使用的网络端口号
        sb.append("getLocalAddr:" + request.getLocalAddr() + "<br>");    //返回WEB服务器的IP地址
        sb.append("getLocalName:" + request.getLocalName() + "<br>");    //返回WEB服务器的主机名
        sb.append("getLocalPort:" + request.getLocalPort() + "<br>");    //返回WEB服务器的网络端口号
        sb.append("getRemoteUser:" + request.getRemoteUser() + "<br>");

        return sb.toString();
    }

    @RequestMapping(value = {"/response"})
    @ResponseBody
    public String response(@CookieValue(value = "nowcoderid", defaultValue = "a") String nowcoderid,
                           @RequestParam(value = "key", defaultValue = "key") String key,
                           @RequestParam(value = "value", defaultValue = "value") String value,
                           HttpServletResponse response){
        response.addCookie(new Cookie(key, value));
        response.addHeader(key, value);
        return "NowCoderId From Cookie:" + nowcoderid;
    }

    @RequestMapping("/redirect/{code}")
    /*public RedirectView redirect(@PathVariable("code") int code){
        RedirectView red = new RedirectView("/", true);
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }*/
    public String redirect(@PathVariable("code") int code,
                           HttpSession session){
        session.setAttribute("msg", "Jump from redirect.");
        return "redirect:/";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key", required = false) String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("Key 错误");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error:" + e.getMessage();
    }

}
