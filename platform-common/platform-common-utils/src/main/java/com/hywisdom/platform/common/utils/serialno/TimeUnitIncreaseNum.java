package com.hywisdom.platform.common.utils.serialno;

import com.hywisdom.platform.common.utils.DateHelper;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * 〈功能简述〉<br>
 * 〈单位时间内循环自增数〉
 *支持每天、每小时、每分、每秒重置自增 . 比如：new TimeUnitIncreaseNum(TimeUnit.DAYS)超过每天23:59:59后重置
 * @author wangxz
 * @create 2018/11/19
 * @since 1.0.0
 */
public final class TimeUnitIncreaseNum extends BaseIncreaseNum {
    private TimeUnit timeUnit;

    /**
     * 构造函数
     *
     * @param timeUnit 时间单元
     */
    public TimeUnitIncreaseNum(TimeUnit timeUnit) {
        super();
        this.timeUnit = timeUnit;
        isSupportUnit();
        setNextTimestamp();
    }

    /**
     * 构造函数
     *
     * @param timeUnit 时间单元
     * @param initNum 初始值
     */
    public TimeUnitIncreaseNum(TimeUnit timeUnit, int initNum) {
        super(initNum);
        this.timeUnit = timeUnit;
        isSupportUnit();
        setNextTimestamp();
    }

    @Override
    public long nextNum() {
        long curTime = System.currentTimeMillis();
        // System.out.println(curTime);
        // System.out.println(lastTimestamp.get());
        if (curTime > lastTimestamp.get()) {
            sequence.set(initSeq);
            setNextTimestamp();
            // System.out.println("reset..");
        }
        return sequence.getAndIncrement();
    }

    private void setNextTimestamp() {
        Calendar calendar = Calendar.getInstance();
        if (timeUnit == TimeUnit.DAYS) {
            lastTimestamp.set(DateHelper.getEndTimeOfDay(calendar.getTime()).getTime());
        } else if (timeUnit == TimeUnit.HOURS) {
            lastTimestamp.set(getEndTimeOfHour(calendar));
        } else if (timeUnit == TimeUnit.MINUTES) {
            lastTimestamp.set(getEndTimeOfMiunte(calendar));
        } else if (timeUnit == TimeUnit.SECONDS) {
            lastTimestamp.set(getEndTimeOfSecond(calendar));
        }
    }

    private void isSupportUnit() {
        if (timeUnit != TimeUnit.DAYS && timeUnit != TimeUnit.HOURS && timeUnit != TimeUnit.MINUTES
                && timeUnit != TimeUnit.SECONDS) {
            throw new IllegalArgumentException("只支持\"天->秒\"时间单位");
        }
    }

    private long getEndTimeOfHour(Calendar calendar) {
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    private long getEndTimeOfMiunte(Calendar calendar) {
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    private long getEndTimeOfSecond(Calendar calendar) {
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
}
