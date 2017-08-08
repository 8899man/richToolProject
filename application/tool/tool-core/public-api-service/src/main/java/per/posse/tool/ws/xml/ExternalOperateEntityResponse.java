package per.posse.tool.ws.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by posse on 17-7-19.
 */
@XmlRootElement(name = "externalOperateEntityResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExternalOperateEntityResponse {
    private String msg = "Operate Success.";

    private DomainAttribute attribute;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DomainAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(DomainAttribute attribute) {
        this.attribute = attribute;
    }
}
