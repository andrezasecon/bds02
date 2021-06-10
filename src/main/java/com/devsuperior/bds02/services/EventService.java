package com.devsuperior.bds02.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;
	
	@Autowired
	private CityRepository cityRepository;
	
	
	@Transactional(readOnly = true)
	public List<EventDTO> findAll(){
		List<Event> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new EventDTO(x)).collect(Collectors.toList());
	}
	
	
	@Transactional(readOnly = true)
    public EventDTO findEventById(Long id) {
        Optional<Event> obj = repository.findById(id);
        Event entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new EventDTO(entity);
    }
	
	
	@Transactional
    public EventDTO updateEvent(Long id, EventDTO dto) {
        try {
            Event entity = repository.getOne(id);
            entity.setName(dto.getName());
            entity.setDate(dto.getDate());
            entity.setUrl(dto.getUrl());
            City city = cityRepository.getOne(dto.getCityId());
            entity.setCity(city);
            entity = repository.save(entity);
            return new EventDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
    }
	
}
