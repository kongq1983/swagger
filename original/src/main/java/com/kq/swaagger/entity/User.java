package com.kq.swaagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author kq
 * @date 2021-06-03 17:35
 * @since 2020-0630
 */
@ApiModel
public class User {

    @ApiModelProperty(value="ID",example = "0")
    private Long id;
    @ApiModelProperty(value="帐号")
    private String username;
    @ApiModelProperty(value="姓名")
    private String name;
    @ApiModelProperty(value="年龄",example = "18")
    private int age;
    @ApiModelProperty(value="地址")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
