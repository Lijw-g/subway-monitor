package com.subway.monitor.kafka;

import com.subway.monitor.model.GeographicalData;
import com.subway.monitor.service.GeographicalDataService;
import com.subway.monitor.service.log.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Optional;

/**
 * @program: subway-monitor
 * @description: 地理位置信息
 * @author: lijiwen
 * @create: 2019-10-27 19:46
 **/
@Component
public class GeographicalDataConsumer {
    @Autowired
    private GeographicalDataService geographicalDataService;
    private Logger logger = LoggerFactory.getLogger(GeographicalDataConsumer.class);

    @KafkaListener(groupId = "geographical", id = "geo", topics = "railway_geographical_topic")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String data = String.valueOf(kafkaMessage.get()).replace("\"", "");

            String date = buildDate(data.substring(0, 6));
            String time = buildTime(data.substring(6, 12));
            String lat = buildLat(data.substring(12, 20));
            String lng = buildLng(data.substring(20, 28));
            String speed = buildSpeed(data.substring(28, 32));
            String direction = buildDirection(data.substring(32, 36));
            /**
             标志为0F，表示0x0F，表示东经，北纬，3D定位(
             1字节十六进制数，GPS状态字
             Bit0     1—东经，0—西经。
             Bit1     1—北纬，0—南纬。
             Bit2-3   00---未定位01---2D定位 11---3D定位
             Bit4-7 保留。 )*/
            String sign = String.valueOf(Long.parseLong(data.substring(36, 37), 16));
            GeographicalData geographicalData = new GeographicalData();
            geographicalData.setDate(date)
                    .setTime(time)
                    .setLat(lat)
                    .setLng(lng)
                    .setSpeed(speed)
                    .setDirection(direction).setSign(sign);
            geographicalDataService.createData(geographicalData);
        }

    }

    private String buildDirection(String dateInfo) {
        /***方向为E70D,表示0x0DE7，转成度为0x0DE7/10=355.9度*/
        StringBuilder dir = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.00");
        dir.append(dateInfo.substring(2, 4));
        dir.append(dateInfo.substring(0, 2));
        long speed = Long.parseLong(dir.toString(), 16);
        double speedNum = Double.valueOf(speed) / 10;
        return df.format(speedNum);
    }

    private String buildSpeed(String speedstring) {
        /***速度为4103，表示0x0341cm/s，转成km/h为0x0341*3600/100000= 29.988km/h*/
        StringBuilder data = new StringBuilder();
        data.append(speedstring.substring(2, 4));
        data.append(speedstring.substring(0, 2));
        long speed = Long.parseLong(data.toString(), 16);
        double speedNum = Double.valueOf(speed) * 3600 / 100000;
        return String.valueOf(speedNum);
    }

    private String buildLng(String lngstring) {
        /***经度为E0E48B18，表示0x188BE4E0，转成度单位是0x188BE4E0/3600000= 114.3948度*/
        double lngNum = getLatAndLngNum(lngstring);
        return String.valueOf(lngNum);
    }

    private String buildLat(String latString) {
        /***纬度为A099D004，表示0x04D099A0，转成度单位是0x/3600000= 22.4388度*/
        double latNum = getLatAndLngNum(latString);
        return String.valueOf(latNum);
    }

    private double getLatAndLngNum(String latString) {
        StringBuilder data = new StringBuilder();
        data.append(latString.substring(6, 8));
        data.append(latString.substring(4, 6));
        data.append(latString.substring(2, 4));
        data.append(latString.substring(0, 2));
        long lat = Long.parseLong(data.toString(), 16);
        return Double.valueOf(lat) / 3600000;
    }

    private String buildTime(String timeString) {
        /**时间段为173B3A，17为0x17，表示23时，3B为0x3B，表示59分，3A为0x3A，表示58秒*/
        StringBuilder time = new StringBuilder();
        time.append(Long.parseLong(timeString.substring(0, 2), 16) + ":");
        time.append(Long.parseLong(timeString.substring(2, 4), 16) + ":");
        time.append(Long.parseLong(timeString.substring(4, 6), 16));
        return time.toString();
    }

    private String buildDate(String dateInfo) {
        /**日期段为12010A，12为0x12,表示18日，01为0x01，表示1月，0A为0x0A，表示10年*/
        StringBuilder date = new StringBuilder();
        int size = dateInfo.length();
        date.append(Long.parseLong(dateInfo.substring(size - 2, size), 16) + "-");
        date.append(Long.parseLong(dateInfo.substring(size - 4, size - 2), 16) + "-");
        date.append(Long.parseLong(dateInfo.substring(size - 6, size - 4), 16));
        return date.toString();
    }

}
