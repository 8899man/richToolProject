package pers.posse.tool.service.persistence.finder;

import pers.posse.tool.service.dto.ToolUserDto;

/**
 * Created by posse on 17-7-20.
 */
public interface IUserFinder {
    ToolUserDto findUserByUserNameAndPassword(String apiName, String apiPassword);

    ToolUserDto findUserDto(Long id);
}
