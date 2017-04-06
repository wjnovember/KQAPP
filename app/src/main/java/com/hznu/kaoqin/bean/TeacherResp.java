package com.hznu.kaoqin.bean;

/**
 * Created by 代码咖啡 on 17/3/28
 * <p>
 * Email: wjnovember@icloud.com
 */

public class TeacherResp {

    private String id;
    private String name;
    private String phone;

    public TeacherResp() {
    }

    public TeacherResp(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
