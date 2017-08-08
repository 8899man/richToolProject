package pers.posse.tool.service.impl.persistence;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import pers.posse.tool.service.dto.ToolUserDto;
import pers.posse.tool.service.impl.domain.ToolUser;
import pers.posse.tool.service.impl.parser.ToolUserDtoParser;
import pers.posse.tool.service.persistence.IToolUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by posse on 17-7-20.
 */
@Repository
public class ToolUserRepositoryJPA implements IToolUserRepository {

    private static String FIND_USER_BY_ID = "find.user.by.id";

    @PersistenceContext
    private EntityManager em;

    @Override
    public ToolUserDto findUserByUserNameAndPassword(String apiName, String apiPassword) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<ToolUser> query = builder.createQuery(ToolUser.class);

        Root<ToolUser> root = query.from(ToolUser.class);
        query.where(builder.and(builder.equal(root.get("apiName"), apiName)),
                builder.equal(root.get("apiPassword"), apiPassword));
        List<ToolUser> toolUsers = em.createQuery(query).getResultList();
        return CollectionUtils.isEmpty(toolUsers) ? null : ToolUserDtoParser.fromDomain(toolUsers.get(0));
    }

    @Override
    public ToolUserDto findUserDto(Long id) {
        return ToolUserDtoParser.fromDomain(em.find(ToolUser.class, id));
    }

    @Override
    public Long createUser(ToolUserDto toolUserDto) {
        ToolUser toolUser = new ToolUser(null, toolUserDto.getName(), toolUserDto.getAge(), toolUserDto.getGender(),
            toolUserDto.getIdNum(), toolUserDto.getAddress(), toolUserDto.getMobile(), toolUserDto.getApiName(),
                toolUserDto.getApiPassword());
        em.persist(toolUser);
        return toolUser.getId();
    }

    @Override
    public void updateUser(ToolUserDto toolUserDto) {
        ToolUser toolUser = em.find(ToolUser.class, toolUserDto.getId());
        updateUser(toolUser, toolUserDto);
    }

    @Override
    public void deleteUser(Long id) {
        em.remove(this.em.find(ToolUser.class, id));
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

}
