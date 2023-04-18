package com.controller;

import com.dto.*;
import com.dto.report.EventStatisticDto;
import com.dto.report.ProjectReportDto;
import com.entity.Event;
import com.entity.EventTiers;
import com.service.EventService;
import com.service.EventTiersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/events")
@CrossOrigin()
public class EventController {
    @Autowired
    EventService eventService;



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EventInfoDto getOne(@PathVariable("id") Integer id) {
        return eventService.getOne(id);
    }

    @RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
    public List<EventInfoDto> getList() {
        return eventService.getList();
    }

    @RequestMapping(value = "/paginate", method = RequestMethod.GET)
    public PageDto<EventInfoDto> getPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") int size, @RequestParam String search) {
        return eventService.getPage(page, size, search);
    }

    @RequestMapping(method = RequestMethod.POST)
    public EventInfoDto add(@Valid @RequestBody EventAddDto eventAddDto) {
        return eventService.add(eventAddDto);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public EventInfoDto update(@Valid @RequestBody EventUpdateDto eventUpdateDto) {
        return eventService.update(eventUpdateDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") Integer id) {
        return eventService.delete(id);
    }

    @RequestMapping(value = "/multiple/{eventsId}", method = RequestMethod.DELETE)
    public Boolean deleteMultiple(@PathVariable("eventsId") List<Integer> eventsId) {
        return eventService.deleteMultiple(eventsId);
    }

    @RequestMapping(value = "/visitors/statistics", method = RequestMethod.GET)
    public List<EventStatisticDto> getStatisticsBySite() {
        return eventService.getVisitorsStatistics();
    }

    @RequestMapping(value = "/typeEtab/statistics", method = RequestMethod.GET)
    public List<EventStatisticDto> getTypeEtabStatistics() {
        return eventService.getTypeEtabStatistics();
    }


}
