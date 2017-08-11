package per.posse.tool.service.persistence;

import per.posse.tool.dto.ToolUserDto;

/**
 * Created by posse on 17-7-20.
 */
public interface IToolUserRepository {

    ToolUserDto findUserByApiUserNameAndPassword(String apiName, String apiPassword);

    ToolUserDto findUserByLoginUserNameAndPassword(String loginEmail, String loginPassword);

    ToolUserDto findUserDto(Long id);

    Long createUser(ToolUserDto toolUserDto);

    void updateUser(ToolUserDto toolUserDto);

    void deleteUser(Long id);

}
