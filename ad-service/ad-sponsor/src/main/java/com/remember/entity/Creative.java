package com.remember.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
  * @author remember
  * @date 2020/4/19 12:47
 * 创意实体类
 *      创意本身不与其他实体类相关联
  */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_creative")
public class Creative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Basic
    @Column(name = "name",nullable = false)
    private String name;

    @Basic
    @Column(name = "type",nullable = false)
    private Integer type;

    /**
     * 物料的类型，上边的type属性是主类型，那么当主类型是照片的时候，物料类型可以是png、jpg等等。
     */
    @Basic
    @Column(name = "material_type",nullable = false)
    private Integer materialType;

    @Basic
    @Column(name = "height",nullable = false)
    private Integer height;

    @Basic
    @Column(name = "width",nullable = false)
    private Integer width;

    @Basic
    @Column(name = "size",nullable = false)
    private Long size;

    @Basic
    @Column(name = "duration",nullable = false)
    private Integer duration;

    /*审核状态*/
    @Basic
    @Column(name = "audit_status",nullable = false)
    private Integer auditStatus;

    @Basic
    @Column(name = "user_id",nullable = false)
    private Long userId;

    /*物料地址*/
    @Basic
    @Column(name = "url",nullable = false)
    private String url;

    @Basic
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time",nullable = false)
    private Date updateTime;
}
