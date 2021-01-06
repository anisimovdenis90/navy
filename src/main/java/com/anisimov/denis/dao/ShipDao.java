package com.anisimov.denis.dao;

import com.anisimov.denis.model.entity.Ship;
import com.anisimov.denis.model.enums.ShipStatusType;

import java.util.List;

public interface ShipDao {

    List<Ship> selectAllShips();
    List<Ship> selectShipsByStatus(ShipStatusType status);

    int selectShipsCountByPortId(Long id);

    Ship selectShipById(Long id);
    void insertShip(Ship ship);
    void deleteShipById(Long id);

    void updateShipStatusById(Long id, ShipStatusType status);
    void updateShipPortIdById(Long id, Long portId);
}
