package com.example.apidemo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created by lihong10 on 2017/8/9.
 * 一个循环计数器，每天从1开始计数，隔天重置为1。
 * 可以创建一个该类的全局对象，然后每次使用时候调用其get方法即可，可以保证线程安全性
 */
public class CircularCounter
{

    private static final AtomicReferenceFieldUpdater<CircularCounter, AtomicInteger> valueUpdater =
            AtomicReferenceFieldUpdater.newUpdater(CircularCounter.class, AtomicInteger.class, "value");
    //保证内存可见性
    private volatile String key;
    //保证内存可见性
    private volatile AtomicInteger value;
    private static final String DATE_PATTERN = "yyyy-MM-dd";


    public CircularCounter()
    {
        this.key = getCurrentDateString() ;
        this.value = new AtomicInteger(0);
    }


    /**
     * 获取计数器加1以后的值
     *
     * @return
     */
    public Integer addAndGet()
    {

        AtomicInteger oldValue = value;
        AtomicInteger newInteger = new AtomicInteger(0);

        int newVal = -1;
        String newDateStr = getCurrentDateString();
        //日期一致，计数器加1后返回
        if (isDateEquals(newDateStr))
        {
            newVal = add(1);
            return newVal;
        }

        //日期不一致，保证有一个线程重置技术器
        reSet(oldValue, newInteger, newDateStr);
        this.key = newDateStr;
        //重置后加1返回
        newVal = add(1);
        return newVal;
    }

    /**
     * 获取计数器的当前值
     *
     * @return
     */
    public Integer get()
    {
        return value.get();
    }

    /**
     * 判断当前日期与老的日期（也即key成员变量记录的值）是否一致
     *
     * @return
     */
    private boolean isDateEquals(String newDateStr)
    {
        String oldDateStr = key;
        if (!isBlank(oldDateStr) && oldDateStr.equals(newDateStr))
        {
            return true;
        }

        return false;
    }


    /**
     * 如果日期发生变化，重置计数器，也即将key设置为当前日期，并将value重置为0，重置后才能接着累加，
     */
    private void reSet(AtomicInteger oldValue, AtomicInteger newValue, String newDateStr)
    {
        if (valueUpdater.compareAndSet(this, oldValue, newValue))
        {
            System.out.println("线程" + Thread.currentThread().getName() + "发现日期发生变化");
        }
    }

    /**
     * 获取当前日期字符串
     *
     * @return
     */
    private String getCurrentDateString()
    {
        Date date = new Date();
        String newDateStr = new SimpleDateFormat(DATE_PATTERN).format(date);
        return newDateStr;
    }

    /**
     * 计数器的值加1。采用CAS保证线程安全性
     *
     * @param increment
     */
    private int add(int increment)
    {
        return value.addAndGet(increment);
    }

    public static boolean isBlank(CharSequence cs)
    {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0)
        {
            for (int i = 0; i < strLen; ++i)
            {
                if (!Character.isWhitespace(cs.charAt(i)))
                {
                    return false;
                }
            }

            return true;
        } else
        {
            return true;
        }
    }
}
