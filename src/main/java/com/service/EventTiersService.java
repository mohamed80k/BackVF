package com.service;

import com.dto.EventTiersInfoDto;
import com.dto.PageDto;
import com.dto.TiersOfEvent;
import com.entity.Event;
import com.entity.EventTiers;
import com.entity.Tiers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventTiersService {

    //    @Override
    //    public PageDto<TiersInfoDto> getPageTiersEvent(int page, int size, String search) {
    //        Page<Tiers> tiers = tiersRepository.findEventsTiers(new PageRequest(page, size), search);
    //        for(Tiers tier: tiers){
    //            Event event
    //        }
    //
    //        List<TiersInfoDto> content = Utils.map(modelMapper, tiers.getContent(), TiersInfoDto.class);
    //        return new PageDto<TiersInfoDto>(content, tiers.getTotalPages(), tiers.getTotalElements(), tiers.getSize(),
    //                tiers.getNumber(), tiers.getNumberOfElements(), tiers.isFirst(), tiers.isLast());
    //        return null;
    //   }


    public PageDto<EventTiersInfoDto> getPageEvent(int page, int size, String search);

    @Transactional
    boolean delete(Integer id);

    boolean checkTiersInAffiliateCommercials(Tiers tiers);
}
