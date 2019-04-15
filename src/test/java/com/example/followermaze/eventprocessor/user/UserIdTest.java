package com.example.followermaze.eventprocessor.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {
    private static final int key1 = 1;
    private static final int key2 = 2;
    private final UserId id1_val1 = new UserId(key1);
    private final UserId id2_val1 = new UserId(key1);
    private final UserId id3_val2 = new UserId(key2);


    @Test
    void getKey() {
        assertEquals(id1_val1.getKey(), key1, "Key is wrong");
    }

    @Test
    void userIdEqual() {
        assertEquals(id1_val1, id2_val1, "UserIds should be equal");
        assertNotEquals(id1_val1, id3_val2, "UserIds should not be equal");
    }

    @Test
    void userIdHashCode() {
        assertEquals(id1_val1.hashCode(), id2_val1.hashCode(), "Hash codes should be same");
        assertNotEquals(id1_val1.hashCode(), id3_val2.hashCode(), "Hash codes should be different");

    }
}