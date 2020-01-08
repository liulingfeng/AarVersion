package com.lxs.mstaar.test;

/**
 * @author liuxiaoshuai
 * @date 2020-01-03
 * @desc
 * @email liulingfeng@mistong.com
 */
public class Host2 {
    private Host2(){

    }
    private static class Handler{
        private static Host2 instance = new Host2();
    }

    public static Host2 getInstance() {
        return Handler.instance;
    }
}
