package per.posse.tool.service;

import per.posse.tool.service.dto.ToolUserDto;
import per.posse.tool.ws.xml.DomainAttribute;
import per.posse.tool.ws.ExternalException;

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
