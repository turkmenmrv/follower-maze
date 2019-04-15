package com.example.followermaze.eventprocessor.user;

public class UserId {
    private final int key;

    public UserId(int key){
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public boolean equals(Object o){
        return ((UserId)o).key ==this.key;
    }

    public int hashCode(){
        return key;
    }
}
