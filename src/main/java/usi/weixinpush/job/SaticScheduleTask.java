package usi.weixinpush.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static usi.weixinpush.service.WeiXinTemplateService.pushWeiXinTemplate;

@Configuration
@EnableScheduling
public class SaticScheduleTask {
    @Autowired
    private ApplicationContext appContext;
    public void initiateShutdown(int returnCode){
        SpringApplication.exit(appContext, () -> returnCode);
    }

    @Scheduled(cron = "0/20 * * * * ?")
    private void configureTasks() {
        pushWeiXinTemplate();
    }
}
