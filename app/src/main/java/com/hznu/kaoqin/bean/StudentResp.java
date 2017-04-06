package com.hznu.kaoqin.bean;

/**
 * Created by 代码咖啡 on 17/3/29
 * <p>
 * Email: wjnovember@icloud.com
 */

public class StudentResp {

    private String id;
    private String name;
    private int gender;
    private String department_name;
    private int class_number;
    private String card_number;
    private String course_id;

    public StudentResp() {
    }

    public StudentResp(String id, String name, int gender, int class_number, String card_number, String course_id) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.class_number = class_number;
        this.card_number = card_number;
        this.course_id = course_id;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDepartmentName() {
        return department_name;
    }

    public void setDepartmentName(String department_name) {
        this.department_name = department_name;
    }

    public int getClassNumber() {
        return class_number;
    }

    public void setClassNumber(int class_number) {
        this.class_number = class_number;
    }

    public String getCardNumber() {
        return card_number;
    }

    public void setCardNumber(String card_number) {
        this.card_number = card_number;
    }

    public String getCourseId() {
        return course_id;
    }

    public void setCourseId(String course_id) {
        this.course_id = course_id;
    }
}
