package com.lxs.mstaar;

/**
 * @author liuxiaoshuai
 * @date 2019-12-23
 * @desc
 * @email liulingfeng@mistong.com
 */
public class Student {
    public synchronized void test(){
        //锁实例
    }

    public synchronized static void test1(){
        //锁类对象
    }

    public void test2(){
        //锁类对象
        synchronized (Student.class){
            synchronized (this){
                //锁实例
            }
        }
    }
}
