package com.remember.entity;

import com.remember.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
  * @author remember
  * @date 2020/4/19 9:36
 * 推广计划
  */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_plan")
public class AdPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Basic
    @Column(name = "user_id",nullable = false) //为什么不是外键而是字段：
                                                //1.外键占用空间
                                                //2.外键与母表之间有关联，一旦母表出错，子表很难修复
                                                //3.在数据表迁移、分库分表时，若存在外键不易操作。
                                                //4.所以通常我们是将外键约束在应用程序中完成的，而不是在表中定义的。
    /**
      * @author remember
      * @date 2020/4/19 9:49
     * 关于互联网上经常出现的一种说法：尽量不要使用外键：
     *  这里说的外键指的是外键约束，当需要用外键关联时通常会抽象出关联键成为一张表
      */
    private Long UserId;

    @Basic
    @Column(name = "plan_name",nullable = false)
    private String planName;

    @Basic
    @Column(name = "pan_status",nullable = false)
    private Integer planStatus;

    @Basic
    @Column(name = "start_date",nullable = false)
    private Date startTime;

    @Basic
    @Column(name = "end_date",nullable = false)
    private Date endDate;

    @Basic
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time",nullable = false)
    private Date updateTime;

    public AdPlan(Long userId,String planName,Date startTime,Date endDate){
        this.UserId = userId;
        this.planName = planName;
        this.startTime = startTime;
        this.endDate  = endDate;
        this.createTime = new Date();
        this.updateTime = this.createTime;
        this.planStatus = CommonStatus.VALID.getStatus();
    }

}
