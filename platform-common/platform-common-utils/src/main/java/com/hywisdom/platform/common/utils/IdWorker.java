/**
 * twitter的分布式自增id生成算法
 * http://cailin.iteye.com/blog/2256390
 * http://cailin.iteye.com/blog/2256376
 */
package com.hywisdom.platform.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 第一位为未使用（实际上也可作为long的符号位），接下来的41位为毫秒级时间，然后5位datacenter标识位，</br>
 * 5位机器ID（并不算标识符，实际是为线程标识），然后12位该毫秒内的当前毫秒内的计数，加起来刚好64位，为一个Long型。
 * 0---0000000000 0000000000 0000000000 0000000000 0 --- 00000 ---00000 ---000000000000
 * @author wangxz
 *
 */
public class IdWorker {
     
    protected static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);
     
    //机器id
    private long workerId;
    
    //数据中心id
    private long datacenterId;
    private long sequence = 0L;
 
    private long twepoch = 1288834974657L;
 
    //机器标识位数
    private long workerIdBits = 5L;
    //数据中心标识位数
    private long datacenterIdBits = 5L;
    //机器ID最大值
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    //数据中心ID最大值
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    //毫秒内自增位
    private long sequenceBits = 12L;
    //机器ID偏左移12位
    private long workerIdShift = sequenceBits;
    //数据中心ID左移17位
    private long datacenterIdShift = sequenceBits + workerIdBits;
    //时间毫秒左移22位
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
 
    private long lastTimestamp = -1L;
 
    /**
     * @param workerId 机器id
     * @param datacenterId 数据中心id
     */
    public IdWorker(long workerId, long datacenterId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        LOG.info(String.format("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d", timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId));
    }
    
    
    public IdWorker(long workerId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
 
        this.workerId = workerId;
        this.datacenterId = 1;//默认只有一个数据中心
        LOG.info(String.format("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d", timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId));
    }
 
    public synchronized long nextId() {
        long timestamp = timeGen();
 
        if (timestamp < lastTimestamp) {
            LOG.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
 
        if (lastTimestamp == timestamp) {
        	//当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
            //当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
 
        lastTimestamp = timestamp;
        
        //ID偏移组合生成最终的ID，并返回ID   
 
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }
 
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
 
    protected long timeGen() {
        return System.currentTimeMillis();
    }
    
    public static void main(String[] args) {
    	//不指定数据中心
    	IdWorker iw1 = new IdWorker(1);
    	System.out.println(iw1.nextId());
  
    	
     	//指定数据中心
    	IdWorker iw2 = new IdWorker(1,1);
    	System.out.println(iw2.nextId());
	}
}
