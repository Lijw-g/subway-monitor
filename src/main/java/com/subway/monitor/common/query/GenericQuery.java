package com.subway.monitor.common.query;


import com.google.common.base.Splitter;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class GenericQuery extends LinkedHashMap<String, Object> implements ListQuery<GenericQuery> {

    private static Splitter semi = Splitter.on(';').trimResults().omitEmptyStrings();
    private static Splitter colon = Splitter.on(':').trimResults().omitEmptyStrings();

    protected static final String _LIMIT = "_limit";
    protected static final String _OFFSET = "_offset";
    protected static final String _ORDER_BY = "_order_by";

    @Override
    public GenericQuery fill(String key, Object value) {
        put(key, value);
        return this;
    }

    @Override
    public GenericQuery like(String key) {

        Object value = get(key);
        if (value instanceof String) {
            if (!"".equals(value)) {
                put(key, "%" + value + "%");
            }
        }

        return this;
    }

    @Override
    public Integer getLimit() {
        return (Integer) get(_LIMIT);
    }

    @Override
    public Integer getOffset() {
        return (Integer) get(_OFFSET);
    }


    public GenericQuery setLimit(Integer limit) {
        put(_LIMIT, limit);
        return this;
    }

    public GenericQuery setOffset(Integer offset) {
        put(_OFFSET, offset);
        return this;
    }

    public List<OrderBy> getOrderBy() {

        List<OrderBy> orders = (List<OrderBy>) get(_ORDER_BY);

        if (orders == null) {
            put(_ORDER_BY, orders = new LinkedList<OrderBy>());
        }

        return orders;
    }

    public GenericQuery orderBy(String orderBy) {
        List<OrderBy> orders = getOrderBy();

        for (String order : semi.splitToList(orderBy)) {
            Iterator<String> i = colon.split(order).iterator();

            String name = i.next();
            OrderBy.Direction direction = i.hasNext() ? OrderBy.Direction.valueOf(i.next().toUpperCase()) : OrderBy.Direction.ASC;

            orders.add(new OrderBy(name, direction));
        }

        return this;
    }
}
