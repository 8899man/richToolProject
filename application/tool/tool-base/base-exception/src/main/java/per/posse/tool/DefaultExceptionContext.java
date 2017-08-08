package per.posse.tool;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Default implementation of the context storing the label-value pairs for contexted exceptions.
 * <p>
 * This implementation is serializable, however this is dependent on the values that
 * are added also being serializable.
 * </p>
 *
 */
public class DefaultExceptionContext implements ExceptionContext, Serializable {

    /** The serialization version. */
    private static final long serialVersionUID = 1L;

    /** The list storing the label-data pairs. */
    private final List<Pair<String, Object>> contextValues = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public DefaultExceptionContext addContextValue(final String label, final Object value) {
        contextValues.add(new Pair<>(label, value));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DefaultExceptionContext setContextValue(final String label, final Object value) {
        for (final Iterator<Pair<String, Object>> iter = contextValues.iterator(); iter.hasNext();) {
            final Pair<String, Object> p = iter.next();
            if (label !=null && label.equals(p.getKey())) {
                iter.remove();
            }
        }
        addContextValue(label, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getContextValues(final String label) {
        final List<Object> values = new ArrayList<>();
        for (final Pair<String, Object> pair : contextValues) {
        	if (label !=null && label.equals(pair.getKey())) {
                values.add(pair.getValue());
            }
        }
        return values;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getFirstContextValue(final String label) {
        for (final Pair<String, Object> pair : contextValues) {
        	if (label !=null && label.equals(pair.getKey())) {
                return pair.getValue();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getContextLabels() {
        final Set<String> labels = new HashSet<>();
        for (final Pair<String, Object> pair : contextValues) {
            labels.add(pair.getKey());
        }
        return labels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pair<String, Object>> getContextEntries() {
        return contextValues;
    }

    /**
     * Builds the message containing the contextual information.
     * 
     * @param baseMessage  the base exception message <b>without</b> context information appended
     * @return the exception message <b>with</b> context information appended, never null
     */
    @Override
    public String getFormattedExceptionMessage(final String baseMessage){
        final StringBuilder buffer = new StringBuilder(256);
        if (baseMessage != null) {
            buffer.append(baseMessage);
        }
        
        if (contextValues.size() > 0) {
            if (buffer.length() > 0) {
                buffer.append('\n');
            }
            buffer.append("Exception Context:\n");
            
            int i = 0;
            for (final Pair<String, Object> pair : contextValues) {
                buffer.append("\t[");
                buffer.append(++i);
                buffer.append(':');
                buffer.append(pair.getKey());
                buffer.append("=");
                final Object value = pair.getValue();
                if (value == null) {
                    buffer.append("null");
                } else {
                    String valueStr;
                    try {
                        valueStr = value.toString();
                    } catch (final Exception e) {
                        valueStr = "Exception thrown on toString(): " + Throwables.getStackTrace(e);
                    }
                    buffer.append(valueStr);
                }
                buffer.append("]\n");
            }
            buffer.append("---------------------------------");
        }
        return buffer.toString();
    }

	@Override
	public void addAllContextValues(Collection<Pair<String, Object>> coll) {
		if(coll != null && coll.size()>0) {
			for(Pair<String, Object> p : coll){
				setContextValue(p.getKey(), p.getValue());
			}
		}
	}

	@Override
	public Object getLastContextValue(String label) {
		for (int i=contextValues.size()-1; i>-1;i--) {
			Pair<String, Object> pair = contextValues.get(i);
        	if (label !=null && label.equals(pair.getKey())) {
                return pair.getValue();
            }
        }
        return null;
	}

	@Override
	public String getToolCode() {
		return (String)getFirstContextValue(CODE);
	}

	@Override
	public String getToolMessage() {
		return (String)getFirstContextValue(MESSAGE);
	}

	@Override
	public ExceptionContext setToolCode(String toolCode) {
		return addContextValue(CODE,toolCode);
	}

	@Override
	public ExceptionContext setToolMessage(String toolMessage) {
		return addContextValue(MESSAGE,toolMessage);
	}

	@Override
	public Collection<String> getAllToolCode() {
		final List<String> values = new ArrayList<>();
        for (final Pair<String, Object> pair : contextValues) {
        	if (CODE.equals(pair.getKey())) {
                values.add((String)pair.getValue());
            }
        }
        return values;
	}

	@Override
	public Collection<String> getAllToolMessage() {
		final List<String> values = new ArrayList<>();
        for (final Pair<String, Object> pair : contextValues) {
        	if (MESSAGE.equals(pair.getKey())) {
                values.add((String)pair.getValue());
            }
        }
        return values;
	}

	@Override
	public ExceptionContext getExceptionContext() {
		return this;
	}

}
