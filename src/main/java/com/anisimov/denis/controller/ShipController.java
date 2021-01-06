package com.anisimov.denis.controller;

import com.anisimov.denis.model.entity.Ship;
import com.anisimov.denis.model.entity.ShipStatus;
import com.anisimov.denis.service.ShipService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ships", produces = "application/json")
@Api(value = "/ports", tags = {"Работа с кораблями"})
public class ShipController {

    private final ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    // 200, 400, 500
    @GetMapping
    @ApiOperation(
            value = "Получить текущие корабли (с возможностью фильтрации)",
            httpMethod = "GET",
            produces = "application/json",
            response = Ship.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Неверный статус корабля"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<List<Ship>> getAllShips(
            @ApiParam(
                    value = "Статус (местоположение) корабля",
                    name = "status",
                    allowableValues = "SEA, PORT",
                    example = "SEA"
            )
            @RequestParam(value = "status", required = false) String status
    ) {
        return shipService.readAllShips(status);
    }
    // 200, 404, 500
    @GetMapping("/{id}")
    public ResponseEntity<Ship> getShipById (@PathVariable(value = "id") Long shipId) {
        return shipService.readShipById(shipId);
    }

    // 200, 400, 404, 422, 500
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> postShip(@RequestBody Ship ship) {
        return shipService.createShip(ship);
    }

    // 200, 404, 500
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShip(@PathVariable(value = "id") Long shipId) {
        return shipService.deleteShip(shipId);
    }

    // 200, 404, 500
    @GetMapping("/{id}/status")
    public ResponseEntity<ShipStatus> getShipStatus (@PathVariable(value = "id") Long shipId) {
        return shipService.readShipStatus(shipId);
    }

    // 200, 400, 404, 422, 500
    @PutMapping("/{id}/status")
    public ResponseEntity<ShipStatus> putShipStatus(
            @PathVariable(value = "id") Long shipId,
            @RequestParam(value = "port_id", required = false) Long portId,
            @RequestBody ShipStatus status
    ) {
        return shipService.updateShipStatus(shipId, portId, status);
    }
}
