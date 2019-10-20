package com.subway.monitor.common.query;

import java.util.Map;

/**
 * 查询条件封装
 */
public interface Query<T extends Query<T>> extends Map<String, Object> {

    T fill(String key, Object value);
}
