package com.lxs.mstaar.test;

/**
 * @author liuxiaoshuai
 * @date 2020-01-03
 * @desc
 * @email liulingfeng@mistong.com
 */
public class Host1 {
    private static Host1 host1 = new Host1();

    private Host1() {

    }

    public static Host1 getInstance() {
        return host1;
    }
}
