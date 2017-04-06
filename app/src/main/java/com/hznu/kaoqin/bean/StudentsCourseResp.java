package com.hznu.kaoqin.bean;

import java.util.List;

/**
 * Created by 代码咖啡 on 17/3/29
 * <p>
 * Email: wjnovember@icloud.com
 */

public class StudentsCourseResp {

    private CourseResp course;
    private List<StudentResp> students;

    public StudentsCourseResp() {
    }

    public StudentsCourseResp(CourseResp course, List<StudentResp> students) {
        this.course = course;
        this.students = students;
    }

    public CourseResp getCourse() {
        return course;
    }

    public void setCourse(CourseResp course) {
        this.course = course;
    }

    public List<StudentResp> getStudents() {
        return students;
    }

    public void setStudents(List<StudentResp> students) {
        this.students = students;
    }
}
