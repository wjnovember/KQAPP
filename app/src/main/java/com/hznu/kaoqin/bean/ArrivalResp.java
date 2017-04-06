package com.hznu.kaoqin.bean;

/**
 * Created by 代码咖啡 on 17/3/29
 * <p>
 * Email: wjnovember@icloud.com
 */

public class ArrivalResp {
    private String id;
    private String name;
    private int gender;
    private String department_name;
    private int class_number;
    private String card_number;
    private String course_id;
    private String arrival_date;
    private String arrival_time;

    public ArrivalResp() {
    }

    public ArrivalResp(String id, String name, int gender, String department_name, int class_number,
                       String card_number, String course_id, String arrival_date, String arrival_time) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.department_name = department_name;
        this.class_number = class_number;
        this.card_number = card_number;
        this.course_id = course_id;
        this.arrival_date = arrival_date;
        this.arrival_time = arrival_time;
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

    public String getArrivalDate() {
        return arrival_date;
    }

    public void setArrivalDate(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public String getArrivalTime() {
        return arrival_time;
    }

    public void setArrivalTime(String arrival_time) {
        this.arrival_time = arrival_time;
    }
}
