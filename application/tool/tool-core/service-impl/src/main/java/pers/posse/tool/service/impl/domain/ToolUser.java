package pers.posse.tool.service.impl.domain;

import domain.enums.Gender;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by posse on 17-7-20.
 */
@Entity
@Table(name = "tool_user")
//@SequenceGenerator(name = "seq_tool_user", sequenceName = "seq_tool_user")
public class ToolUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tool_user")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private Gender gender;

    @Column(length = 18)
    private String idNum;

    @Column
    private String address;

    @Column
    private String mobile;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "api_password")
    private String apiPassword;

    public ToolUser() {
    }

    public ToolUser(Long id, String name, Integer age, Gender gender, String idNum, String address, String mobile,
            String apiName, String apiPassword) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.idNum = idNum;
        this.address = address;
        this.mobile = mobile;
        this.apiName = apiName;
        this.apiPassword = apiPassword;
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
}
