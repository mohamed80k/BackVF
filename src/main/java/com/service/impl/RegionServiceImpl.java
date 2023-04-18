package com.service.impl;

import com.dto.RegionAddDto;
import com.dto.RegionInfoDto;
import com.dto.RegionUpdateDto;
import com.entity.Region;
import com.exception.ResourceAlreadyExistException;
import com.repository.RegionRepository;
import com.service.RegionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionRepository regionRepository;

    ModelMapper modelMapper = new ModelMapper();
    @Override
    public Region getOne(Integer id) {
        return regionRepository.getOne(id);
    }

    @Override
    public RegionInfoDto AddRegion(RegionAddDto regionDto) {

        if(regionRepository.existsByRegionName(regionDto.getRegionName())){
            throw new ResourceAlreadyExistException("la region est deja existe ");
        }

        Region region = modelMapper.map(regionDto, Region.class);
        regionRepository.save(region);
        return modelMapper.map(region, RegionInfoDto.class);


    }

    @Override
    public RegionInfoDto UpdateRegion(RegionUpdateDto regionDto) {
        if(this.getOne(regionDto.getId())!=null){
            Region region = modelMapper.map(regionDto, Region.class);
            regionRepository.save(region);
            return modelMapper.map(region, RegionInfoDto.class);
        }
        return null;
    }

    @Override
    public Boolean DeleteRegion(Integer id) {
        if(getOne(id)!=null){
            this.regionRepository.deleteById(id);
            return true;
        }
        return false;

    }

    @Override
    public List<Region> GetAllRegion() {
        return this.regionRepository.findAll();
    }
}

