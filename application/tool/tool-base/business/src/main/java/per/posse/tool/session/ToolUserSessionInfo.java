package per.posse.tool.session;

import per.posse.tool.dto.ToolUserDto;

import java.io.Serializable;

public class ToolUserSessionInfo extends SessionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private ToolUserDto userInfo;

    public ToolUserDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ToolUserDto userInfo) {
        userInfo = userInfo;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
