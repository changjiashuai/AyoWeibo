package org.ayo.weibo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化器，所有程序启动时要做的初始化工作，请放在这里
 *
 * 为什么呢
 *
 * 现在不建议在Application里做所有初始化工作，而是放在SplashActivity
 *
 * 注意，初始化不完成，SplashActivity不结束，一直完不成初始化，大不了就一直不进应用
 *
 * 可以允许的失败：可能有些初始化过程允许失败
 *
 * 你还可以把初始化过程分为几步，每一步都对结果进行监听，并作出单独处理
 *
 * 剩下的问题就是：
 * 1 初始化放在SpashActivity的哪里，才能保证界面瞬间弹出？
 * 2 对于SpashActivity需要用到的库，也必须做完初始化才能进行，那SpashActivity界面怎么设计呢？
 * 3 有些初始化是比较耗时的，比如视频，IM，Logger等
 *
 *
 */
public class Initializer {

    /**
     * 一个初始化任务，如极光的初始化是一个step，sd卡的初始化是一个step，http的初始化也是一个step
     */
    public interface Step{

        /** 做一些严肃的工作，也就是正事 */
        boolean doSeriousWork();

        /** 这一步的名字 */
        String getName();

        /** 初始化失败，给用户的提示 */
        String getNotify();

        /** 如果这一步初始化失败，是否可以继续，对于一些不影响系统使用的功能，可以接受失败 */
        boolean acceptFail();

    }

    public interface StepListner{
        /**
         * suffering是说的谁？说的用户，你在初始化时，遭罪的是用户
         * @param step  当前的任务
         * @param isSuccess  当前的任务是否成功了
         * @param currentStep 当前是第几个任务，从1开始
         * @param total  一共几个任务
         * @return true表示可以继续初始化，false表示某一步出错了，并且错误不可接受，app已经没法用了，没必要继续了
         */
        boolean onSuffering(Step step, boolean isSuccess, int currentStep, int total);
    }

    private List<Step> steps = new ArrayList<Step>();
    private StepListner stepListner;

    private Initializer(){

    }

    public static Initializer initailizer(){
        return new Initializer();
    }

    public Initializer addStep(Step step){
        steps.add(step);
        return this;
    }

    public Initializer setStepListener(StepListner stepListener){
        this.stepListner = stepListener;
        return this;
    }

    /**
     * 开始初始化
     */
    public void suffer(){
        int total = steps.size();
        if(total == 0){
            if(this.stepListner != null) this.stepListner.onSuffering(null, true, 0, 0);
            return;
        }

        int current = 0;
        for(Step step: steps){
            current += 1;
            Log.i("initialize", "初始化--" + step.getName());
            boolean isSuccess = step.doSeriousWork();
            Log.i("initialize", "初始化--" + isSuccess);
            if(this.stepListner != null) {
                if(!this.stepListner.onSuffering(step, isSuccess, current, total)){
                    break;
                }
            }
        }
    }

}
