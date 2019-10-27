package com.subway.monitor.service;

import com.subway.monitor.model.GeographicalData;
import com.subway.monitor.model.HeartData;
import org.springframework.stereotype.Service;

/**
 * @program: subway-monitor
 * @description: 地理位置信息
 * @author: lijiwen
 * @create: 2019-10-27 21:40
 **/
@Service
public class GeographicalDataService extends  AbstractService {
    public int createData(GeographicalData geographicalData) {
        return sqlSession.insert("geographicalData.insert", geographicalData);
    }
}
