package com.subway.monitor.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/***
 * @author: Lijiwen
 * Description:监控数据model
 * @createDate
 **/
@Data
@Accessors(chain = true)
public class MonitorData {

    private long id;
    private String xieyi;
    private long length;
    private String deviceId;
    private String command;
    private String mVstate;
    private String mAstate;
    private String mTstate;
    private String dVstate;
    private String dAstate;
    private String dTstate;
    private String degree;
    private String crcCode;
    private Timestamp created_at;
}
