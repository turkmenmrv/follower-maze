package com.example.followermaze.eventprocessor.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FollowerCache {
    private static final ConcurrentMap<UserId, List<UserId>> followersMap;

    static{
        followersMap = new ConcurrentHashMap<>();
    }

    public static void followUser(UserId followed, UserId follower){
        if(followersMap.containsKey(followed)){
            List<UserId> list = followersMap.get(followed);
            list.add(follower);
        }
        else{
            List<UserId> list = new ArrayList<>();
            list.add(follower);
            followersMap.put(followed, list);
        }
    }

    public static void unfollowUser(UserId followed, UserId follower){
        if(followersMap.containsKey(followed)){
            List<UserId> list = followersMap.get(followed);
            list.remove(follower);
        }
    }

    public static List<UserId> getFollowers(UserId userId){
        return followersMap.getOrDefault(userId, Collections.emptyList());
    }

    public static void clearFollowerCache(){
        followersMap.clear();
    }
}
