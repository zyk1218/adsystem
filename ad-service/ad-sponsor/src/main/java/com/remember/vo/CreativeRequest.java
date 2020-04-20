package com.remember.vo;

import com.remember.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  * @author remember
  * @date 2020/4/20 18:37
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeRequest {
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Long size;
    private Integer duration;
    private Long userId;
    private String url;

    //将request变成pojo
    public Creative convertToEntity(){
        Creative creative = new Creative();
        creative.setName(name);
        creative.setDuration(duration);
        creative.setHeight(height);
        creative.setWidth(width);
        creative.setMaterialType(materialType);
        creative.setUrl(url);
        creative.setUserId(userId);
        creative.setType(type);
        creative.setSize(size);
        creative.setCreateTime(new Date());
        creative.setUpdateTime(creative.getCreateTime());
        return creative;
    }
}
