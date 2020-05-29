package com.wang.dubbox.api.pojo;

import java.io.Serializable;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/05/09 22:15
 * @version: V1.0
 */
public class BonusPoint implements Serializable {

    private Integer id;

    private Integer points;

    public BonusPoint() {
    }

    public BonusPoint(Integer id, Integer points) {
        this.id = id;
        this.points = points;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "BonusPoint{" +
                "id=" + id +
                ", points=" + points +
                '}';
    }
}
