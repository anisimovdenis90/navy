package com.anisimov.denis.service;

import com.anisimov.denis.model.entity.Port;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PortService {

    ResponseEntity<List<Port>> readAllPorts();

    ResponseEntity<String> readPortCapacityById(Long portId);
}
