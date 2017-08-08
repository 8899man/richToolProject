package pers.posse.tool.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.posse.tool.ws.ExternalException;
import per.posse.tool.ws.xml.DomainAttribute;
import pers.posse.tool.service.IToolUserService;
import pers.posse.tool.service.dto.ToolUserDto;
import pers.posse.tool.service.persistence.IToolUserRepository;

/**
 * Created by posse on 17-7-25.
 */
@Service
public class ToolUserService implements IToolUserService {

    @Autowired
    private IToolUserRepository toolUserRepository;

    @Override
    @Transactional
    public ToolUserDto authUser(String apiName, String apiPassword) throws ExternalException {
        if (StringUtils.isBlank(apiName) || StringUtils.isBlank(apiPassword)) {
            throw new ExternalException("auth info missing.");
        }

        ToolUserDto userDto = toolUserRepository.findUserByUserNameAndPassword(apiName, apiPassword);
        if (userDto == null) {
            throw new ExternalException("user not exists.");
        }
        return userDto;
    }

    @Override
    @Transactional
    public void createUser(DomainAttribute attribute) {
        ToolUserDto toolUserDto = new ToolUserDto();
        toolUserDto.setName(attribute.getName());
        toolUserDto.setAge(attribute.getAge());
        toolUserDto.setGender(attribute.getGender());
        toolUserDto.setMobile(attribute.getMobile());
        toolUserDto.setIdNum(attribute.getIdNum());
        toolUserDto.setAddress(attribute.getAddress());
        toolUserDto.setApiName(attribute.getApiName());
        toolUserDto.setApiPassword(attribute.getApiPassword());
        toolUserRepository.createUser(toolUserDto);
    }

    @Override
    public ToolUserDto retrieve(Long id) throws ExternalException {
        if (id == null) {
            throw new ExternalException("id can not null");
        }
        return toolUserRepository.findUserDto(id);
    }

    @Override
    @Transactional
    public void updateUser(DomainAttribute attribute) throws ExternalException {

        if (retrieve(attribute.getId()) == null) {
            throw new ExternalException("user not exists");
        }

        ToolUserDto toolUserDto = new ToolUserDto();
        toolUserDto.setId(attribute.getId());
        toolUserDto.setName(attribute.getName());
        toolUserDto.setAge(attribute.getAge());
        toolUserDto.setGender(attribute.getGender());
        toolUserDto.setMobile(attribute.getMobile());
        toolUserDto.setIdNum(attribute.getIdNum());
        toolUserDto.setAddress(attribute.getAddress());
        toolUserDto.setApiName(attribute.getApiName());
        toolUserDto.setApiPassword(attribute.getApiPassword());
        toolUserRepository.updateUser(toolUserDto);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) throws ExternalException {
        if (id == null) {
            throw new ExternalException("id can not null");
        }
        toolUserRepository.deleteUser(id);
    }
}
