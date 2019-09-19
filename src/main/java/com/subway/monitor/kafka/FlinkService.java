package com.subway.monitor.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class FlinkService {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    /**
     * 监听kafka.tut 的topic，不做其他业务
     *
     * @param record
     * @param topic  topic
     */
    @KafkaListener(groupId="ff", topics = "test")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            logger.info("Receive： +++++++flink++++++++ Topic:" + topic);
            logger.info("Receive： ++++++++flink+++++++ Record:" + record);
            logger.info("Receive： ++++++++flink+++++++ Message:" + message);
        }
    }

}