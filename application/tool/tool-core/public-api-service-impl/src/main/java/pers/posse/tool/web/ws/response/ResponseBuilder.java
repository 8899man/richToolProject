package pers.posse.tool.web.ws.response;

import pers.posse.tool.service.dto.ToolUserDto;
import pers.posse.tool.web.ws.xml.DomainAttribute;
import pers.posse.tool.web.ws.xml.ExternalOperateEntityResponse;

/**
 * Created by posse on 17-7-31.
 */
public class ResponseBuilder {
    public static void addResult(ExternalOperateEntityResponse response, String msg) {
        response.setMsg(msg);
    }

    public static void builderRetrieveEntityResult(ExternalOperateEntityResponse response, ToolUserDto user) {
        if (user == null) {
            return;
        }
        DomainAttribute attribute = new DomainAttribute();
        attribute.setId(user.getId());
        attribute.setName(user.getName());
        attribute.setAge(user.getAge());
        attribute.setGender(user.getGender());
        attribute.setIdNum(user.getIdNum());
        attribute.setAddress(user.getAddress());
        attribute.setMobile(user.getMobile());
        attribute.setApiName(user.getApiName());
        attribute.setApiPassword(user.getApiPassword());
        response.setAttribute(attribute);
    }
}
