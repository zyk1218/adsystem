package com.remember.service;

import com.alibaba.fastjson.JSON;
import com.remember.Application;
import com.remember.constant.CommonStatus;
import com.remember.dao.AdPlanRepository;
import com.remember.dao.AdUnitRepository;
import com.remember.dao.CreativeRepository;
import com.remember.dao.unit_condition.AdUnitDistrictRepository;
import com.remember.dao.unit_condition.AdUnitItRepository;
import com.remember.dao.unit_condition.AdUnitKeywordRepository;
import com.remember.dao.unit_condition.CreativeUnitRepository;
import com.remember.dump.table.AdCreativeTable;
import com.remember.dump.table.AdPlanTable;
import com.remember.dump.table.AdUnitTable;
import com.remember.entity.AdPlan;
import com.remember.entity.AdUnit;
import com.remember.entity.Creative;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
  * @author remember
  * @date 2020/4/24 11:18
 * 测试数据导出的方法
  */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},webEnvironment = SpringBootTest.WebEnvironment.NONE)
@SuppressWarnings("all")
public class DumpDataService {
    @Autowired
    private AdPlanRepository planRepository;
    @Autowired
    private AdUnitRepository unitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private AdUnitKeywordRepository unitKeywordRepository;
    @Autowired
    private AdUnitItRepository unitItRepository;
    @Autowired
    private AdUnitDistrictRepository unitDistrictRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;

    private void dumpAdPlanTable(String fileName){
        List<AdPlan> adPlans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if(CollectionUtils.isEmpty(adPlans)){
            return;
        }
        List<AdPlanTable> planTables = new ArrayList<>();
        //将表填充
        adPlans.forEach(i -> planTables.add(new AdPlanTable(i.getId(),i.getUserId(),i.getPlanStatus(),i.getStartTime(),i.getEndDate())));
        //将填充之后的表持久化到本地文件中
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for(AdPlanTable planTable:planTables){
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
            writer.close();
        }catch (IOException e){
            log.error("dumpAdPlanTable error");
        }
    }
    private void dumpAdUnitTable(String fileName){
        List<AdUnit> adUnits = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if(CollectionUtils.isEmpty(adUnits)){
            return;
        }
        List<AdUnitTable> unitTables = new ArrayList<>();
        //将表填充
        adUnits.forEach(i -> unitTables.add(new AdUnitTable(i.getId(),i.getUnitStatus(),i.getPositionType(),i.getPlanId())));
        //将填充之后的表持久化到本地文件中
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for(AdUnitTable unitTable:unitTables){
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
            writer.close();
        }catch (IOException e){
            log.error("dumpAdUnitTable error");
        }
    }
    private void dumpCreativeTable(String fileName){
        List<Creative> creatives = creativeRepository.findAll();
        if(CollectionUtils.isEmpty(creatives)){
            return;
        }
        List<AdCreativeTable> creativeTables = new ArrayList<>();
        //将表填充
        creatives.forEach(i -> creativeTables.add(new AdCreativeTable(i.getId(),i.getName(),i.getType(),
                i.getMaterialType(),i.getHeight(),i.getWidth(),i.getAuditStatus(),i.getUrl())));
        //将填充之后的表持久化到本地文件中
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for(AdCreativeTable creativeTable:creativeTables){
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
            writer.close();
        }catch (IOException e){
            log.error("dumpAdUnitTable error");
        }
    }
}
