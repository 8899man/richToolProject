package per.posse.tool.service.impl.test;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by posse on 17-8-8.
 */
/**
 * Notices:
 *
 * Job : 表示一个工作，要执行的具体内容。此接口中只有一个方法
 * JobDetail : 表示一个具体的可执行的调度程序，Job是这个可执行程调度程序所要执行的内容，另外JobDetail还包含了这个任务调度的方案和策略。
 * Trigger : 代表一个调度参数的配置，什么时候去调。
 * Scheduler : 代表一个调度容器，一个调度容器中可以注册多个JobDetail和Trigger。当Trigger与JobDetail组合，就可以被Scheduler容器调度了.
 */

/**
 *
 * 1、scheduler是一个计划调度器容器（总部），容器里面可以盛放众多的JobDetail和trigger，当容器启动后，里面的每个JobDetail都会根据trigger按部就班自动去执行。
 *
 * 2、JobDetail是一个可执行的工作，它本身可能是有状态的。
 *
 * 3、Trigger代表一个调度参数的配置，什么时候去调。
 *
 * 4、当JobDetail和Trigger在scheduler容器上注册后，形成了装配好的作业（JobDetail和Trigger所组成的一对儿），就可以伴随容器启动而调度执行了。
 *
 * 5、scheduler是个容器，容器中有一个线程池，用来并行调度执行每个作业，这样可以提高容器效率。
 *
 * 6、Quartz与Spring的整合也非常简单，Spring提供一组Bean来支持：MethodInvokingJobDetailFactoryBean、SimpleTriggerBean、SchedulerFactoryBean，看看里面需要注入什么属性即可明白了。Spring会在Spring容器启动时候，启动Quartz容器。
 *
 * 7、Quartz的JobDetail、Trigger都可以在运行时重新设置，并且在下次调用时候起作用。这就为动态作业的实现提供了依据。你可以将调度时间策略存放到数据库，然后通过数据库数据来设定Trigger，这样就能产生动态的调度。
 *
 * 晚上11点到早上8点之间每两个小时，早上八点
 *  0 23-7/2，8 * * *
 * 每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点
 *  0 11 4 * 1-3
 * /

public class QuartzTest implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(new Date() + ": doing something...");
    }
}

class Test {
    public static void main(String[] args) {
        //1、创建JobDetial对象
        JobDetail jobDetail = new JobDetail();
        //设置内容
        jobDetail.setJobClass(QuartzTest.class);
        jobDetail.setName("MyJob_1");
        jobDetail.setGroup("JobGroup_1");

        //2、创建Trigger对象
        SimpleTrigger strigger = new SimpleTrigger();
        strigger.setName("Trigger_1");
        strigger.setGroup("Trigger_Group_1");
        strigger.setStartTime(new Date());

        //设置重复停止时间，并销毁该Trigger对象,
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis() + 5000 * 1L);
        strigger.setEndTime(c.getTime());
        strigger.setFireInstanceId("Trigger_1_id_001");
        //设置重复间隔时间
        strigger.setRepeatInterval(1000 * 1L);
        //设置重复执行次数, 重复次数结束, 处于休眠状态, 没有退出, 退出需要显示调用shutdown方法, 如果结束时间设置比较短,可能就会没有执行到重复3次就退出了.
        // 结束时间充足的情况下, 执行次数 = 1 + 重复次数.
        strigger.setRepeatCount(3);

        //3、创建Scheduler对象，并配置JobDetail和Trigger对象
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = sf.getScheduler();
            scheduler.scheduleJob(jobDetail, strigger);
            //4、并执行启动、关闭等操作
            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        //                try {
        //                        //关闭调度器
        //                        scheduler.shutdown(true);
        //                } catch (SchedulerException e) {
        //                        e.printStackTrace();
        //                }
    }

    class TestJob implements Job {
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String returnstr = DateFormat.format(d);

        public void execute(JobExecutionContext arg0) throws JobExecutionException {
            // TODO Auto-generated method stub
            System.out.println(returnstr+"★★★★★★★★★★★");
        }

    }

    class QuartzTest {

        public static void main(String[] args) {

            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date d = new Date();
            String returnstr = DateFormat.format(d);

            TestJob job = new TestJob();
            String job_name ="11";
            try {
                System.out.println(returnstr+ "【系统启动】");
                QuartzManager.addJob(job_name,job,"0/2 * * * * ?"); //每2秒钟执行一次

                //            Thread.sleep(10000);
                //            System.out.println("【修改时间】");
                //            QuartzManager.modifyJobTime(job_name,"0/10 * * * * ?");
                //            Thread.sleep(20000);
                //            System.out.println("【移除定时】");
                //            QuartzManager.removeJob(job_name);
                //            Thread.sleep(10000);
                //
                //            System.out.println("/n【添加定时任务】");
                //            QuartzManager.addJob(job_name,job,"0/5 * * * * ?");

            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class QuartzManager {
        private static SchedulerFactory sf = new StdSchedulerFactory();
        private static String JOB_GROUP_NAME = "group1";
        private static String TRIGGER_GROUP_NAME = "trigger1";


        /** *//**
         *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
         * @param jobName 任务名
         * @param job     任务
         * @param time    时间设置，参考quartz说明文档
         * @throws SchedulerException
         * @throws ParseException
         */
        public static void addJob(String jobName,Job job,String time)
                throws SchedulerException, ParseException{
            Scheduler sched = sf.getScheduler();
            JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());//任务名，任务组，任务执行类
            //触发器
            CronTrigger  trigger =
                    new CronTrigger(jobName, TRIGGER_GROUP_NAME);//触发器名,触发器组
            trigger.setCronExpression(time);//触发器时间设定
            sched.scheduleJob(jobDetail,trigger);
            //启动
            if(!sched.isShutdown())
                sched.start();
        }

        /** *//**
         * 添加一个定时任务
         * @param jobName 任务名
         * @param jobGroupName 任务组名
         * @param triggerName  触发器名
         * @param triggerGroupName 触发器组名
         * @param job     任务
         * @param time    时间设置，参考quartz说明文档
         * @throws SchedulerException
         * @throws ParseException
         */
        public static void addJob(String jobName,String jobGroupName,
                String triggerName,String triggerGroupName,
                Job job,String time)
                throws SchedulerException, ParseException{
            Scheduler sched = sf.getScheduler();
            JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());//任务名，任务组，任务执行类
            //触发器
            CronTrigger  trigger =
                    new CronTrigger(triggerName, triggerGroupName);//触发器名,触发器组
            trigger.setCronExpression(time);//触发器时间设定
            sched.scheduleJob(jobDetail,trigger);
            if(!sched.isShutdown())
                sched.start();
        }

        /** *//**
         * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
         * @param jobName
         * @param time
         * @throws SchedulerException
         * @throws ParseException
         */
        public static void modifyJobTime(String jobName,String time)
                throws SchedulerException, ParseException{
            Scheduler sched = sf.getScheduler();
            Trigger trigger =  sched.getTrigger(jobName,TRIGGER_GROUP_NAME);
            if(trigger != null){
                CronTrigger  ct = (CronTrigger)trigger;
                ct.setCronExpression(time);
                sched.resumeTrigger(jobName,TRIGGER_GROUP_NAME);
            }
        }

        /** *//**
         * 修改一个任务的触发时间
         * @param triggerName
         * @param triggerGroupName
         * @param time
         * @throws SchedulerException
         * @throws ParseException
         */
        public static void modifyJobTime(String triggerName,String triggerGroupName,
                String time)
                throws SchedulerException, ParseException{
            Scheduler sched = sf.getScheduler();
            Trigger trigger =  sched.getTrigger(triggerName,triggerGroupName);
            if(trigger != null){
                CronTrigger  ct = (CronTrigger)trigger;
                //修改时间
                ct.setCronExpression(time);
                //重启触发器
                sched.resumeTrigger(triggerName,triggerGroupName);
            }
        }

        /** *//**
         * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
         * @param jobName
         * @throws SchedulerException
         */
        public static void removeJob(String jobName)
                throws SchedulerException{
            Scheduler sched = sf.getScheduler();
            sched.pauseTrigger(jobName,TRIGGER_GROUP_NAME);//停止触发器
            sched.unscheduleJob(jobName,TRIGGER_GROUP_NAME);//移除触发器
            sched.deleteJob(jobName,JOB_GROUP_NAME);//删除任务
        }

        /** *//**
         * 移除一个任务
         * @param jobName
         * @param jobGroupName
         * @param triggerName
         * @param triggerGroupName
         * @throws SchedulerException
         */
        public static void removeJob(String jobName,String jobGroupName,
                String triggerName,String triggerGroupName)
                throws SchedulerException{
            Scheduler sched = sf.getScheduler();
            sched.pauseTrigger(triggerName,triggerGroupName);//停止触发器
            sched.unscheduleJob(triggerName,triggerGroupName);//移除触发器
            sched.deleteJob(jobName,jobGroupName);//删除任务
        }
    }
}
