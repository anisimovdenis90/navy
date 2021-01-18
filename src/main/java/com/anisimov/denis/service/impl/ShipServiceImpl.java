package com.anisimov.denis.service.impl;

import com.anisimov.denis.dao.PortDao;
import com.anisimov.denis.dao.ShipDao;
import com.anisimov.denis.model.entity.Port;
import com.anisimov.denis.model.entity.Ship;
import com.anisimov.denis.model.entity.ShipStatus;
import com.anisimov.denis.model.enums.ShipStatusType;
import com.anisimov.denis.service.ShipService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {

    private final ShipDao shipDao;
    private final PortDao portDao;

    @Autowired
    public ShipServiceImpl(ShipDao shipDao, PortDao portDao) {
        this.shipDao = shipDao;
        this.portDao = portDao;
    }

    @Override
    public ResponseEntity<List<Ship>> readAllShips(String status) {
        if (status == null) {
            return ResponseEntity.ok(shipDao.selectAllShips());
        }
        final ShipStatusType shipStatusType = ShipStatusType.getStatusTyoe(status);
        if (shipStatusType == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(shipDao.selectShipsByStatus(shipStatusType));
    }

    @Override
    public ResponseEntity<String> createShip(Ship ship) {
        if (ship == null || ship.getName() == null || ship.getPortId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        final Port port = portDao.selectPortBuId(ship.getPortId());
        if (port == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        final int shipInPortCount = shipDao.selectShipsCountByPortId(ship.getPortId());
        if (shipInPortCount < port.getCapacity()) {
            shipDao.insertShip(ship);
            final Optional<Ship> lastInsertShip = shipDao.selectAllShips().stream().max(Comparator.comparingLong(Ship::getId));
            if (lastInsertShip.isPresent()) {
                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", lastInsertShip.get().getId());
                return ResponseEntity.ok(jsonObject.toString());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Override
    public ResponseEntity<String> deleteShip(Long id) {
        final Ship ship = shipDao.selectShipById(id);
        if (ship == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        shipDao.deleteShipById(id);
        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<ShipStatus> readShipStatus(Long id) {
        final Ship ship = shipDao.selectShipById(id);
        if (ship == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        final ShipStatus shipStatus = new ShipStatus();
        shipStatus.setStatus(ship.getStatus().name());
        return ResponseEntity.ok(shipStatus);
    }

    @Override
    public ResponseEntity<ShipStatus> updateShipStatus(Long shipId, Long portId, ShipStatus status) {
        final Ship ship = shipDao.selectShipById(shipId);
        if (ship == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        final ShipStatusType shipStatusType = ShipStatusType.getStatusTyoe(status.getStatus());
        if (shipStatusType == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        switch (shipStatusType) {
            case SEA:
                if (ship.getStatus() != ShipStatusType.SEA) {
                    ship.setStatus(ShipStatusType.SEA);
                    shipDao.updateShipStatusById(shipId, shipStatusType);
                    shipDao.updateShipPortIdById(shipId, null);
                }
                break;
            case PORT:
                if (ship.getStatus() != ShipStatusType.PORT) {
                    if (portId == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }
                    final Port port = portDao.selectPortBuId(portId);
                    if (port == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    }
                    final int shipsInPortCount = shipDao.selectShipsCountByPortId(portId);
                    if (shipsInPortCount < port.getCapacity()) {
                        ship.setStatus(ShipStatusType.PORT);
                        shipDao.updateShipStatusById(shipId, shipStatusType);
                        shipDao.updateShipPortIdById(shipId, portId);
                    } else {
                        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
                    }
                }
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        final ShipStatus newShipStatus = new ShipStatus();
        newShipStatus.setStatus(ship.getStatus().name());
        return ResponseEntity.ok(newShipStatus);
    }

    @Override
    public ResponseEntity<Ship> readShipById(Long shipId) {
        if (shipId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        final Ship ship = shipDao.selectShipById(shipId);
        if (ship == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(ship);
    }
}
