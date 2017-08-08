package pers.posse.tool.service.persistence;

import pers.posse.tool.service.dto.ToolUserDto;

/**
 * Created by posse on 17-7-20.
 */
public interface IToolUserRepository {

    ToolUserDto findUserByUserNameAndPassword(String apiName, String apiPassword);

    ToolUserDto findUserDto(Long id);

    Long createUser(ToolUserDto toolUserDto);

    void updateUser(ToolUserDto toolUserDto);

    void deleteUser(Long id);

}
