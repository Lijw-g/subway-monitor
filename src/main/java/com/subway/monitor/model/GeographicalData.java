package com.subway.monitor.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: subway-monitor
 * @description: 地理位置实体类
 * @author: lijiwen
 * @create: 2019-10-27 19:47
 **/
@Data
@Accessors(chain = true)
public class GeographicalData {
    private String date;
    private String time;
    private String lat;
    private String lng;
    private String speed;
    private String direction;
    private String sign;

}
