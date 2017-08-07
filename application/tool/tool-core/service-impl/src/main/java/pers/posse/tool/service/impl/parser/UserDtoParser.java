package pers.posse.tool.service.impl.parser;

import pers.posse.tool.service.dto.ToolUserDto;
import pers.posse.tool.service.impl.domain.ToolUser;

/**
 * Created by posse on 17-7-31.
 */
public final class UserDtoParser {
    public UserDtoParser() {
    }

    public static ToolUserDto fromDomain(ToolUser toolUser){
        if (toolUser == null) {
            return null;
        }
        ToolUserDto toolUserDto = new ToolUserDto();
        toolUserDto.setId(toolUser.getId());
        toolUserDto.setName(toolUser.getName());
        toolUserDto.setAge(toolUser.getAge());
        toolUserDto.setGender(toolUser.getGender());
        toolUserDto.setMobile(toolUser.getMobile());
        toolUserDto.setAddress(toolUser.getAddress());
        toolUserDto.setIdNum(toolUser.getIdNum());
        toolUserDto.setApiName(toolUser.getApiName());
        toolUserDto.setApiPassword(toolUser.getApiPassword());
        return toolUserDto;
    }
}
