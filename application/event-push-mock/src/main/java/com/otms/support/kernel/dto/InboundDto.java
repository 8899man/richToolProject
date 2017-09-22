package com.otms.support.kernel.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otms.support.supplier.database.enums.APISource;
import com.otms.support.supplier.database.enums.Direction;
import com.otms.support.supplier.serializer.EnumDeserializer;
import com.otms.support.supplier.serializer.EnumSerializer;
import com.otms.support.supplier.serializer.LocalDateTimeDeserializer;
import com.otms.support.supplier.serializer.LocalDateTimeSerializer;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 17-9-12.
 */
public class InboundDto {

    private Long id;

    private String payload;

    private String remoteIp;

    private String url;

    private String method;

    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private APISource apiSource;

    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private Direction direction;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdOn;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdOnBegin;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdOnEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public APISource getApiSource() {
        return apiSource;
    }

    public void setApiSource(APISource apiSource) {
        this.apiSource = apiSource;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getCreatedOnBegin() {
        return createdOnBegin;
    }

    public void setCreatedOnBegin(LocalDateTime createdOnBegin) {
        this.createdOnBegin = createdOnBegin;
    }

    public LocalDateTime getCreatedOnEnd() {
        return createdOnEnd;
    }

    public void setCreatedOnEnd(LocalDateTime createdOnEnd) {
        this.createdOnEnd = createdOnEnd;
    }
}
