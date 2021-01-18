package com.anisimov.denis.dao.impl;

import com.anisimov.denis.dao.BaseDao;
import com.anisimov.denis.dao.PortDao;
import com.anisimov.denis.model.entity.Port;
import com.anisimov.denis.model.mapper.PortMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PortDaoImpl extends BaseDao implements PortDao {

    private Map<Long, Port> portIdentityMap;

    @PostConstruct
    public void init() {
        portIdentityMap = new HashMap<>();
    }

    @Override
    public List<Port> selectAllPorts() {
        final String sql = "SELECT id, name, capacity FROM ports";
        return jdbcTemplate.query(sql, new PortMapper());
    }

    @Override
    public Port selectPortBuId(Long id) {
        Port port = portIdentityMap.get(id);
        if (port == null) {
            final String sql = "SELECT id, name, capacity FROM ports WHERE id=" + id;
            try {
                port = jdbcTemplate.queryForObject(sql, new PortMapper());
                portIdentityMap.put(id, port);
                return port;
            } catch (DataAccessException e) {
                return null;
            }
        } else {
            return port;
        }
    }
}
