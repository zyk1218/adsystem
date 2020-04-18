package com.remember.entity;

import com.remember.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
  * @author remember
  * @date 2020/4/18 18:28
 * 用户实体类
  */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ad_user")
@Entity
public class AdUser {

    @Id //该注解表明该属性是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //声明主键的生成方式--按数据库策略生成
    @Column(name = "id",nullable = false) //对应数据库表中的列
    private Long id;

    @Basic //字段默认的类型，代表数据表中的基本类型。
    //@Transient 默认是Basic,那么如果定义的属性不是表中的数据的话就使用这个注解。
    @Column(name = "username",nullable = false)
    private String username;

    @Basic
    @Column(name = "token",nullable = false)
    private String token;

    @Basic
    @Column(name = "user_status",nullable = false)
    private Integer userStatus;//用户的状态：被删除、正常等等。

    @Basic
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time",nullable = false)
    private Date updateTime;

    /*
    由于用户创建的时候id是自增的，用户状态是固定的，创建时间和更新时间就是当前时间，不需要指定，所以该构造函数只传入两个字段
     */
    public AdUser(String username,String token){
        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALID.getStatus();
        this.createTime = new Date();
        this.updateTime = this.createTime;

    }
}
