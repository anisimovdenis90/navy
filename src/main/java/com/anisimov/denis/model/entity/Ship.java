package com.anisimov.denis.model.entity;

import com.anisimov.denis.model.enums.ShipStatusType;

public class Ship {
    private long id;
    private String name;
    private Long portId;
    private ShipStatusType status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public ShipStatusType getStatus() {
        return status;
    }

    public void setStatus(ShipStatusType status) {
        this.status = status;
    }
}
