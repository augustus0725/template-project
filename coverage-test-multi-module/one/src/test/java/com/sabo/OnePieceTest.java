package com.sabo;

import static org.junit.Assert.*;

/**
 * @author zhangcanbin@hongwangweb.com
 * @date 2022/6/11 13:20
 */
public class OnePieceTest {

    @org.junit.Test
    public void add() {
        assertEquals(2, OnePiece.add(1, 1));
    }
}