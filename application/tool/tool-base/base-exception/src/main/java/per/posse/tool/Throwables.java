package per.posse.tool;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Helps with Throwable objects.
 */
public final class Throwables {

    private Throwables() {
    }

    /**
     * Returns the deepest cause of the given {@code throwable}.
     *
     * @param throwable the throwable to navigate
     * @return the deepest throwable or the given throwable
     */
    public static Throwable getRootCause(final Throwable throwable) {
        Throwable cause;
        Throwable root = throwable;
        while (root != null && (cause = root.getCause()) != null) {
            root = cause;
        }
        return root;
    }
    
    public static String getRootCauseMessage(final Throwable th) {
        Throwable root = getRootCause(th);
        root = root == null ? th : root;
        return getMessage(root);
    }
    
    public static String getRootCauseMessageWithClassName(final Throwable th) {
        Throwable root = getRootCause(th);
        root = root == null ? th : root;
        return getMessageWithClassName(root);
    }
    
    public static String getRawMessage(final Throwable th) {
        if (th == null) {
            return "";
        }
        String msg = "";
        if(th instanceof ToolRuntimeException) {
        	msg = ((ToolRuntimeException)th).getRawMessage();
        } else if (th instanceof ToolException) {
        	msg = ((ToolException)th).getRawMessage();
        } else {
        	msg = th.getMessage();
        }
        return msg;
    }
    
    public static String getMessage(final Throwable th) {
        if (th == null) {
            return "";
        }
        final String msg = th.getMessage();
        return msg;
    }
    
    public static String getMessageWithClassName(final Throwable th) {
        if (th == null) {
            return "";
        }
        final String clsName = th.getClass().getName();
        final String msg = th.getMessage();
        return clsName + ": " + msg;
    }
    
    
    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    /**
     * Converts a Throwable stack trace into a List of Strings.
     *
     * @param throwable the Throwable
     * @return a List of Strings
     */
    public static List<String> toStringList(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
        } catch (final RuntimeException ex) {
            // Ignore any exceptions.
        }
        pw.flush();
        final List<String> lines = new ArrayList<>();
        final LineNumberReader reader = new LineNumberReader(new StringReader(sw.toString()));
        try {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (final IOException ex) {
            if (ex instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            lines.add(ex.toString());
        } finally {
        	try {
        		if(reader!=null){
        			reader.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return lines;
    }
    
    public static void throwAsToolRuntime(final Throwable t) {
    	ToolRuntimeException r = null;
    	if(t != null){
    		if(t instanceof ToolRuntimeException){
    			r = (ToolRuntimeException)t;
    		} else {
    			r = new ToolRuntimeException(t);
    		}
    		Throwables.<ToolRuntimeException>rethrow0(r);
    	}
    }

    /**
     * Rethrows a {@link Throwable}.
     *
     * @param t the Throwable to throw.
     */
    public static void rethrow(final Throwable t) {
        Throwables.<RuntimeException>rethrow0(t);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void rethrow0(final Throwable t) throws T {
        throw (T) t;
    }
}
