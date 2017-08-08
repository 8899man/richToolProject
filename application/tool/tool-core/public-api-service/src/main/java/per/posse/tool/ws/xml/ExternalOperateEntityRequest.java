package per.posse.tool.ws.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by posse on 17-7-19.
 */
// @XmlRootElement 告诉JAXB如何编组给定的类型 或者 直接从/给定方法返回/接受JAXBElement.
@XmlRootElement(name = "externalOperateEntityRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExternalOperateEntityRequest {

    @XmlAttribute(name = "apiName", required = true)
    private String apiName;
    @XmlAttribute(name = "apiPassword", required = true)
    private String apiPassword;
    @XmlAttribute(name = "version", required = true)
    private String version;

    private Operation operation;

    private DomainAttribute attribute;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiPassword() {
        return apiPassword;
    }

    public void setApiPassword(String apiPassword) {
        this.apiPassword = apiPassword;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public DomainAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(DomainAttribute attribute) {
        this.attribute = attribute;
    }
}
