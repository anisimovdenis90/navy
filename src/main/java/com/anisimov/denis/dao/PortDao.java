package com.anisimov.denis.dao;

import com.anisimov.denis.model.entity.Port;

import java.util.List;

public interface PortDao {

    List<Port> selectAllPorts();

    Port selectPortBuId(Long id);
}
