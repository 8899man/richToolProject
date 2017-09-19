package com.otms.support.controller.view;

import com.otms.support.kernel.dto.BillingEventDto;
import com.otms.support.kernel.dto.InboundDto;
import com.otms.support.kernel.service.OtmsEventService;
import com.otms.support.spring.annotation.PageQueryAon;
import com.otms.support.spring.model.PageQuery;
import com.otms.support.supplier.model.PageResult;
import com.otms.support.supplier.model.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/view/inbound")
public class InboundController {

    @Autowired
    private OtmsEventService eventService;

    @PostMapping("/events")
    public ResponseInfo list(@PageQueryAon PageQuery pageQuery, @RequestBody InboundDto query) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = eventService.inBoundList(pageQuery, query);
        response.wrapSuccess(result);
        return response;
    }
}
