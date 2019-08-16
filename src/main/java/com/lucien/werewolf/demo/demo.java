package com.lucien.werewolf.demo;

import java.util.List;
import java.util.Map;

public class demo {

    private static int room_id = 1;
    private Map<Integer, List<Integer>> roomMap;

    public int createRoom(List<Integer> roles) {
        roomMap.put(room_id, roles);

        return room_id++;
    }

    public List<Integer> enterRoom(int room_id) {
        return roomMap.get(room_id);
    }

    public int queryRole(int room_id, int seat_id) {
        List<Integer> roles = roomMap.get(room_id);

        return roles.get(seat_id - 1);
    }
}
