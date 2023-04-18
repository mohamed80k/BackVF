package com.service;

import com.dto.TypeEventAddDto;
import com.dto.TypeEventInfoDto;
import com.dto.TypeEventUpdateDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TypeEventService {
    @Transactional
    TypeEventInfoDto add(TypeEventAddDto typeEventAddDto);

    @Transactional
    TypeEventInfoDto update(TypeEventUpdateDto typeEventUpdateDto);

    @Transactional
    boolean delete(Integer id);

    TypeEventInfoDto getOne(Integer id);

    List<TypeEventInfoDto> getList();
}
