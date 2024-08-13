package com.swiggy.app.demo.Mapper;

import com.swiggy.app.demo.Entity.Tracking;
import com.swiggy.app.demo.Dto.TrackingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrackingMapper {
    TrackingMapper INSTANCE = Mappers.getMapper(TrackingMapper.class);

    Tracking trackingToTrackingDTO(Tracking tracking);

    Tracking trackingDTOToTracking(Tracking trackingDTO);
}
