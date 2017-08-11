package per.posse.tool.service.impl.domain;

import per.posse.tool.enums.domain.Gender;
import per.posse.tool.service.impl.persistence.ToolUserRepositoryJPA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Created by posse on 17-7-20.
 */
@Entity
@Table(name = "tool_user")
@NamedQueries({
    @NamedQuery(name = ToolUserRepositoryJPA.UPDATE_TOOL_USER, query = "update ToolUser t set "
            + "t.name = :name, t.age = :age, t.gender = :gender, t.idNum = :idNum, t.address = :address, t.mobile = :mobile,"
            + "t.loginEmail = :loginEmail, t.loginPassword = :loginPassword, "
            + "t.apiName = :apiName, t.apiPassword = :apiPassword, "
            + "t.logout = :logout, t.enableConcurrentLogin = :enableConcurrentLogin "
            + "where t.id = :id")
})
public class ToolUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private Gender gender;

    @Column(length = 18)
    private String idNum;

    @Column(length = 255)
    private String address;

    @Column(length = 11)
    private String mobile;

    @Column(length = 32)
    private String loginEmail;

    @Column(length = 32)
    private String loginPassword;

    @Column(name = "api_name", length = 32)
    private String apiName;

    @Column(name = "api_password", length = 32)
    private String apiPassword;

    @Column(name = "logout")
    private Boolean logout = Boolean.FALSE;

    @Column(name = "enable_concurrent_login")
    private Boolean enableConcurrentLogin = Boolean.FALSE;

    public ToolUser() {
    }

    public ToolUser(Long id, String name, Integer age, Gender gender, String idNum, String address, String mobile,
            String loginEmail, String loginPassword, String apiName, String apiPassword, Boolean logout, Boolean enableConcurrentLogin) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.idNum = idNum;
        this.address = address;
        this.mobile = mobile;
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        this.apiName = apiName;
        this.apiPassword = apiPassword;
        this.logout = logout;
        this.enableConcurrentLogin = enableConcurrentLogin;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

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

    public Boolean getLogout() {
        return logout;
    }

    public void setLogout(Boolean logout) {
        this.logout = logout;
    }

    public Boolean getEnableConcurrentLogin() {
        return enableConcurrentLogin;
    }

    public void setEnableConcurrentLogin(Boolean enableConcurrentLogin) {
        this.enableConcurrentLogin = enableConcurrentLogin;
    }
}
