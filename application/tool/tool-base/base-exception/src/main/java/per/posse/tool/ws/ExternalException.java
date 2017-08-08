package per.posse.tool.ws;

import per.posse.tool.ExceptionContext;
import per.posse.tool.ToolException;

/**
 * Created by posse on 17-7-31.
 */
public class ExternalException extends ToolException {
    public ExternalException() {
    }

    public ExternalException(String message) {
        super(message);
    }

    public ExternalException(Throwable cause) {
        super(cause);
    }

    public ExternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalException(String message, Throwable cause, ExceptionContext context) {
        super(message, cause, context);
    }
}
