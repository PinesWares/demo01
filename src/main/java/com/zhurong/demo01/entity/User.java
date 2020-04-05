package com.zhurong.demo01.entity;

public class User extends OnePagePara{
    private int id;
    private String name;
    private String password;
    private String phone;
    private String birthday;
    private int dataCount;
    private String image;
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public int getDataCount() { return dataCount;}
    public void setDataCount(int dataCount) {this.dataCount = dataCount;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id; }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password =password;}
    public String getNumber() { return phone;}
    public void setNumber(String number) {this.phone = number;}
    public String getBirthday() {return birthday;}
    public void setBirthday(String birthday) {this.birthday = birthday; }
}
