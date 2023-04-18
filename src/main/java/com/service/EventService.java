package com.service;

import com.dto.EventAddDto;
import com.dto.EventInfoDto;
import com.dto.EventUpdateDto;
import com.dto.PageDto;
import com.dto.report.EventStatisticDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventService {

    @Transactional
    EventInfoDto add(EventAddDto eventAddDto);

    @Transactional
    EventInfoDto update(EventUpdateDto eventUpdateDto);

    @Transactional
    boolean delete(Integer id);

    @Transactional
    Boolean deleteMultiple(List<Integer> eventsId);

    EventInfoDto getOne(Integer id);

    PageDto<EventInfoDto> getPage(int page, int size, String search);

    List<EventInfoDto> getList();

    List<EventStatisticDto> getVisitorsStatistics();

    List<EventStatisticDto> getTypeEtabStatistics();
}
