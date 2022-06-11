package com.sabo.cov;

import static org.junit.Assert.*;

/**
 * @author zhangcanbin@hongwangweb.com
 * @date 2022/6/11 11:30
 */
public class OnePieceTest {

    @org.junit.Test
    public void sum() {
        assertEquals(2, OnePiece.sum(1, 1));
    }
}