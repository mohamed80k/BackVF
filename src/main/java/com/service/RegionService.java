package com.service;

import com.dto.RegionAddDto;
import com.dto.RegionInfoDto;
import com.dto.RegionUpdateDto;
import com.entity.Region;

import java.util.List;

public interface RegionService {
    public Region getOne(Integer id);

    public RegionInfoDto AddRegion(RegionAddDto region);

    public RegionInfoDto UpdateRegion(RegionUpdateDto region);

    public Boolean DeleteRegion(Integer id);

    public List<Region> GetAllRegion();
}
