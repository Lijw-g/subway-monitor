package com.subway.monitor.kafka;

import com.subway.monitor.model.MonitorData;
import com.subway.monitor.service.MonitorDataService;
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
    //    @Autowired
//    private MongoTemplate mongoTemplate;
    @Autowired
    private MonitorDataService monitorDataService;

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
            String data = String.valueOf(kafkaMessage.get()).replace("\"", "");
            String xieyi = data.substring(0, 4);
            String len = data.substring(4, 8);
            String deviceId = data.substring(8, 22);
            String mingling = data.substring(22, 26);
            String datas = data.substring(26, 76);
            //处理传输的数据
            String M_Vstate = getStatusValue(datas.substring(10, 16));
            String M_Astate = getStatusValue(datas.substring(16, 22));
            String M_Tstate = getStatusValue(datas.substring(22, 28));
            String D_Vstate = getStatusValue(datas.substring(28, 34));
            String D_Astate = getStatusValue(datas.substring(34, 40));
            String D_Tstate = getStatusValue(datas.substring(40, 46));
            String degree = getDegreeValue(datas.substring(46, 50));
            String crc16 = data.substring(76, 80);
            String end = data.substring(80, 84);
            MonitorData monitorData = new MonitorData().setXieyi(xieyi)
                    .setLength(Long.valueOf(Long.parseLong(len, 16)))
                    .setDeviceId(deviceId)
                    .setCommand(mingling)
                    .setMVstate(M_Vstate)
                    .setMAstate(M_Astate)
                    .setMTstate(M_Tstate)
                    .setDVstate(D_Vstate)
                    .setDAstate(D_Astate)
                    .setDTstate(D_Tstate)
                    .setDegree(degree)
                    .setCrcCode(crc16);
            logger.info("---门控器电压：" + M_Vstate + "V");
            logger.info("---电流：" + M_Astate + "A");
            logger.info("---温度：" + M_Tstate + "℃");
            logger.info("---门电机电压：" + D_Vstate + "V");
            logger.info("---电流：" + D_Astate + "A");
            logger.info("---温度：" + D_Tstate + "℃");
            logger.info("---门开度：" + degree + "m");
            int result = monitorDataService.createData(monitorData);
            logger.info("add result is "+(result==1));
//            document.append("xiyie", xieyi).append("len", Long.parseLong(len, 16)).append("diviceId", deviceId).append("mingling", mingling)
//                    .append("Type", "01").append("JZdata", datas.substring(2, 10)).append("M_Vstate", M_Vstate).append("M_Astate", M_Astate)
//                    .append("M_Tstate", M_Tstate).append("D_Vstate", D_Vstate).append("D_Astate", D_Astate)
//                    .append("D_Tstate", D_Tstate).append("Degree", degree).append("crc16", crc16).append("end", end);
//             mongoTemplate.insert(document, "subwayData");
        }
    }

    public static String getStatusValue(String data) {
        Long fuhao = Long.parseLong(data.substring(0, 2), 16);
        Long zhengshu = Long.parseLong(data.substring(2, 4), 16);
        Long xiaoshu = Long.parseLong(data.substring(4, 6), 16);
        if (xiaoshu < 10) {
        }
        StringBuilder value = new StringBuilder();
        if (fuhao == 0L) {
            value.append("-");
        }
        if (xiaoshu < 10L) {
            value.append(zhengshu).append('.').append("0" + xiaoshu);

        } else {
            value.append(zhengshu).append('.').append(xiaoshu);

        }
        return value.toString();
    }

    public static String getDegreeValue(String data) {
        Long zhengshu = Long.parseLong(data.substring(0, 2), 16);
        Long xiaoshu = Long.parseLong(data.substring(2, 4), 16);
        StringBuilder value = new StringBuilder();
        value.append(zhengshu).append('.').append(xiaoshu);
        return value.toString();
    }
}