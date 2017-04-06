package com.hznu.kaoqin.bean;

import java.util.List;

/**
 * Created by 代码咖啡 on 17/3/28
 * <p>
 * Email: wjnovember@icloud.com
 */

public class TeacherCoursesResp {

    private TeacherResp teacher;
    private List<CourseResp> courses;

    public TeacherCoursesResp() {
    }

    public TeacherCoursesResp(TeacherResp teacher, List<CourseResp> courses) {
        this.teacher = teacher;
        this.courses = courses;
    }

    public TeacherResp getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherResp teacher) {
        this.teacher = teacher;
    }

    public List<CourseResp> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseResp> courses) {
        this.courses = courses;
    }
}
