package usi.weixinpush.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static usi.weixinpush.service.WeiXinTemplateService.pushWeiXinTemplate;

@Configuration
@EnableScheduling
public class SaticScheduleTask {

    @Scheduled(cron = "0/30 0 8 * * ?")
    private void configureTasks() {
        pushWeiXinTemplate();
    }
}
