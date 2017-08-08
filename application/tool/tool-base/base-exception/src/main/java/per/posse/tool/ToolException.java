package per.posse.tool;

import javafx.util.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by posse on 17-7-31.
 */
public class ToolException extends Exception implements ExceptionContext {

    /** The serialization version. */
    private static final long serialVersionUID = 1L;
    /** The context where the data is stored. */
    protected final ExceptionContext exceptionContext;

    /**
     * Instantiates ContextedException without message or cause.
     * <p>
     * The context information is stored using a default implementation.
     */
    public ToolException() {
        super();
        exceptionContext = new DefaultExceptionContext();
    }

    /**
     * Instantiates ContextedException with message, but without cause.
     * <p>
     * The context information is stored using a default implementation.
     *
     * @param message  the exception message, may be null
     */
    public ToolException(final String message) {
        super(message);
        exceptionContext = new DefaultExceptionContext();
    }

    /**
     * Instantiates ContextedException with cause, but without message.
     * <p>
     * The context information is stored using a default implementation.
     *
     * @param cause  the underlying cause of the exception, may be null
     */
    public ToolException(final Throwable cause) {
        super(cause);
        if(cause instanceof ToolRuntimeException || cause instanceof ToolException){
            exceptionContext = ((ExceptionContext)cause).getExceptionContext();
        } else {
            exceptionContext = new DefaultExceptionContext();
        }
    }

    /**
     * Instantiates ContextedException with cause and message.
     * <p>
     * The context information is stored using a default implementation.
     *
     * @param message  the exception message, may be null
     * @param cause  the underlying cause of the exception, may be null
     */
    public ToolException(final String message, final Throwable cause) {
        super(message, cause);
        if(cause instanceof ToolRuntimeException || cause instanceof ToolException){
            exceptionContext = ((ExceptionContext)cause).getExceptionContext();
        } else {
            exceptionContext = new DefaultExceptionContext();
        }
    }

    /**
     * Instantiates ContextedException with cause, message, and ExceptionContext.
     *
     * @param message  the exception message, may be null
     * @param cause  the underlying cause of the exception, may be null
     * @param context  the context used to store the additional information, null uses default implementation
     */
    public ToolException(final String message, final Throwable cause, ExceptionContext context) {
        super(message, cause);
        if (context == null) {
            context = new DefaultExceptionContext();
        }
        if(cause instanceof ToolRuntimeException || cause instanceof ToolException){
            exceptionContext = ((ExceptionContext)cause).getExceptionContext();
        } else {
            exceptionContext = context;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Adds information helpful to a developer in diagnosing and correcting the problem.
     * For the information to be meaningful, the value passed should have a reasonable
     * toString() implementation.
     * Different values can be added with the same label multiple times.
     * <p>
     * Note: This exception is only serializable if the object added is serializable.
     * </p>
     *
     * @param label  a textual label associated with information, {@code null} not recommended
     * @param value  information needed to understand exception, may be {@code null}
     * @return {@code this}, for method chaining, not {@code null}
     */
    @Override
    public ToolException addContextValue(final String label, final Object value) {
        exceptionContext.addContextValue(label, value);
        return this;
    }

    /**
     * Sets information helpful to a developer in diagnosing and correcting the problem.
     * For the information to be meaningful, the value passed should have a reasonable
     * toString() implementation.
     * Any existing values with the same labels are removed before the new one is added.
     * <p>
     * Note: This exception is only serializable if the object added as value is serializable.
     * </p>
     *
     * @param label  a textual label associated with information, {@code null} not recommended
     * @param value  information needed to understand exception, may be {@code null}
     * @return {@code this}, for method chaining, not {@code null}
     */
    @Override
    public ToolException setContextValue(final String label, final Object value) {
        exceptionContext.setContextValue(label, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getContextValues(final String label) {
        return this.exceptionContext.getContextValues(label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getFirstContextValue(final String label) {
        return this.exceptionContext.getFirstContextValue(label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pair<String, Object>> getContextEntries() {
        return this.exceptionContext.getContextEntries();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getContextLabels() {
        return exceptionContext.getContextLabels();
    }

    /**
     * Provides the message explaining the exception, including the contextual data.
     *
     * @see java.lang.Throwable#getMessage()
     * @return the message, never null
     */
    @Override
    public String getMessage(){
        return getFormattedExceptionMessage(super.getMessage());
    }

    /**
     * Provides the message explaining the exception without the contextual data.
     *
     * @see java.lang.Throwable#getMessage()
     * @return the message
     */
    public String getRawMessage() {
        return super.getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFormattedExceptionMessage(final String baseMessage) {
        return exceptionContext.getFormattedExceptionMessage(baseMessage);
    }

    @Override
    public void addAllContextValues(Collection<Pair<String, Object>> coll) {
        exceptionContext.addAllContextValues(coll);
    }

    @Override
    public Object getLastContextValue(String label) {
        return exceptionContext.getLastContextValue(label);
    }

    @Override
    public String getToolCode() {
        return exceptionContext.getToolCode();
    }

    @Override
    public String getToolMessage() {
        return exceptionContext.getToolMessage();
    }

    @Override
    public ToolException setToolCode(String toolCode) {
        exceptionContext.setToolCode(toolCode);
        return this;
    }

    @Override
    public ToolException setToolMessage(String toolMessage) {
        exceptionContext.setToolMessage(toolMessage);
        return this;
    }

    @Override
    public Collection<String> getAllToolCode() {
        return exceptionContext.getAllToolCode();
    }

    @Override
    public Collection<String> getAllToolMessage() {
        return exceptionContext.getAllToolMessage();
    }

    @Override
    public ExceptionContext getExceptionContext() {
        return exceptionContext;
    }
}
