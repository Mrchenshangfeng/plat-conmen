package com.hywisdom.platform.common.utils.serialno;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 〈功能简述〉<br>
 * 〈序列号生成基础规则〉
 *
 * @author wangxz
 * @create 2018/11/19
 * @since 1.0.0
 */
public class BaseIncreaseNum {

    protected long initSeq = 1L;
    protected AtomicLong sequence;
    protected AtomicLong lastTimestamp = new AtomicLong(0L);

    public BaseIncreaseNum() {
        sequence = new AtomicLong(initSeq);
    }

    public BaseIncreaseNum(long initSeq) {
        this.initSeq = initSeq;
        sequence = new AtomicLong(initSeq);
    }

    public long nextNum() {
        return sequence.getAndIncrement();
    }
}
