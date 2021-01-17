package com.nespot2.hellobatch.config;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2021/01/17
 **/
public class UtilTest {

    @Test
    void test() {
        int a = 5 + 5;

        assertEquals(10, a);

    }

    @Test
    void test2() {
        int a = 20;

        assertNotEquals(25, a);
    }

    @Test
    void test3(){
        assertTrue(Arrays.asList().isEmpty());
    }


}
