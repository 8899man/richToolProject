package pers.posse.tool.web.ws.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.posse.tool.ToolException;
import pers.posse.tool.service.IToolUserService;
import pers.posse.tool.service.dto.ToolUserDto;
import pers.posse.tool.web.ws.ExternalOperateEntityService;
import pers.posse.tool.web.ws.enums.OperationType;
import pers.posse.tool.web.ws.response.ResponseBuilder;
import pers.posse.tool.web.ws.xml.DomainAttribute;
import pers.posse.tool.web.ws.xml.ExternalOperateEntityRequest;
import pers.posse.tool.web.ws.xml.ExternalOperateEntityResponse;
import pers.posse.tool.ws.ExternalException;
import pers.posse.tool.ws.WsAuthenticationException;

import java.util.concurrent.Semaphore;

/**
 * Created by posse on 17-7-19.
 */
public class ExternalOperateEntityServiceImpl implements ExternalOperateEntityService {

    @Autowired
    private IToolUserService userService;

    @Override
    public ExternalOperateEntityResponse service(ExternalOperateEntityRequest request) {
        long start = System.currentTimeMillis();

        ExternalOperateEntityResponse response = new ExternalOperateEntityResponse();

        ToolUserDto toolUserDto;
        try {
            toolUserDto = userService.authUser(request.getApiName(), request.getApiPassword());
        } catch (ToolException te) {
            ResponseBuilder.addResult(response, te.getToolCode());
            return response;
        }

        // http://blog.csdn.net/lipeng_bigdata/article/details/52165426
        // 创建一个计数阈值为5的信号量对象, 只能5个线程同时访问
        // 默认的是非公平性的, 如果想要公平, 使用 new Semaphore(5, true);
        Semaphore semp = new Semaphore(5);
        try {
            // 申请许可
            /**
             *   Semaphore也提供了boolean tryAcquire(long timeout, TimeUnit unit)、tryAcquire()等限制时间内阻塞或非阻塞实现方式，
             *   比较简单，但是有一点，公平模式下的tryAcquire()、tryAcquire(int permits)会打破原先的公平性，因为其是通过调用sync的nonfairTryAcquireShared()方法的方式实现的，
             *   需要另外使用tryAcquire(long timeout, TimeUnit unit)、tryAcquire(int permits, long timeout, TimeUnit unit)来保持公平性。
             *
             */
            semp.acquire();
            try {
                handleRequest(request, response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                ResponseBuilder.addResult(response, new ExternalException("server exception").getMessage());
                return response;
            } finally {
                /**
                 * 放一个许可，将其返回给信号量。释放一个许可，将可用的许可数增加 1。如果任意线程试图获取许可，则选中一个线程并将刚刚释放的许可给予它。
                 * 然后针对线程安排目的启用（或再启用）该线程。
                 * 不要求释放许可的线程必须通过调用 acquire() 来获取许可。通过应用程序中的编程约定来建立信号量的正确用法。
                 */
                semp.release();
            }
        } catch (InterruptedException ite) {
            ResponseBuilder.addResult(response, new ExternalException("auth failed").getMessage());
            return response;
        }
    }

    private void handleRequest(ExternalOperateEntityRequest request, ExternalOperateEntityResponse response) {
        switch (request.getOperation().getDomain()) {
        case USER:
            handleUser(request.getOperation().getType(), request.getAttribute(), response);
            break;
        default:
            break;
        }
    }

    private void handleUser(OperationType type, DomainAttribute attribute, ExternalOperateEntityResponse response) {
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
            ResponseBuilder.addResult(response, ee.getMessage());
            return;
        }
        ResponseBuilder.addResult(response, new WsAuthenticationException("Success").getResponseCode());
        return;
    }
}
