package com.sabo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author canbin.zhang@qq.com
 * @date 2022/6/11 13:22
 */
public class TwoPiecesTest {

    @Test
    public void minus() {
        assertEquals(1, TwoPieces.minus(2, 1));
    }
}