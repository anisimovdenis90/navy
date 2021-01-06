package com.anisimov.denis.service;

import com.anisimov.denis.model.entity.Ship;
import com.anisimov.denis.model.entity.ShipStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShipService {

    ResponseEntity<List<Ship>> readAllShips(String status);
    ResponseEntity<String> createShip(Ship ship);
    ResponseEntity<String> deleteShip(Long id);
    ResponseEntity<ShipStatus> readShipStatus(Long id);
    ResponseEntity<ShipStatus> updateShipStatus(Long shipId, Long portId, ShipStatus status);

    ResponseEntity<Ship> readShipById(Long shipId);
}
