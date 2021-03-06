package com.hywisdom.platform.common.utils.serialno;

import com.hywisdom.platform.common.utils.DateHelper;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 〈功能简述〉<br>
 * 〈常用序列号生成器〉
 *
 * @author wangxz
 * @create 2018/11/19
 * @since 1.0.0
 */
public final class SerialNoUtils {

    private static ConcurrentMap<String, TimeUnitIncreaseNum> minuteSeqMap = new ConcurrentHashMap<>();
    private static ConcurrentMap<String, TimeUnitIncreaseNum> daySeqMap = new ConcurrentHashMap<>();


    /**
     * 编号生成,时间戳+随机数字
     *
     * @param randomCount 随机数字个数
     * @return
     */
    public static String millisAndRandomNo(int randomCount) {
        return System.currentTimeMillis() + RandomStringUtils.randomNumeric(randomCount);
    }

    /**
     * 编号生成,时间戳(yyMMddHHmmssSSS)+随机数字
     *
     * @param randomCount 随机数字个数
     * @return
     */
    public static String timeAndRandomNo(int randomCount) {
        return DateHelper.formatNow("yyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(randomCount);
    }

    /**
     * 编号生成,规则: 业务标识+时间(yyMMddHHmm)+随机数(1位)+1分钟内流水号
     *
     * @param prefix 业务标识,区分业务场景,相同标识使用同一流水号生成实例,分布式场景建议同时加入机器id
     * @return
     */
    public static String minuteAndSequence(String prefix) {
        TimeUnitIncreaseNum tin = getMinuteSequencer(prefix);
        return prefix + DateHelper.formatNow("yyMMddHHmm") + RandomStringUtils.randomNumeric(1) + tin.nextNum();
    }

    /**
     * 编号生成,规则: 业务标识+时间(yyMMdd)+随机数(2位)+1天内流水号
     *
     * @param prefix 业务标识,区分业务场景,相同标识使用同一流水号生成实例,分布式场景建议同时加入机器id
     * @return
     */
    public static String dayAndSequence(String prefix) {
        TimeUnitIncreaseNum tin = getDaySequencer(prefix);
        return prefix + DateHelper.formatNow("yyMMdd") + RandomStringUtils.randomNumeric(2) + tin.nextNum();
    }

    /**
     * 生成一个ObjectID字符串
     *
     * @return
     */
    public static String objectId() {
        ObjectId idGen = new ObjectId();
        return idGen.toHexString();
    }

    private static TimeUnitIncreaseNum getMinuteSequencer(String prefix) {
        TimeUnitIncreaseNum tin;
        if (minuteSeqMap.containsKey(prefix)) {
            tin = minuteSeqMap.get(prefix);
        } else {
            tin = new TimeUnitIncreaseNum(TimeUnit.MINUTES);
            minuteSeqMap.put(prefix, tin);
        }
        return tin;
    }

    private static TimeUnitIncreaseNum getDaySequencer(String prefix) {
        TimeUnitIncreaseNum tin;
        if (daySeqMap.containsKey(prefix)) {
            tin = daySeqMap.get(prefix);
        } else {
            tin = new TimeUnitIncreaseNum(TimeUnit.DAYS);
            daySeqMap.put(prefix, tin);
        }
        return tin;
    }

//    public static void main(String[] args) {
//        System.out.println(dayAndSequence("hy"));
//        System.out.println(dayAndSequence("hy"));
//        System.out.println(dayAndSequence("hy"));
//        System.out.println(dayAndSequence("hy"));
//        System.out.println(minuteAndSequence("wxz"));
//        System.out.println(minuteAndSequence("wxz"));
//        System.out.println(minuteAndSequence("wxz"));
//    }
}
