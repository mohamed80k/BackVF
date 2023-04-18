package com.service.impl;

import com.dto.TypeEventAddDto;
import com.dto.TypeEventInfoDto;
import com.dto.TypeEventUpdateDto;
import com.entity.TypeEvent;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.EventRepository;
import com.repository.TypeEventRepository;
import com.service.TypeEventService;
import com.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeEventServiceImpl implements TypeEventService {

    @Autowired
    TypeEventRepository typeEventRepository;

    @Autowired
    EventRepository eventRepository;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    @Transactional
    public TypeEventInfoDto add(TypeEventAddDto typeEventAddDto) {
        /**
         * Vérification de type d'evenement
         */
        if (typeEventRepository.existsByName(typeEventAddDto.getName()))
            throw new ResourceAlreadyExistException("Type de projet existe déja !");

        /**
         * Ajouter
         */
        TypeEvent typeEvent = modelMapper.map(typeEventAddDto, TypeEvent.class);
        typeEventRepository.save(typeEvent);

        /**
         * Retourne data
         */
        return modelMapper.map(typeEvent, TypeEventInfoDto.class);
    }

    @Override
    @Transactional
    public TypeEventInfoDto update(TypeEventUpdateDto typeEventUpdateDto) {
        /**
         * Vérification de type de projet
         */
        if (!typeEventRepository.existsById(typeEventUpdateDto.getId())) {
            throw new ResourceNotFoundException("Type de projet non trouvé !");
        }

        TypeEvent typeEventOld = typeEventRepository.getOne(typeEventUpdateDto.getId());

        if (!typeEventOld.getName().equals(typeEventUpdateDto.getName())
                && typeEventRepository.existsByName(typeEventUpdateDto.getName()))
            throw new ResourceAlreadyExistException("Type de projet existe déja !");

        /**
         * Modifier
         */
        TypeEvent typeEvent = modelMapper.map(typeEventUpdateDto, TypeEvent.class);
        typeEventRepository.save(typeEvent);

        /**
         * Retourne data
         */
        return modelMapper.map(typeEvent, TypeEventInfoDto.class);
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        /**
         * Vérification de type d'evenement
         */
        if (!typeEventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Type d'evenement non trouvé !");
        }

        TypeEvent typeEvent = typeEventRepository.getOne(id);

        if (eventRepository.existsByTypeEvent(typeEvent))
            throw new ResourceAlreadyExistException("Type d'evenement déja utilisé !");

        try {
            /**
             * Supprimer
             */
            typeEventRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TypeEventInfoDto getOne(Integer id) {
        /**
         * Vérification de type du projet
         */
        if (!typeEventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Type d'evenement non trouvé !");
        }

        TypeEvent typeEvent = typeEventRepository.getOne(id);

        return modelMapper.map(typeEvent, TypeEventInfoDto.class);
    }

    @Override
    public List<TypeEventInfoDto> getList() {
        return Utils.map(modelMapper, typeEventRepository.findAll(), TypeEventInfoDto.class);
    }
}
