package com.lucien.werewolf;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Demo {

    private Map<Integer, Room> roomMap;

    public int createRoom(List<Player> players) {
        Collections.shuffle(players);
        Room room = new Room(players);
        roomMap.put(room.getRoom_id(), room);

        return room.getRoom_id();
    }

    public List<Player> enterRoom(int room_id) {
        List<Player> players = roomMap.get(room_id).getPlayers();
        Collections.sort(players);

        return players;
    }

    public int queryRole(int room_id, int seat_id, String ownerKey) {
        List<Player> players = roomMap.get(room_id).getPlayers();
        Player player = players.get(seat_id - 1);

        if (player.getOwnerKey().equals(ownerKey)) {
            return player.getRole();
        } else if (player.getOwnerKey().equals("")) {
            player.setOwnerKey(ownerKey);
            return player.getRole();
        } else {
            return -1;
        }
    }
}
