package com.service.impl;

import com.dto.EventTiersInfoDto;
import com.dto.PageDto;
import com.entity.Commercial;
import com.entity.EventTiers;
import com.entity.Tiers;
import com.exception.ResourceNotFoundException;
import com.repository.EventRepository;
import com.repository.EventTiersRepository;
import com.repository.TiersRepository;
import com.service.CommercialService;
import com.service.EventTiersService;
import com.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventTiersServiceImpl implements EventTiersService {
    @Autowired
    private EventTiersRepository eventTiersRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TiersRepository<Tiers> tiersRepository;

    @Autowired
    private CommercialService commercialService;

    ModelMapper modelMapper = new ModelMapper();



    @Override
    public PageDto<EventTiersInfoDto> getPageEvent(int page, int size, String search){
        Page<EventTiers> eventTiers = eventTiersRepository.getPage(new PageRequest(page, size), search);
                List<EventTiersInfoDto> content = Utils.map(modelMapper, eventTiers.getContent(), EventTiersInfoDto.class);
        return new PageDto<EventTiersInfoDto>(content, eventTiers.getTotalPages(), eventTiers.getTotalElements(), eventTiers.getSize(),
                eventTiers.getNumber(), eventTiers.getNumberOfElements(), eventTiers.isFirst(), eventTiers.isLast());

    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        /** Vérification de tiers **/
        Tiers tiers = tiersRepository.findById(id).orElse(null);

        if (tiers == null) {
            throw new ResourceNotFoundException("Visiteur non trouvé !");
        }

        this.checkTiersInAffiliateCommercials(tiers);

            try {
                /** Se détacher de l'evenement **/
                EventTiers eventTiers = eventTiersRepository.findByTiersId(id);
                if (eventTiers != null) {
                    eventTiersRepository.delete(eventTiers);
                }
                tiersRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }


    }

    @Override
    public boolean checkTiersInAffiliateCommercials(Tiers tiers) {
        List<Commercial> commercials = this.commercialService.getAffiliateCommercials();

        if (commercials == null) {
            return true;
        }

        for (Commercial commerial : commercials) {
            if (tiers.getCommercials().contains(commerial)) {
                return true;
            }
        }

        throw new ResourceNotFoundException(tiers.getTypeTiers().getName() + " non accessible !");
    }



}
