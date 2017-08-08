package per.posse.tool.service.impl.persistence;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import per.posse.tool.service.impl.domain.ToolUser;
import per.posse.tool.service.impl.parser.ToolUserDtoParser;
import per.posse.tool.service.dto.ToolUserDto;
import per.posse.tool.service.persistence.IToolUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by posse on 17-7-20.
 */
@Repository
public class ToolUserRepositoryJPA implements IToolUserRepository {

    public static final String UPDATE_TOOL_USER = "update.tool.user";

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
        Query query = em.createNamedQuery(UPDATE_TOOL_USER);
        query.setParameter("name", toolUserDto.getName());
        query.setParameter("age", toolUserDto.getAge());
        query.setParameter("gender", toolUserDto.getGender());
        query.setParameter("idNum", toolUserDto.getIdNum());
        query.setParameter("address", toolUserDto.getAddress());
        query.setParameter("mobile", toolUserDto.getMobile());
        query.setParameter("apiName", toolUserDto.getApiName());
        query.setParameter("apiPassword", toolUserDto.getApiPassword());
        query.setParameter("id", toolUserDto.getId());
        query.executeUpdate();
    }

    @Override
    public void deleteUser(Long id) {
        em.remove(this.em.find(ToolUser.class, id));
    }
}
