package com.subway.monitor.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.Date;

/**
 * @author: huangyibo
 * @Date: 2019/1/30 23:33
 * @Description:图书实体类
 */
@Data
public class Book {
    @Id
    private String id;
    //价格
    private Integer price;
    //书名
    private String name;
    //简介
    private String info;
    //出版社
    private String publish;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
}