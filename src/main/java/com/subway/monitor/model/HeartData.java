package com.subway.monitor.model;

import lombok.Data;

/**
 * @program: subway-monitor
 * @description: 心跳数据
 * @author: lijiwen
 * @create: 2019-10-27 13:37
 **/
@Data
public class HeartData {
    private Integer id;
    private String ip;
    private String deviceId;
    private String stateCode;
}
