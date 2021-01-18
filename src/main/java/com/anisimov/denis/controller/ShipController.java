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
    @ApiOperation(
            value = "Получить корабль с указанным id",
            httpMethod = "GET",
            produces = "application/json",
            response = Ship.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Неверный id"),
            @ApiResponse(code = 404, message = "Нет корабля с указанным id"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<Ship> getShipById(
            @ApiParam(
                    value = "id корабля",
                    required = true,
                    name = "id",
                    example = "1"
            )
            @PathVariable(value = "id") Long shipId
    ) {
        return shipService.readShipById(shipId);
    }

    // 200, 400, 404, 422, 500
    @PostMapping(consumes = "application/json")
    @ApiOperation(
            value = "Сохранить новый корабль",
            httpMethod = "POST",
            produces = "application/json",
            consumes = "application/json",
            response = String.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 422, message = "Нет места для сохранения в указанном порту"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<String> postShip(
            @ApiParam(
                    value = "Объект корабля",
                    required = true
            )
            @RequestBody Ship ship
    ) {
        return shipService.createShip(ship);
    }

    // 200, 404, 500
    @DeleteMapping("/{id}")
    @ApiOperation(
            value = "Удалить корабль с указанным id",
            httpMethod = "DELETE",
            produces = "application/json",
            response = String.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Нет корабля с указанным id"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<String> deleteShip(
            @ApiParam(
                    value = "id корабля",
                    required = true,
                    name = "id",
                    example = "1"
            )
            @PathVariable(value = "id") Long shipId
    ) {
        return shipService.deleteShip(shipId);
    }

    // 200, 404, 500
    @GetMapping("/{id}/status")
    @ApiOperation(
            value = "Получить корабли с указанным id",
            httpMethod = "GET",
            produces = "application/json",
            response = Ship.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Нет корабля с указанным id"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<ShipStatus> getShipStatus(
            @ApiParam(
                    value = "id корабля",
                    required = true,
                    name = "id",
                    example = "1"
            )
            @PathVariable(value = "id") Long shipId
    ) {
        return shipService.readShipStatus(shipId);
    }

    // 200, 400, 404, 422, 500
    @PutMapping("/{id}/status")
    @ApiOperation(
            value = "Изменить статус корабля с указанным id",
            httpMethod = "PUT",
            produces = "application/json",
            consumes = "application/json",
            response = ShipStatus.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Неверные параметры"),
            @ApiResponse(code = 404, message = "Неверный id порта"),
            @ApiResponse(code = 422, message = "В указанном порту нет места"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<ShipStatus> putShipStatus(
            @ApiParam(
                    value = "id корабля",
                    required = true,
                    name = "id",
                    example = "1"
            )
            @PathVariable(value = "id") Long shipId,
            @ApiParam(
                    value = "id порта",
                    name = "port_id",
                    example = "1"
            )
            @RequestParam(value = "port_id", required = false) Long portId,
            @ApiParam(
                    value = "новый статус корабля"
            )
            @RequestBody ShipStatus status
    ) {
        return shipService.updateShipStatus(shipId, portId, status);
    }
}
