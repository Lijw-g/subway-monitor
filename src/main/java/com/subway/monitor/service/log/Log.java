package com.subway.monitor.service.log;

import java.util.Date;

/**
 * 操作日志
 */
public class Log {

    // 日志ID
    private int id;

    // 对象
    private int oid;

    // 对象类型
    private LogType type;

    // 日志内容
    private String content;

    // 创建人
    private String createUser;

    // 创建日期
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static Builder newBuilder(LogType type, int oid) {
        return new Builder(type, oid);
    }

    public static class Builder {
        private LogType type;
        private int oid;
        private String content;
        private String createUser;


        private Builder(LogType type, int oid) {
            this.type = type;
            this.oid = oid;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCreateUser(String createUser) {
            this.createUser = createUser;
            return this;
        }

        public Log build() {
            Log log = new Log();
            log.setType(type);
            log.setOid(oid);
            log.setContent(content);
            log.setCreateUser(createUser);
            return log;
        }
    }
}
