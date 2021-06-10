package com.devsuperior.bds02.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.services.EventService;

@RestController
@RequestMapping(value = "/events")
class EventController {

	@Autowired
	private EventService service;
	
	@GetMapping
    public ResponseEntity<List<EventDTO>> findAllEvents() {
        List<EventDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);

    }
	
	@GetMapping(value = "/{id}")
    public ResponseEntity<EventDTO> findEventById(@PathVariable Long id) {
        EventDTO dto = service.findEventById(id);
        return ResponseEntity.ok().body(dto);

    }
	
	@PutMapping(value = "/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO dto) {
        dto = service.updateEvent(id, dto);
        return ResponseEntity.ok().body(dto);
    }
	

}
