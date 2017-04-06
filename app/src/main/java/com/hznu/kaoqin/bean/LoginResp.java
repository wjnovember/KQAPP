package com.hznu.kaoqin.bean;

/**
 * Created by 代码咖啡 on 17/3/27
 * <p>
 * Email: wjnovember@icloud.com
 */

public class LoginResp {

    private String id;
    private String name;
    private int token;

    public LoginResp() {

    }

    public LoginResp(String id, String name, int token) {
        this.id = id;
        this.name = name;
        this.token = token;
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

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
