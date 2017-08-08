package per.posse.tool.ws.xml;

import per.posse.tool.ws.enums.OperationDomain;
import per.posse.tool.ws.enums.OperationType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by posse on 17-7-25.
 */
@XmlRootElement(name = "operation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Operation {

    private OperationDomain domain;
    private OperationType type;

    public OperationDomain getDomain() {
        return domain;
    }

    public void setDomain(OperationDomain domain) {
        this.domain = domain;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }
}
