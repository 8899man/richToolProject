package per.posse.tool.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import per.posse.tool.dto.ToolUserDto;
import per.posse.tool.service.IToolUserService;
import per.posse.tool.service.auth.IAuthorityService;
import per.posse.tool.service.impl.domain.ToolUser;
import per.posse.tool.session.SessionInfo;
import per.posse.tool.session.ToolUserSessionInfo;
import per.posse.tool.ws.ExternalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by posse on 17-8-9.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private IToolUserService toolUserService;

    @Autowired
    private IAuthorityService authorityService;

    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    public void userLogin(ToolUser user, HttpServletResponse response, HttpSession session) {
        String result = StringUtils.EMPTY;
        if (user == null) {
            result = "{\"result\":\"error\", \"msg\":\"request is null\"}";
            responseWrite(response, result);
            return;
        }

        try {
            ToolUserDto userDto = toolUserService.authLoginUser(user.getLoginEmail(), user.getLoginPassword());
            result = "{\"result\":\"success\", \"msg\":\"login in success\"}";
            // put into session.
            if (session != null) {
                SessionInfo sessionInfo = buildSessionInfoInstance(session, userDto);

                // Don't allow concurrent login, set logout true.
                if (!userDto.getEnableConcurrentLogin()) {
                    authorityService.removeUserSession(userDto.getId(), sessionInfo);
                }
                authorityService.updateSession(sessionInfo);
                session.setAttribute("userName", userDto.getName());
            }
        } catch (ExternalException e) {
            e.printStackTrace();
            result = "{\"result\":\"error\", \"msg\":\"" + e.getRawMessage() + "\"}";
        } finally {
            responseWrite(response, result);
        }
    }

    private SessionInfo buildSessionInfoInstance(HttpSession httpSession, ToolUserDto user) {
        ToolUserSessionInfo sessionInfo = new ToolUserSessionInfo();
        sessionInfo.setKey(httpSession.getId());
        sessionInfo.setCreatedAt(System.currentTimeMillis());
        sessionInfo.setExpiryTime(Long.valueOf(httpSession.getMaxInactiveInterval()));
        sessionInfo.setUserInfo(user);
        return sessionInfo;
    }

    private void responseWrite(HttpServletResponse response, String json) {
        response.setContentType("application/json");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 干货
    private static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return attrs.getRequest();
    }
}
