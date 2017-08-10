package per.posse.tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import per.posse.tool.service.impl.domain.ToolUser;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by posse on 17-8-9.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    public String userLogin(ToolUser user, HttpServletResponse response) {
        return "{\"result\":\"success\"}";
    }

//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public void register(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> statuts = new HashMap<String, Object>();
//        statuts.put("messageCode", 0);
//        statuts.put("register", 0);
//        statuts.put("register", 1);
//        JsonUtil.toWriter(statuts, response);
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(HttpServletRequest request, HttpServletResponse response) {
//        String path = request.getContextPath();
//        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//        System.out.println(path);
//        System.out.println(basePath);
//        ToolUser toolUser = null;
//        if( toolUser != null){
//            return "index";
//        }else{
//            return "redirect:"+basePath+"login.jsp";
//        }
//    }
}
