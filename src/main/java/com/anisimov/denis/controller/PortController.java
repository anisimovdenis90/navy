package com.anisimov.denis.controller;

import com.anisimov.denis.model.entity.Port;
import com.anisimov.denis.service.PortService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ports", produces = "application/json")
@Api(value = "/ports", tags = {"Работа с портами"})
public class PortController {

    private final PortService portService;

    @Autowired
    public PortController(PortService portService) {
        this.portService = portService;
    }

    // 200, 500
    @GetMapping
    @ApiOperation(
            value = "Получить сведения о портах",
            httpMethod = "GET",
            produces = "application/json",
            response = Port.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Порт не найден")
    })
    public ResponseEntity<List<Port>> getAllPorts() {
        return portService.readAllPorts();
    }

    // 200, 404, 500
    @GetMapping("/{id}/capacity")
    @ApiOperation(
            value = "Получить текущую загрузку порта",
            httpMethod = "GET",
            produces = "application/json",
            response = String.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Порт не найден"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<String> getPortCapacityById(
            @ApiParam(
                    value = "id порта",
                    name = "id",
                    required = true,
                    example = "3"
            )
            @PathVariable(value = "id") Long portId
    ) {
        return portService.readPortCapacityById(portId);
    }
}
