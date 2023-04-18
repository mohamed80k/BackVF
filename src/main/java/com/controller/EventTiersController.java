package com.controller;

import com.dto.EventInfoDto;
import com.dto.EventTiersInfoDto;
import com.dto.PageDto;
import com.service.EventTiersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/eventTiers")
@CrossOrigin()
public class EventTiersController {

    @Autowired
    EventTiersService eventTiersService;



    @RequestMapping(value = "/paginate", method = RequestMethod.GET)
    public PageDto<EventTiersInfoDto> getPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                         @RequestParam(value = "size", required = false, defaultValue = "10") int size, @RequestParam String search) {
        return eventTiersService.getPageEvent(page, size, search);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") Integer id) {
        return eventTiersService.delete(id);
    }
}
