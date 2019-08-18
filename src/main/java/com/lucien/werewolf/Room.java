package com.lucien.werewolf;

import lombok.Data;

import java.util.List;

@Data
public class Room {

    private static int id = 1;

    private int room_id;
    private List<Player> players;

    public Room(List<Player> players) {
        this.room_id = id++;
        this.players = players;
    }
}
