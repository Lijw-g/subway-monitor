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
    @KafkaListener(id = "data", groupId = "dataBase", topics = "test")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Document document = new Document();
            String data = kafkaMessage.get().toString();
            String xiyie = data.substring(0, 4);
            String len = data.substring(4, 8);
            String diviceId = data.substring(8, 22);
            String mingling = data.substring(22, 26);
            String datas = data.substring(26, 76);
            //处理传输的数据

            String crc16 = data.substring(76, 80);
            String end = data.substring(80, 84);


            document.append("xiyie", xiyie).append("len", len).append("diviceId", diviceId).append("mingling", mingling)
                    .append("Type", "01").append("JZdata", datas.substring(2, 10)).append("M_Vstate", "110.53").append("M_Astate", "-1.33")
                    .append("M_Tstate", "23.02").append("D_Vstate", "10.18").append("D_Astate", "-1.09")
                    .append("D_Tstate", "33.84").append("Degree", "0.21").append("crc16", crc16).append("end", end);
            mongoTemplate.insert(document, "subwayData");
            logger.info("Receive： +++++++monitor++++++++ Topic:" + topic);
            logger.info("Receive： ++++++++monitor+++++++ Record:" + record);
            logger.info("Receive： ++++++++monitor+++++++ Message:" + data);
        }
    }


}