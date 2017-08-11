package per.posse.tool.ws.impl;

import org.springframework.beans.factory.annotation.Autowired;
import per.posse.tool.ws.ExternalException;
import per.posse.tool.ws.ExternalOperateEntityService;
import per.posse.tool.ws.enums.OperationType;
import per.posse.tool.ws.response.ResponseBuilder;
import per.posse.tool.ws.xml.DomainAttribute;
import per.posse.tool.ws.xml.ExternalOperateEntityRequest;
import per.posse.tool.ws.xml.ExternalOperateEntityResponse;
import per.posse.tool.ToolException;
import per.posse.tool.service.IToolUserService;
import per.posse.tool.dto.ToolUserDto;

import java.util.concurrent.Semaphore;

/**
 * Created by posse on 17-7-19.
 */
public class ExternalOperateEntityServiceImpl implements ExternalOperateEntityService {

    @Autowired
    private IToolUserService userService;

    @Override
    public ExternalOperateEntityResponse service(ExternalOperateEntityRequest request) {
        // TODO log
        long start = System.currentTimeMillis();

        ExternalOperateEntityResponse response = new ExternalOperateEntityResponse();

        ToolUserDto toolUserDto;
        try {
            toolUserDto = userService.authApiUser(request.getApiName(), request.getApiPassword());
        } catch (ToolException te) {
            ResponseBuilder.addResult(response, te.getRawMessage());
            return response;
        }

        Semaphore semp = new Semaphore(5);
        try {
            semp.acquire();
            try {
                handleRequest(request, response, toolUserDto);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                ResponseBuilder.addResult(response, new ExternalException("server exception.").getRawMessage());
                return response;
            } finally {
                semp.release();
            }
        } catch (InterruptedException ite) {
            ResponseBuilder.addResult(response, new ExternalException("concurrent exception.").getRawMessage());
            return response;
        }
    }

    private void handleRequest(ExternalOperateEntityRequest request, ExternalOperateEntityResponse response,
            ToolUserDto toolUserDto) {
        switch (request.getOperation().getDomain()) {
        case TOOL_USER:
            handleToolUser(request.getOperation().getType(), request.getAttribute(), response);
            break;
        default:
            break;
        }
    }

    private void handleToolUser(OperationType type, DomainAttribute attribute, ExternalOperateEntityResponse response) {
        try {
            switch (type) {
            case CREATE:
                userService.createUser(attribute);
                break;
            case RETRIEVE:
                ToolUserDto user = userService.retrieve(attribute.getId());
                ResponseBuilder.builderRetrieveEntityResult(response, user);
                break;
            case UPDATE:
                userService.updateUser(attribute);
                break;
            case DELETE:
                userService.deleteUser(attribute.getId());
                break;
            default:
                break;
            }
        } catch (ExternalException ee) {
            ee.printStackTrace();
            ResponseBuilder.addResult(response, ee.getRawMessage());
            return;
        }
        return;
    }
}
