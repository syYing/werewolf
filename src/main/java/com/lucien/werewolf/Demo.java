package com.lucien.werewolf;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Demo {

    private Map<Integer, Room> roomMap;

    public int createRoom(List<Integer> roles) {
        Room room = new Room(roles);
        roomMap.put(room.getRoomId(), room);

        return room.getRoomId();
    }

    public List<Integer> enterRoom(int roomId) {
        List<Integer> roles = roomMap.get(roomId).getRoles();
        Collections.sort(roles);

        return roles;
    }

    public int queryRole(int roomId, int seatId, String ownerKey) {
        Player[] players = roomMap.get(roomId).getPlayers();
        Player player = players[seatId - 1];

        if (player.getOwnerKey() == null) {
            player.setOwnerKey(ownerKey);
            return player.getRole();
        } else {
            if (player.getOwnerKey().equals(ownerKey)) {
                return player.getRole();
            } else {
                return 0;
            }
        }
    }
}
