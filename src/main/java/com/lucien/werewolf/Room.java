package com.lucien.werewolf;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class Room {

    private static int id = 1;

    private int roomId;
    private List<Integer> roles;
    private Player[] players;

    public Room(List<Integer> roles) {
        Collections.shuffle(roles);

        this.roomId = id++;
        this.roles = roles;
        this.players = new Player[roles.size()];

        for (int i = 0; i < roles.size(); i++) {
            Player player = new Player(roles.get(i));
            players[i] = player;
        }
    }
}
