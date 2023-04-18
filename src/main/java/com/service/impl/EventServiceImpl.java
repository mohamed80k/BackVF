package com.service.impl;

import com.dto.*;
import com.dto.report.EventStatisticDto;
import com.entity.*;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceConflictException;
import com.exception.ResourceNotFoundException;
import com.repository.*;
import com.service.EventService;
import com.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    TypeEventRepository typeEventRepository;

    @Autowired
    CommercialRepository commercialRepository;
    @Autowired
    EventTiersRepository eventTiersRepository;

    @Autowired
    private TiersRepository<Tiers> tierRepository;

    ModelMapper modelMapper = new ModelMapper();



    @Override
    @Transactional
    public EventInfoDto add(EventAddDto eventAddDto) {

        /** Vérification des dates **/
        Date date = new Date();

        if (eventAddDto.getCreateAt() == null) {
            eventAddDto.setCreateAt(date);
        } else if (eventAddDto.getCreateAt().compareTo(date) == 1) {
            throw new ResourceConflictException("Date création d'évenement incorrect !");
        }

        /** Vérification d'évenement **/
        if (eventRepository.existsByName(eventAddDto.getName()))
            throw new ResourceAlreadyExistException("Nom d'évenement existe déja !");

        /** Vérification de type d'évenement **/
        if (!typeEventRepository.existsById(eventAddDto.getTypeEvent())) {
            throw new ResourceNotFoundException("Type d'évenement non trouvé !");
        }

        TypeEvent typeEvent = typeEventRepository.getOne(eventAddDto.getTypeEvent());

        /** Vérification des commerciaux **/
        Set<Commercial> commercials = new HashSet<Commercial>();
        for (Integer commercialId : eventAddDto.getCommercials()) {
            if (commercialRepository.existsById(commercialId)) {
                commercials.add(commercialRepository.getOne(commercialId));
            } else {
                throw new ResourceNotFoundException("Commercial non trouvé !");
            }
        }
        /** Ajouter **/

        Locality locality = modelMapper.map(eventAddDto.getLocality(), Locality.class);
        locality.setId(null);
        Event event = modelMapper.map(eventAddDto, Event.class);
        event.setId(null);
        event.setLocality(locality);
        event.setCommercials(commercials);
        event.setTypeEvent(typeEvent);
        eventRepository.save(event);

        return modelMapper.map(event, EventInfoDto.class);
    }

    @Override
    @Transactional
    public EventInfoDto update(EventUpdateDto eventUpdate) {

        /** Vérification de projet **/
        Event eventOld = eventRepository.findById(eventUpdate.getId()).orElse(null);
        if (eventOld == null) {
            throw new ResourceNotFoundException("Evenement non trouvé !");
        }

        if (!eventOld.getName().equals(eventUpdate.getName())
                && eventRepository.existsByName(eventUpdate.getName()))
            throw new ResourceAlreadyExistException("Evenement existe déja !");


        /** Vérification des dates **/
        Date date = new Date();

        if (eventUpdate.getCreateAt() == null) {
            eventUpdate.setCreateAt(date);
        } else if (eventUpdate.getCreateAt().compareTo(date) == 1) {
            throw new ResourceConflictException("Date création d'évenement incorrect !");
        }

        /** Vérification de type du projet **/
        if (!typeEventRepository.existsById(eventUpdate.getTypeEvent())) {
            throw new ResourceNotFoundException("Type d'évenement non trouvé !");
        }
        TypeEvent typeEvent = typeEventRepository.getOne(eventUpdate.getTypeEvent());

        /** Vérification de type du projet **/
//        if (!eventRepository.existsById(eventUpdateDto.getTypeProject())) {
//            throw new ResourceNotFoundException("Type de projet non trouvé !");
//        }
//        TypeProject typeProject = typeProjectRepository.getOne(eventUpdateDto.getTypeProject());

        /** Vérification des commerciaux **/
        Set<Commercial> commercials = new HashSet<Commercial>();
        for (Integer commercialId : eventUpdate.getCommercials()) {
            if (commercialRepository.existsById(commercialId)) {
                commercials.add(commercialRepository.getOne(commercialId));
            } else {
                throw new ResourceNotFoundException("Commercial non trouvé !");
            }
        }


        /** Modifier **/
        Locality locality = modelMapper.map(eventUpdate.getLocality(), Locality.class);
        locality.setId(eventOld.getLocality().getId());

        Event event = modelMapper.map(eventUpdate, Event.class);
        event.setLocality(locality);
        event.setCommercials(commercials);
        event.setTypeEvent(typeEvent);

        /** Vérification des Tiers **/
        Set<Tiers> tiers = new HashSet<Tiers>();
        List<EventTiers> eventTiers = new ArrayList<>();
        for (Integer tierId : eventUpdate.getTiers()) {
            Tiers tier = tierRepository.findById(tierId).orElse(null);
            if (tier != null) {
                tiers.add(tier);
                eventTiers.add(new EventTiers(event, tier, eventUpdate.getEventsTiers().get(tier.getId()).getEtablissement(),
                        eventUpdate.getEventsTiers().get(tier.getId()).getTypeEtablissement(), eventUpdate.getEventsTiers().get(tier.getId()).getTypeTiers(),
                        eventUpdate.getEventsTiers().get(tier.getId()).getSample(), eventUpdate.getEventsTiers().get(tier.getId()).getVille(),
                        eventUpdate.getEventsTiers().get(tier.getId()).getFonction(),eventUpdate.getEventsTiers().get(tier.getId()).getObservation()));
            } else {
                throw new ResourceNotFoundException("Tiers non trouvé !");
            }
        }

        event.setEventTiers(eventTiers);
        eventRepository.save(event);
        eventTiersRepository.saveAll(eventTiers);
        EventInfoDto eventInfoDto = modelMapper.map(event, EventInfoDto.class);
        eventInfoDto.setTiers(Utils.map(modelMapper, tiers, TiersInfoDto.class));

        return eventInfoDto;
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        /** Vérification de projet **/
        Event  event = eventRepository.findById(id).orElse(null);

        if (event == null) {
            throw new ResourceNotFoundException("Evenement non trouvé !");
        }
        try {
            eventRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean deleteMultiple(List<Integer> eventsId) {
        try {
            for(Integer id : eventsId) {
                this.delete(id);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public EventInfoDto getOne(Integer id) {
        EventInfoDto eventInfoDto = null;
        /** Vérification d'évenement **/
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            throw new ResourceNotFoundException("Evenement non trouvé !");
        }
        /** Vérification des Tiers & Articles**/
        List<TiersInfoDto> tiers = new ArrayList<>();
        Map<Integer, Set<ArticleInfoDto>> articles = new HashMap<>();
        Map<Integer,EventTierDto> eventTierDtoMap = new HashMap<>();
        //Map<Integer, String> etablissements = new HashMap<>();
        List<EventTiers> eventTiers = eventTiersRepository.findByEventId(event.getId());
        for(EventTiers e : eventTiers){
            if(tierRepository.existsById(e.getTiers().getId())){
                tiers.add(modelMapper.map(tierRepository.getOne(e.getTiers().getId()),TiersInfoDto.class));
                EventTierDto eventTierDto = modelMapper.map(e, EventTierDto.class);
                eventTierDtoMap.put(e.getTiers().getId(), eventTierDto);
            }else {
                throw new ResourceNotFoundException("Tier non trouvé");
            }
        }

        eventInfoDto = modelMapper.map(event, EventInfoDto.class);
        eventInfoDto.setTiers(tiers);
        eventInfoDto.setEventsTiers(eventTierDtoMap);

        return eventInfoDto;
    }

    @Override
    public PageDto<EventInfoDto> getPage(int page, int size, String search) {

        Page<Event> events = eventRepository.findAllByOrderByCreateAtDesc(PageRequest.of(page, size), search);

        List<EventInfoDto> content = Utils.map(modelMapper, events.getContent(), EventInfoDto.class);
        return new PageDto<EventInfoDto>(content, events.getTotalPages(), events.getTotalElements(),
                events.getSize(), events.getNumber(), events.getNumberOfElements(), events.isFirst(),
                events.isLast());
    }
    @Override
    public List<EventInfoDto> getList() {
       // return Utils.map(modelMapper, eventRepository.findAll(), EventInfoDto.class);
        Date toDay = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return Utils.map(modelMapper, eventRepository.getListByEndDate(dateFormat.format(toDay)), EventInfoDto.class);
    }

    @Override
    public List<EventStatisticDto> getVisitorsStatistics(){
        return eventRepository.getVisitorsStatictic();
    }

    @Override
    public List<EventStatisticDto> getTypeEtabStatistics(){
        return eventRepository.getTypeEtabStatistics();
    }
}
