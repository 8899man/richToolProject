package com.otms.support.kernel.parser;

import com.otms.support.kernel.dto.BillingEventDto;
import com.otms.support.kernel.dto.InboundDto;
import com.otms.support.kernel.entity.BillingEvent;
import com.otms.support.kernel.entity.Inbound;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-9-12.
 */
public class InboundEventParser {

    public static List<InboundDto> toDtoList(List<Inbound> inbounds) {
        List<InboundDto> dtos = new ArrayList<>();
        for (Inbound inbound : inbounds) {
            dtos.add(toDto(inbound, new InboundDto()));
        }
        return dtos;
    }

    private static InboundDto toDto(Inbound inbound, InboundDto dto) {
        dto.setCreatedOn(inbound.getCreatedOn());
        dto.setPayload(inbound.getPayload());
        return dto;
    }
}
