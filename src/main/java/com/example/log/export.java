package com.example.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class export {

    private static final Logger log = LoggerFactory.getLogger(export.class);

    @Scheduled(cron = "0 0/1 * * * ?")
    public void minuteExport(){
        log.info("每分钟执行一次的任务：" + getDate());
    }

    @Scheduled(fixedRate = 5000)
    public void fiveSecondExport(){
        log.info("每5秒执行一次：" + getDate());
    }

    @Scheduled(cron = "0/2 * * * * ?")
    public void twoSecondExport(){
        log.info("每2秒执行一次：" + getDate());
    }

    @Scheduled(cron = "0 55 14 ? * *")
    public void regularTimeExport(){
        log.info("每天上午14点55分执行：" + getDate());
    }

    private String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
