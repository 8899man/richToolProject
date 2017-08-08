package per.posse.tool.ws;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import per.posse.tool.ws.xml.ExternalOperateEntityRequest;
import per.posse.tool.ws.xml.ExternalOperateEntityResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by posse on 17-7-19.
 */
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@Path("/operate")
@Api(value = "External insert entity service")
public interface ExternalOperateEntityService {
    @POST
    @Path("/entity")
    @ApiOperation(value = "external insert entity", response = ExternalOperateEntityResponse.class, httpMethod = "POST", notes = "接口发布说明") ExternalOperateEntityResponse service(
            @ApiParam(required = true, name = "externalOperateEntityRequest", value = "参数具体描述") ExternalOperateEntityRequest externalInsertEntityRequest);

}
