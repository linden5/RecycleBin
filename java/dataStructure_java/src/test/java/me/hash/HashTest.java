package me.hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Created by lyx on 2016/9/8.
 */
public class HashTest {
    @Test
    public void primeTest() {
        assertEquals(SeparateChainingHashTable.montgomery(10, 2, 3), 1);
        assertEquals(SeparateChainingHashTable.montgomery(2, 10, 2), 0);
        assertFalse(SeparateChainingHashTable.isPrime(1));
        assertTrue(SeparateChainingHashTable.isPrime(3));
        assertFalse(SeparateChainingHashTable.isPrime(100));

        assertEquals(SeparateChainingHashTable.nextPrime(2), 3);
        assertEquals(SeparateChainingHashTable.nextPrime(90), 97);
    }
}
