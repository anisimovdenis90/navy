package com.anisimov.denis.dao.impl;

import com.anisimov.denis.dao.BaseDao;
import com.anisimov.denis.dao.ShipDao;
import com.anisimov.denis.model.entity.Ship;
import com.anisimov.denis.model.enums.ShipStatusType;
import com.anisimov.denis.model.mapper.ShipMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ShipDaoImpl extends BaseDao implements ShipDao {

    @Override
    public List<Ship> selectAllShips() {
        final String sql = "SELECT id, name, port_id, status FROM ships";
        return jdbcTemplate.query(sql, new ShipMapper());
    }

    @Override
    public List<Ship> selectShipsByStatus(ShipStatusType status) {
        if (status == null) {
            return Collections.emptyList();
        }
        final String sql = "SELECT id, name, port_id, status FROM ships WHERE status='" + status.name() + "'";
        return jdbcTemplate.query(sql, new ShipMapper());
    }

    @Override
    public int selectShipsCountByPortId(Long id) {
        final String sql = String.format("SELECT COUNT(id) FROM ships WHERE port_id='%s' AND status='%s'", id, ShipStatusType.PORT.name());
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public Ship selectShipById(Long id) {
        final String sql = "SELECT id, name, port_id, status FROM ships WHERE id=" + id;
        try {
            return jdbcTemplate.queryForObject(sql, new ShipMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void insertShip(Ship ship) {
        if (ship == null || ship.getName() == null || ship.getPortId() == null) {
            return;
        }
        final String sql = String.format("INSERT INTO ships (name, port_id, status) VALUES ('%s', '%s', '%s')",
                ship.getName(), ship.getPortId(), ShipStatusType.PORT.name());
        jdbcTemplate.update(sql);
    }

    @Override
    public void deleteShipById(Long id) {
        final String sql = "DELETE FROM ships WHERE id=" + id;
        jdbcTemplate.update(sql);
    }

    @Override
    public void updateShipStatusById(Long id, ShipStatusType status) {
        if (status == null) {
            return;
        }
        final String sql = String.format("UPDATE ships SET status='%s' WHERE id=%s", status.name(), id);
        jdbcTemplate.update(sql);
    }

    @Override
    public void updateShipPortIdById(Long id, Long portId) {
        final String sql = String.format("UPDATE ships SET port_id=%s WHERE id=%s", portId, id);
        jdbcTemplate.update(sql);
    }
}
