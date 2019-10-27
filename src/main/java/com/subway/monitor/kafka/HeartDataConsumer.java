package com.subway.monitor.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.subway.monitor.model.HeartData;
import com.subway.monitor.service.HeartDataService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 消息消费者
 *
 * @author lijiwen
 * @date 2018/8/3
 */
@Component
public class HeartDataConsumer {
    @Autowired
    private HeartDataService heartDataService;
    private Logger logger = LoggerFactory.getLogger(CoreDataConsumer.class);

    /**
     * 监听kafka.tut 的topic，不做其他业务
     *
     * @param record
     * @param topic  topic
     */
    @KafkaListener(groupId="heart",id = "heart",topics = "heart_topic")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String message = (String) kafkaMessage.get();
            JSONArray jsonArray = JSON.parseArray(message);
            HeartData heartData = new HeartData();
            heartData.setIp(jsonArray.get(0).toString());
            heartData.setStateCode(jsonArray.get(1).toString());
            heartData.setDeviceId(jsonArray.get(2).toString());
            heartDataService.createData(heartData);
        }
    }

}