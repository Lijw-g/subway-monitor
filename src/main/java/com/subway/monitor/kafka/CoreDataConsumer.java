package com.subway.monitor.kafka;

import com.subway.monitor.model.MonitorData;
import com.subway.monitor.service.MonitorDataService;
import com.subway.monitor.utils.DataUtil;
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
public class CoreDataConsumer {

    private Logger logger = LoggerFactory.getLogger(CoreDataConsumer.class);
    @Autowired
    private MonitorDataService monitorDataService;

    /**
     * 监听kafka.tut 的topic，不做其他业务
     *
     * @param record
     * @param topic  topic
     */
    @KafkaListener(id = "data", groupId = "dataBase", topics = "railway_core_data_topic")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String data = String.valueOf(kafkaMessage.get()).replace("\"", "");
            String agreement = data.substring(0, 4);
            Integer len = DataUtil.getDataSize(data.substring(4, 8));
            String deviceId = data.substring(8, 22);
            String mingling = data.substring(22, 26);
            String datas = data.substring(26, 84);
            //处理传输的数据
            Integer type = Integer.parseInt(datas.substring(0, 2));
            String JZ_data = datas.substring(2, 18);
            String M_Vstate = getStatusValue(datas.substring(18, 24));
            String M_Astate = getStatusValue(datas.substring(24, 30));
            String M_Tstate = getStatusValue(datas.substring(30, 36));
            String D_Vstate = getStatusValue(datas.substring(36, 42));
            String D_Astate = getStatusValue(datas.substring(42, 48));
            String D_Tstate = getStatusValue(datas.substring(48, 54));
            String degree = getDegreeValue(datas.substring(54, 58));
            MonitorData monitorData = new MonitorData().setAgreement(agreement)
                    .setLength(len)
                    .setDeviceId(deviceId)
                    .setCommand(mingling)
                    .setMVstate(M_Vstate)
                    .setMAstate(M_Astate)
                    .setMTstate(M_Tstate)
                    .setDVstate(D_Vstate)
                    .setDAstate(D_Astate)
                    .setDTstate(D_Tstate)
                    .setDegree(degree);
            logger.info("---门控器电压：" + M_Vstate + "V");
            logger.info("---电流：" + M_Astate + "A");
            logger.info("---温度：" + M_Tstate + "℃");
            logger.info("---门电机电压：" + D_Vstate + "V");
            logger.info("---电流：" + D_Astate + "A");
            logger.info("---温度：" + D_Tstate + "℃");
            logger.info("---门开度：" + degree + "m");
            int result = monitorDataService.createData(monitorData);
            logger.info("add result is " + (result == 1));
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