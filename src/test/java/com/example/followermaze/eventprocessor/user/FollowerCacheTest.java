package com.example.followermaze.eventprocessor.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FollowerCacheTest {
    private static UserId user1 = new UserId(10);
    private static UserId user2 = new UserId(20);
    private static UserId user3 = new UserId(30);

    @BeforeEach
    public void setUp(){
        FollowerCache.clearFollowerCache();
    }

    @Test
    void followUser() {
        FollowerCache.followUser(user1, user2);
        FollowerCache.followUser(user1, user3);

        assertEquals(FollowerCache.getFollowers(user1).size(), 2 , "FollowerCache size is wrong");
        assertEquals(FollowerCache.getFollowers(user1).containsAll(Arrays.asList(user2, user3)), true, "FollowerCache is wrong");
    }


    @Test
    void unfollowUser() {
        FollowerCache.followUser(user1, user2);
        assertEquals(FollowerCache.getFollowers(user1).size(), 1 , "FollowerCache size is wrong");

        FollowerCache.unfollowUser(user1, user2);
        assertEquals(FollowerCache.getFollowers(user1).isEmpty() ,true ,"FollowerCache size is wrong");

    }
}