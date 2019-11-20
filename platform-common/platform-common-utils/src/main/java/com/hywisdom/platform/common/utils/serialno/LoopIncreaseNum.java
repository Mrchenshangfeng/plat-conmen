package com.hywisdom.platform.common.utils.serialno;

/**
 * 〈功能简述〉<br>
 * 〈循环递增数.原子数每次+1递增，到达最大值时重新从初始值开始递增。
 * 同一实例线程安全，不同实例线程不安全〉
 *
 * @author wangxz
 * @create 2018/11/19
 * @since 1.0.0
 */
public class LoopIncreaseNum extends BaseIncreaseNum {

    private long maxSeq;

    /**
     * 构造函数.初始值默认1
     *
     * @param maxNum 最大值
     */
    public LoopIncreaseNum(long maxNum) {
        super();
        this.maxSeq = maxNum;
    }

    /**
     * 构造函数
     *
     * @param maxNum 最大值
     * @param initNum 初始值
     */
    public LoopIncreaseNum(long maxNum, int initNum) {
        super(initNum);
        this.maxSeq = maxNum;
    }

    /**
     * 下一个递增值
     *
     * @return
     */
    @Override
    public long nextNum() {
        long next = sequence.getAndIncrement();
        if (next > maxSeq) {
            sequence.set(initSeq);
        }
        return next;
    }

}
