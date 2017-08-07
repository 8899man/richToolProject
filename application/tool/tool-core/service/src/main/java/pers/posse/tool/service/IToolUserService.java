package pers.posse.tool.service;

import pers.posse.tool.service.dto.ToolUserDto;
import pers.posse.tool.web.ws.xml.DomainAttribute;
import pers.posse.tool.ws.ExternalException;

/**
 * Created by posse on 17-7-20.
 */
public interface IToolUserService {
    ToolUserDto authUser(String userName, String password) throws ExternalException;

    void createUser(DomainAttribute attribute);

    ToolUserDto retrieve(Long id) throws ExternalException;

    void updateUser(DomainAttribute attribute) throws ExternalException;

    void deleteUser(Long id) throws ExternalException;
}
