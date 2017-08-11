package per.posse.tool.dto;

import per.posse.tool.enums.domain.Gender;

/**
 * Created by posse on 17-7-25.
 */
public class ToolUserDto {

    private Long id;

    private String name;

    private Integer age;

    private Gender gender;

    private String idNum;

    private String address;

    private String mobile;

    private String loginEmail;

    private String loginPassword;

    private String apiName;

    private String apiPassword;

    private Boolean logout = Boolean.FALSE;

    private Boolean enableConcurrentLogin = Boolean.FALSE;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
