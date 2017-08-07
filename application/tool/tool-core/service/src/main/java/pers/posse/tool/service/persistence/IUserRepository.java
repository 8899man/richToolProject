package pers.posse.tool.service.persistence;

import pers.posse.tool.service.dto.ToolUserDto;

/**
 * Created by posse on 17-7-20.
 */
public interface IUserRepository {
    Long createUser(ToolUserDto toolUserDto);

    void updateUser(ToolUserDto toolUserDto);

    void deleteUser(Long id);
}
