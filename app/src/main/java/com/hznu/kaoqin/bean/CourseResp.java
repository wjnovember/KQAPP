package com.hznu.kaoqin.bean;

/**
 * Created by 代码咖啡 on 17/3/27
 * <p>
 * Email: wjnovember@icloud.com
 */

public class CourseResp {
    private String id;
    private String name;
    private int start_week;
    private int end_week;
    private int day_in_week;
    private int start_class;
    private int end_class;
    private String teacher_id;
    private String classroom;

    public CourseResp() {

    }

    public CourseResp(String id, String name, int start_week, int end_week, int day_in_week, int start_class, int end_class, String teacher_id, String classroom) {
        this.id = id;
        this.name = name;
        this.start_week = start_week;
        this.end_week = end_week;
        this.day_in_week = day_in_week;
        this.start_class = start_class;
        this.end_class = end_class;
        this.teacher_id = teacher_id;
        this.classroom = classroom;
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

    public int getStartWeek() {
        return start_week;
    }

    public void setStartWeek(int start_week) {
        this.start_week = start_week;
    }

    public int getEndWeek() {
        return end_week;
    }

    public void setEndWeek(int end_week) {
        this.end_week = end_week;
    }

    public int getDayInWeek() {
        return day_in_week;
    }

    public void setDayInWeek(int day_in_week) {
        this.day_in_week = day_in_week;
    }

    public int getStartClass() {
        return start_class;
    }

    public void setStartClass(int start_class) {
        this.start_class = start_class;
    }

    public int getEndClass() {
        return end_class;
    }

    public void setEndClass(int end_class) {
        this.end_class = end_class;
    }

    public String getTeacherId() {
        return teacher_id;
    }

    public void setTeacherId(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
