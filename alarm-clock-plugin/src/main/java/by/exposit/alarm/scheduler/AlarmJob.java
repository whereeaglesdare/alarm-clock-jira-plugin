package by.exposit.alarm.scheduler;

import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.scheduler.*;
import com.atlassian.scheduler.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@ExportAsService({ AlarmJob.class })
@Component
public class AlarmJob implements JobRunner, InitializingBean, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(AlarmJob.class);

    private static final JobRunnerKey JOB_RUNNER_KEY =
            JobRunnerKey.of(AlarmJob.class.getName());

    private static final String CRON_EXPRESSION = "0 * * ? * *";

    final JobConfig jobConfig =
            JobConfig.forJobRunnerKey(JOB_RUNNER_KEY).withRunMode
                    (RunMode.RUN_LOCALLY).withSchedule(Schedule.forCronExpression(CRON_EXPRESSION));

    private static final JobId JOB_ID =
            JobId.of(AlarmJob.class.getName());

    @ComponentImport
    private final SchedulerService scheduler;

    @Autowired
    public AlarmJob(SchedulerService scheduler) {
        logger.info("AlarmJob schedule initialization.");
        this.scheduler = scheduler;
    }

    @Override
    public JobRunnerResponse runJob(JobRunnerRequest request) {
        logger.info("AlarmClock scheduled job running " + request.getStartTime());
        return JobRunnerResponse.success();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        scheduler.registerJobRunner(JOB_RUNNER_KEY, this);
        try {
            scheduler.scheduleJob(JOB_ID, jobConfig);
        } catch (SchedulerServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        scheduler.unscheduleJob(JOB_ID);
    }
}
