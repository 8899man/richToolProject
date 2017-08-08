package per.posse.tool.utils.quartz;

import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by posse on 17-8-8.
 */
public class ToolJobDetailFactoryBean extends JobDetailFactoryBean {
    private static final long serialVersionUID = 1L;

    private Map<String, Object> arguments;

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    @Override
    public void afterPropertiesSet() {
        if (arguments != null) {
            for (Iterator<Map.Entry<String, Object>> it = arguments.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
                getJobDataMap().put(entry.getKey(), entry.getValue());
            }
        }
        super.afterPropertiesSet();
    }
}
