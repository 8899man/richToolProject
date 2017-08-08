package per.posse.tool;

import javafx.util.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Allows the storage and retrieval of contextual information based on
 * label-value pairs for exceptions.
 * <p>
 * Implementations are expected to manage the pairs in a list-style collection
 * that keeps the pairs in the sequence of their addition.
 * </p>
 *
 */
public interface ExceptionContext {

    public static final String CODE = "tool_code";
    public static final String MESSAGE = "tool_message";

    /**
     * Adds a contextual label-value pair into this context.
     * <p>
     * The pair will be added to the context, independently of an already
     * existing pair with the same label.
     * </p>
     *
     * @param label
     *            the label of the item to add, {@code null} not recommended
     * @param value
     *            the value of item to add, may be {@code null}
     * @return {@code this}, for method chaining, not {@code null}
     */
    ExceptionContext addContextValue(String label, Object value);

    /**
     * Sets a contextual label-value pair into this context.
     * <p>
     * The pair will be added normally, but any existing label-value pair with
     * the same label is removed from the context.
     * </p>
     *
     * @param label
     *            the label of the item to add, {@code null} not recommended
     * @param value
     *            the value of item to add, may be {@code null}
     * @return {@code this}, for method chaining, not {@code null}
     */
    ExceptionContext setContextValue(String label, Object value);

    void addAllContextValues(Collection<Pair<String, Object>> coll);

    /**
     * Retrieves all the contextual data values associated with the label.
     *
     * @param label
     *            the label to get the contextual values for, may be
     *            {@code null}
     * @return the contextual values associated with the label, never
     *         {@code null}
     */
    List<Object> getContextValues(String label);

    /**
     * Get the first Tool Code in ExceptionContext
     *
     * @return
     */
    String getToolCode();

    /**
     * Get the first Tool Message in ExceptionContext
     *
     * @return
     */
    String getToolMessage();

    /**
     * Get all of Tool Codes in ExceptionContext
     *
     * @return
     */
    Collection<String> getAllToolCode();

    /**
     * Get all of Tool Messages in ExceptionContext
     *
     * @return
     */
    Collection<String> getAllToolMessage();

    /**
     * Set Tool Code into ExceptionContext
     *
     * @param toolCode
     * @return
     */
    ExceptionContext setToolCode(String toolCode);

    /**
     * Set Tool Message into ExceptionContext
     *
     * @param toolMessage
     * @return
     */
    ExceptionContext setToolMessage(String toolMessage);

    /**
     * Retrieves the first available contextual data value associated with the
     * label.
     *
     * @param label
     *            the label to get the contextual value for, may be {@code null}
     * @return the first contextual value associated with the label, may be
     *         {@code null}
     */
    Object getFirstContextValue(String label);

    /**
     * Get the last available contextual data value associated with the label.
     *
     * @param label
     * @return
     */
    Object getLastContextValue(String label);

    /**
     * Retrieves the full set of labels defined in the contextual data.
     *
     * @return the set of labels, not {@code null}
     */
    Set<String> getContextLabels();

    /**
     * Retrieves the full list of label-value pairs defined in the contextual
     * data.
     *
     * @return the list of pairs, not {@code null}
     */
    List<Pair<String, Object>> getContextEntries();

    /**
     * Gets the contextualized error message based on a base message. This will
     * add the context label-value pairs to the message.
     *
     * @param baseMessage
     *            the base exception message <b>without</b> context information
     *            appended
     * @return the exception message <b>with</b> context information appended,
     *         not {@code null}
     */
    String getFormattedExceptionMessage(String baseMessage);

    /**
     * Get ExceptionContext
     *
     * @return
     */
    ExceptionContext getExceptionContext();

}
