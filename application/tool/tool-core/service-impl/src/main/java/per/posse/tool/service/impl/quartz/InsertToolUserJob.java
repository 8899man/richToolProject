package per.posse.tool.service.impl.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import per.posse.tool.utils.quartz.ToolQuartzJobBean;

/**
 * Created by posse on 17-8-8.
 */
public class InsertToolUserJob extends ToolQuartzJobBean {

    @Override
    protected void toolExecuteInternal(JobExecutionContext context) throws JobExecutionException {
        quartzTest();
    }

    private void quartzTest() {
        System.out.println("quartz test print...");
    }
}
