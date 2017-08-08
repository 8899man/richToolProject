package per.posse.tool.ws.response;

import pers.posse.tool.service.dto.ToolUserDto;
import per.posse.tool.ws.xml.DomainAttribute;
import per.posse.tool.ws.xml.ExternalOperateEntityResponse;

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
