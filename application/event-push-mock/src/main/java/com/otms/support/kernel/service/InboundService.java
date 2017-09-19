package com.otms.support.kernel.service;

import com.otms.support.kernel.dto.OrderEventDto;
import com.otms.support.kernel.entity.Inbound;
import com.otms.support.kernel.entity.OrderEvent;
import com.otms.support.kernel.repository.InBoundRepo;
import com.otms.support.kernel.repository.OrderEventRepo;
import com.otms.support.spring.annotation.InboundLog;
import com.otms.support.supplier.database.enums.APISource;
import com.otms.support.supplier.database.enums.Direction;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bert on 17-9-11.
 */
@Service
public class InboundService {

    @Autowired
    private InBoundRepo inBoundRepo;

    @Transactional
    public void save(String remoteIp, String payload, InboundLog inboundLog, String url) {
        Inbound inbound = new Inbound();
        inbound.setRemoteIp(remoteIp);
        inbound.setPayload(payload);
        inbound.setCreatedOn(LocalDateTime.now());
        inbound.setDirection(Direction.IN);
        inbound.setApiSource(inboundLog.value());
        inbound.setMethod(inboundLog.method());
        inbound.setUrl(url);
        inBoundRepo.save(inbound);
    }
}
