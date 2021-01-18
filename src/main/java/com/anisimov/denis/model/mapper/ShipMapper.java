package com.anisimov.denis.model.mapper;

import com.anisimov.denis.model.entity.Ship;
import com.anisimov.denis.model.enums.ShipStatusType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShipMapper implements RowMapper<Ship> {

    @Override
    public Ship mapRow(ResultSet rs, int i) throws SQLException {
        final Ship ship = new Ship();
        ship.setId(rs.getLong("id"));
        ship.setName(rs.getString("name"));
        ship.setStatus(ShipStatusType.getStatusTyoe(rs.getString("status")));
        final Long portId = rs.getLong("port_id");
        if (!rs.wasNull()) {
            ship.setPortId(portId);
        }
        return ship;
    }
}
