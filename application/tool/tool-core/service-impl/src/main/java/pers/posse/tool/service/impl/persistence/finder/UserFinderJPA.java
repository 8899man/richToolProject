package pers.posse.tool.service.impl.persistence.finder;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import pers.posse.tool.service.dto.ToolUserDto;
import pers.posse.tool.service.impl.domain.ToolUser;
import pers.posse.tool.service.impl.parser.UserDtoParser;
import pers.posse.tool.service.persistence.finder.IUserFinder;

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
public class UserFinderJPA implements IUserFinder {

    @PersistenceContext(unitName = "persistenceUnitRead")
    private EntityManager emRead;

    @Override
    public ToolUserDto findUserByUserNameAndPassword(String apiName, String apiPassword) {
        CriteriaBuilder builder = emRead.getCriteriaBuilder();
        CriteriaQuery<ToolUser> query = builder.createQuery(ToolUser.class);

        Root<ToolUser> root = query.from(ToolUser.class);
        query.where(builder.and(builder.equal(root.get("apiName"), apiName)),
                builder.equal(root.get("apiPassword"), apiPassword));
        List<ToolUser> toolUsers = emRead.createQuery(query).getResultList();
        return CollectionUtils.isEmpty(toolUsers) ? null : UserDtoParser.fromDomain(toolUsers.get(0));
    }

    private ToolUser findUser(Long id) {
        CriteriaBuilder builder = emRead.getCriteriaBuilder();
        CriteriaQuery<ToolUser> query = builder.createQuery(ToolUser.class);

        Root<ToolUser> root = query.from(ToolUser.class);
        query.where(builder.and(builder.equal(root.get("id"), id)));
        List<ToolUser> toolUsers = emRead.createQuery(query).getResultList();
        return CollectionUtils.isEmpty(toolUsers) ? null : toolUsers.get(0);
    }

    @Override
    public ToolUserDto findUserDto(Long id) {
        ToolUser toolUser = findUser(id);
        return toolUser == null ? null : UserDtoParser.fromDomain(toolUser);
    }
}
