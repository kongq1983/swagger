package com.kq.swagger.customize.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author kq
 * @date 2021-06-15 10:13
 * @since 2020-0630
 */

@ApiModel
public class School {

    @ApiModelProperty(value="老师")
    private List<Teacher> teacherList;

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @Override
    public String toString() {
        return "School{" +
                "teacherList=" + teacherList +
                '}';
    }
}
