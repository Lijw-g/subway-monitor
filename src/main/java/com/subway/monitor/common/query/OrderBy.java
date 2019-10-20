package com.subway.monitor.common.query;

import java.io.Serializable;

public class OrderBy implements Serializable {

    private final String name;
    private final Direction direction;

    public OrderBy(String name, Direction direction) {
        this.name = name;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public Direction getDirection() {
        return direction;
    }

    public enum Direction {
        ASC,
        DESC
    }
}
