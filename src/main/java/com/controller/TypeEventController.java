package com.controller;

import com.dto.*;
import com.service.TypeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/type/events")
@CrossOrigin
public class TypeEventController {

    @Autowired
    private TypeEventService typeEventService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TypeEventInfoDto getOne(@PathVariable("id") Integer id) {
        return typeEventService.getOne(id);
    }

    @RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
    public List<TypeEventInfoDto> getList() {
        return typeEventService.getList();
    }

    @RequestMapping(method = RequestMethod.POST)
    public TypeEventInfoDto add(@Valid @RequestBody TypeEventAddDto typeEventAddDto) {
        return typeEventService.add(typeEventAddDto);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public TypeEventInfoDto update(@Valid @RequestBody TypeEventUpdateDto typeEventUpdateDto) {
        return typeEventService.update(typeEventUpdateDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") Integer id) {
        return typeEventService.delete(id);
    }
}
