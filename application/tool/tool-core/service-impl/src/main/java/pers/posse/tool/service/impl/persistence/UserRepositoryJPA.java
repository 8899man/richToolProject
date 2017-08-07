package pers.posse.tool.service.impl.persistence;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.posse.tool.service.dto.ToolUserDto;
import pers.posse.tool.service.impl.domain.ToolUser;
import pers.posse.tool.service.persistence.IUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by posse on 17-7-20.
 */
@Repository
public class UserRepositoryJPA implements IUserRepository {

    private static String FIND_USER_BY_ID = "find.user.by.id";

    @PersistenceContext(unitName = "persistenceUnitWrite")
    private EntityManager emWrite;

    @Override
    public Long createUser(ToolUserDto toolUserDto) {
        ToolUser toolUser = new ToolUser(null, toolUserDto.getName(), toolUserDto.getAge(), toolUserDto.getGender(), toolUserDto
                .getIdNum(),
                toolUserDto.getAddress(), toolUserDto.getMobile(), toolUserDto.getApiName(), toolUserDto.getApiPassword());
        emWrite.persist(toolUser);
        return toolUser.getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateUser(ToolUserDto toolUserDto) {
        ToolUser toolUser = emWrite.find(ToolUser.class, toolUserDto.getId());
        updateUser(toolUser, toolUserDto);
    }

    private void updateUser(ToolUser toolUser, ToolUserDto toolUserDto) {
        if (toolUserDto == null) {
            return;
        }
        toolUser.setName(StringUtils.isBlank(toolUserDto.getName()) ? null : toolUserDto.getName());
        toolUser.setAge(toolUserDto.getAge() == null ? null : toolUserDto.getAge());
        toolUser.setGender(toolUserDto.getGender() == null ? null : toolUserDto.getGender());
        toolUser.setMobile(StringUtils.isBlank(toolUserDto.getMobile()) ? null : toolUserDto.getMobile());
        toolUser.setAddress(StringUtils.isBlank(toolUserDto.getAddress()) ? null : toolUserDto.getAddress());
        toolUser.setIdNum(StringUtils.isBlank(toolUserDto.getIdNum()) ? null : toolUserDto.getIdNum());
        toolUser.setApiName(StringUtils.isBlank(toolUserDto.getApiName()) ? null : toolUserDto.getApiName());
        toolUser.setApiPassword(StringUtils.isBlank(toolUserDto.getApiPassword()) ? null : toolUserDto.getApiPassword());
    }

    @Override
    public void deleteUser(Long id) {
        emWrite.remove(this.emWrite.find(ToolUser.class, id));
    }
}
