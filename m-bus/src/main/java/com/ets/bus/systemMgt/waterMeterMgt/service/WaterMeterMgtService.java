package com.ets.bus.systemMgt.waterMeterMgt.service;

import com.ets.bus.systemMgt.waterMeterMgt.dao.WaterMeterMgtDao;
import com.ets.bus.systemMgt.waterMeterMgt.entity.RoomWaterMeterVo;
import com.ets.bus.systemMgt.waterMeterMgt.entity.WaterMeterMgt;
import com.ets.common.MyConstant;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.ExcelUtils;
import com.ets.utils.UUIDUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class WaterMeterMgtService {
    @Resource
    WaterMeterMgtDao waterMeterMgtDao;

    /**
     * 水表信息列表集合
     *
     * @param map
     * @return
     */
    public List<WaterMeterMgt> getWaterMeterMgts(Map<String, Object> map) {
        return waterMeterMgtDao.getWaterMeterMgts(map);
    }

    /**
     * 水表信息列表的总记录数
     *
     * @param map
     * @return
     */
    public long getCount(Map<String, Object> map) {
        return waterMeterMgtDao.getCount(map);
    }

    /**
     * 保存水表的信息
     *
     * @param waterMeterMgt
     * @return
     */
    public void saveWaterMeter(WaterMeterMgt waterMeterMgt) {
        waterMeterMgtDao.saveWaterMeter(waterMeterMgt);
    }

    /**
     * 根据id得到水表的信息
     *
     * @param id
     * @return
     */
    public WaterMeterMgt getWaterMeterById(String id) {
        return waterMeterMgtDao.getWaterMeterById(id);
    }

    /**
     * 更新水表的信息
     *
     * @param waterMeterMgt
     * @return
     */
    public void updateWaterMeter(WaterMeterMgt waterMeterMgt) {
        waterMeterMgtDao.updateWaterMeter(waterMeterMgt);
    }

    /**
     * 根据id批量删除水表信息
     *
     * @param id
     * @return
     */
    public void delWaterMeterMgts(String[] id) {
        waterMeterMgtDao.delWaterMeterMgts(id);
    }
    /**
     * 根据id批量解除绑定水表信息
     *
     * @param id
     * @return
     */
    public void cancelBind(String[] id) {
        waterMeterMgtDao.cancelBind(id);
    }


    /**
     * 查询未绑定水表的房间集合
     *
     * @param map
     * @return
     */
    public List<RoomWaterMeterVo> getAllRoom(Map<String, Object> map) {
        return waterMeterMgtDao.getAllRoom(map);
    }

    /**
     * 查询未绑定水表的房间记录数
     *
     * @param map
     * @return
     */
    public long getAllRoomCount(Map<String, Object> map) {
        return waterMeterMgtDao.getAllRoomCount(map);
    }

    /**
     * 根据水表的编号查询表中的水表信息的数量
     *
     * @param waterMeterId
     * @return
     */
    public long isCheckWaterMeterId(String waterMeterId) {
        return waterMeterMgtDao.isCheckWaterMeterId(waterMeterId);
    }

    public List<RoomWaterMeterVo> getAllWaterMeter(Map<String, Object> map) {
        return waterMeterMgtDao.getAllWaterMeter(map);
    }

    public long getAllWaterMeterCount(Map<String, Object> map) {
        return waterMeterMgtDao.getAllWaterMeterCount(map);
    }

    /**
     * 根据水表的id查询出 RoomWaterMeterVo 的属性值
     *
     * @param id
     * @return
     */
    public RoomWaterMeterVo waterMeterRoomInfo(String id) {
        return waterMeterMgtDao.waterMeterRoomInfo(id);
    }

    /**
     * 批量导入
     *
     * @param file
     */
    public void upload(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            List<Object> list = ExcelUtils.getInstance().readExcel2ObjsByStream(inputStream, MyConstant.EXCEL_TEM_ADDWATERTemplate, "sheet1", WaterMeterMgt.class);
            List<WaterMeterMgt> failList = Lists.newArrayList();
            List<WaterMeterMgt> waterMeterMgts = parseResult(list);
            //校验
            filterIllegal(waterMeterMgts, failList);
            checkDbResult(waterMeterMgts, failList);
            if (failList.isEmpty()) {
                waterMeterMgtDao.batchInsert(waterMeterMgts);
            } else {
                StringBuilder sb = new StringBuilder();
                String failFileName = appendFailReason(file, waterMeterMgts, sb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<WaterMeterMgt> parseResult(List<Object> list) {
        List<WaterMeterMgt> mgtList = list.stream().map(item -> (WaterMeterMgt) item).collect(Collectors.toList());
        mgtList.forEach(item -> {
            item.setId(UUIDUtils.getUUID());
            item.setCreate_time(DateTimeUtils.getnowdate());
            item.setDelStatus(0);
        });
        return mgtList;
    }

    private void checkDbResult(List<WaterMeterMgt> list, List<WaterMeterMgt> fail) {
        for (WaterMeterMgt item : list) {
            long checkWaterMeterId = waterMeterMgtDao.isCheckWaterMeterId(item.getWater_meter_id());
            if (0 != checkWaterMeterId) {
                item.setFailResult("数据库已经存在的水表编号");
                fail.add(item);
            }
        }
    }

    private void filterIllegal(List<WaterMeterMgt> allRecords, List<WaterMeterMgt> fail) {
        allRecords.stream().forEach(t -> {
            if (StringUtils.isEmpty(t.getWater_meter_id())) {
                t.setFailResult("水表编号不能为空");
                fail.add(t);
            }
        });
    }

    /**
     * 暂时不校验，可以让成功的先入库
     *
     * @param success
     * @param fail
     * @param allRecords
     */
    private void filterRepeat(List<WaterMeterMgt> success, List<WaterMeterMgt> fail, List<WaterMeterMgt> allRecords) {
        Map<String, List<WaterMeterMgt>> map = allRecords.stream()
                .filter(r -> StringUtils.isEmpty(r.getFailResult()))
                .collect(Collectors.groupingBy(WaterMeterMgt::getName));
        for (Map.Entry<String, List<WaterMeterMgt>> entry : map.entrySet()) {
            int size = entry.getValue().size();
            if (size > 1) {
                // 有重复的,第一条成功，后面的几条失败
                success.add(entry.getValue().get(0));
                entry.getValue().remove(0);
                entry.getValue().stream().forEach(p -> p.setFailResult("不能重复创建"));
                fail.addAll(entry.getValue());
            } else {
                success.addAll(entry.getValue());
            }
        }

    }

    private String appendFailReason(MultipartFile file, List<WaterMeterMgt> allRecords,
                                    StringBuilder sb) throws Exception {
        /**
         * 1  从输入流中获取信息
         */
        InputStream tempStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        //文件先存放在resources下
        //String failFileName = getClass().getResource("/template/").getPath()+ fileName;
        String failFileName = "logs/"+fileName;
        new File(failFileName).delete();
        /**
         * 2 在临时目录中，以当前项目创建文件夹，在原文件中追加失败原因列，再保存
         */
        ExcelUtils.getInstance().addColumn(tempStream,
                allRecords.stream().map(t -> (Object) t).collect(Collectors.toList()),
                "sheet1",
                WaterMeterMgt.class, "失败明细",
                failFileName);

        return failFileName;
    }
}
