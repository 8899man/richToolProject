package pers.posse.tool.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.message.Message;

/**
 * Created by posse on 17-7-18.
 */
public class EncodingLoggingInInterceptor extends LoggingInInterceptor {
    @Override
    public void handleMessage(Message message) throws Fault {
        String encoding = System.getProperty("file.encoding");
        encoding = StringUtils.isEmpty(encoding) ? "UTF-8" : encoding;
        // TODO log
        System.out.println("externalOperationEntityService interceptor encodingLoggingInInterceptor : encoding = " + encoding);
        message.put(Message.ENCODING, encoding);
        super.handleMessage(message);
    }
}
