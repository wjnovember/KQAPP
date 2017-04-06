package com.hznu.kaoqin.net.retrofit;

import com.hznu.kaoqin.bean.ArrivalResp;
import com.hznu.kaoqin.bean.BaseResponse;
import com.hznu.kaoqin.bean.LoginResp;
import com.hznu.kaoqin.bean.LogoutResp;
import com.hznu.kaoqin.bean.StudentResp;
import com.hznu.kaoqin.bean.StudentsCourseResp;
import com.hznu.kaoqin.bean.TeacherCoursesResp;
import com.hznu.kaoqin.bean.TeacherResp;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 代码咖啡 on 17/3/27
 * <p>
 * Email: wjnovember@icloud.com
 */

public interface RetrofitAPI {

    /**
     * 登录接口
     * @param account
     * @param pwd
     * @return
     */
    @GET("login.php")
    Observable<BaseResponse<LoginResp>> login(
            @Query("account") String account,
            @Query("pwd") String pwd
    );

    /**
     * 登出（退出登录）接口
     * @param id
     * @param token
     * @return
     */
    @GET("logout.php")
    Observable<BaseResponse<LogoutResp>> logout(
            @Query("id") String id,
            @Query("token") int token
    );

    /**
     * 判断用户是否在线
     * @param id
     * @param token
     * @return
     */
    @GET("isOnline.php")
    Observable<BaseResponse<TeacherResp>> isOnLine(
            @Query("id") String id,
            @Query("token") int token
    );

    /**
     * 获取教师的所有课程
     * @param teacherId
     * @param token
     * @return
     */
    @GET("getCourses.php")
    Observable<BaseResponse<TeacherCoursesResp>> getCourses(
            @Query("teacherId") String teacherId,
            @Query("token") int token
    );

    /**
     * 获取听这门课的所有学生
     * @param courseId
     * @param teacherId
     * @param token
     * @return
     */
    @GET("getStudentsByCourse.php")
    Observable<BaseResponse<StudentsCourseResp>> getStudentsByCourse(
            @Query("courseId") String courseId,
            @Query("teacherId") String teacherId,
            @Query("token") int token
    );

    /**
     * 获取教师的所有课程的名字
     * @param teacherId
     * @param token
     * @return
     */
    @GET("getCourseNames.php")
    Observable<BaseResponse<List<String>>> getCourseNames(
            @Query("teacherId") String teacherId,
            @Query("token") int token
    );

    /**
     * 获取当前课程所有到勤记录的日期
     * @param teacherId
     * @param courseName
     * @param token
     * @return
     */
    @GET("getArrivalDate.php")
    Observable<BaseResponse<List<String>>> getArrivalDate(
            @Query("teacherId") String teacherId,
            @Query("courseName") String courseName,
            @Query("token") int token
    );

    @GET("getArrival.php")
    Observable<BaseResponse<List<ArrivalResp>>> getArrival(
            @Query("teacherId") String teacherId,
            @Query("courseName") String courseName,
            @Query("arrivalDate") String arrivalDate,
            @Query("token") int token
    );
}
