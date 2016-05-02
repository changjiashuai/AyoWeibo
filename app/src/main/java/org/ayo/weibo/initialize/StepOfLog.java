package org.ayo.weibo.initialize;

import org.ayo.Ayo;
import org.ayo.jlog.JLog;
import org.ayo.jlog.constant.JLogLevel;
import org.ayo.jlog.constant.JLogSegment;
import org.ayo.weibo.App;
import org.ayo.weibo.Config;
import org.ayo.weibo.Initializer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class StepOfLog implements Initializer.Step {
    @Override
    public boolean doSeriousWork() {
        List<JLogLevel> logLevels = new ArrayList<>(); //哪些级别的日志可以输出到文件中
        logLevels.add(JLogLevel.ERROR);
        logLevels.add(JLogLevel.JSON);

        File logDir = new File(Ayo.ROOT + "log");
        if(!logDir.exists()){
            logDir.mkdirs();
        }

        JLog.init(App.app)
                .setDebug(Config.Build.OPEN_DEBUG_LOG)
                .writeToFile(false)
                .setLogDir(Ayo.ROOT + "log")
                .setLogPrefix("log_")
                .setLogSegment(JLogSegment.ONE_HOUR)  //日志按照时间切片写入到不同的文件中
                .setLogLevelsForFile(logLevels)
                .setCharset("UTF-8");
        return true;
    }

    @Override
    public String getName() {
        return "日志功能";
    }

    @Override
    public String getNotify() {
        return "日志功能";
    }

    @Override
    public boolean acceptFail() {
        return true;
    }
}
