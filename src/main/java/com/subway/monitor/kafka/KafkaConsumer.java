package com.subway.monitor.kafka;

import com.subway.monitor.model.Book;
import com.subway.monitor.service.MongoDbService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
public class KafkaConsumer {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    private MongoDbService mongoDbService;
    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 监听kafka.tut 的topic，不做其他业务
     *
     * @param record
     * @param topic  topic
     */
    @KafkaListener(id = "data",groupId="dataBase", topics = "test")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            Document document = new Document();
            document.append("topic", topic).append("Record", record).append("mesage", message.toString());
            mongoTemplate.insert(document, "subwayData");
            logger.info("Receive： +++++++monitor++++++++ Topic:" + topic);
            logger.info("Receive： ++++++++monitor+++++++ Record:" + record);
            logger.info("Receive： ++++++++monitor+++++++ Message:" + message);
        }
    }

}