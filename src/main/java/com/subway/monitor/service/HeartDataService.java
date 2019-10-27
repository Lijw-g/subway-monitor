package com.subway.monitor.service;

import com.subway.monitor.model.HeartData;
import com.subway.monitor.model.MonitorData;
import org.springframework.stereotype.Service;

/**
 * @program: subway-monitor
 * @description: 心跳数据
 * @author: lijiwen
 * @create: 2019-10-27 13:50
 **/
@Service
public class HeartDataService extends AbstractService {
    public int createData(HeartData heartData) {
        return sqlSession.insert("heartData.insert", heartData);
    }
}
