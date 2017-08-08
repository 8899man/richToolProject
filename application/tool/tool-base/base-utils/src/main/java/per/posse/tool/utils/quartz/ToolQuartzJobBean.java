package per.posse.tool.utils.quartz;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by posse on 17-8-8.
 */
public abstract class ToolQuartzJobBean extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context){
        // TODO log
        System.out.println(context.getJobDetail().getKey() + "at : " + System.currentTimeMillis());
        JobDataMap arguments = context.getJobDetail().getJobDataMap();
        boolean errorOccured = false;
        if (arguments != null) {
            String method = arguments.getString("method");
            if (!StringUtils.isEmpty(method)) {
                Method m = null;
                // do what?
                // 提前check job方法是否正常
                try {
                    m = this.getClass().getDeclaredMethod(method, JobExecutionContext.class);
                } catch (NoSuchMethodException | SecurityException e) {
                    errorOccured = true;
                    System.out.println("Error occured when get declared method fot the job: " + context.getJobDetail().getKey()
                            + ",class: " + this.getClass().getName() + ", method: " + method);
                }
                try {
                    if(!errorOccured){
                        m.invoke(this, context);
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    errorOccured = true;
                    System.out.println("Error occured when reflect invoke method " + context.getJobDetail().getKey()
                            + ",class: " + this.getClass().getName() + ",method: " + method +"arguments:"+arguments);
                }
            }
            try {
                if(!errorOccured){
                    this.toolExecuteInternal(context);
                }
            } catch (JobExecutionException e) {
                errorOccured = true;
                System.out.println("Error occured when execute job: " + context.getJobDetail().getKey()
                        + ",class: " + this.getClass().getName() + ",method: " + method +"arguments:"+arguments);
            }
        }

        System.out.println("job status is" + !errorOccured);
    }

    /**
     * The actual business logic should in sub-class.
     * */
    protected abstract void toolExecuteInternal(JobExecutionContext context) throws JobExecutionException;
}
