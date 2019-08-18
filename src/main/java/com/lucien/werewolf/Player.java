package com.lucien.werewolf;

import lombok.Data;

@Data
public class Player implements Comparable<Player> {

    private int role;
    private String ownerKey;

    public Player(int role) {
        this.role = role;
    }

    @Override
    public int compareTo(Player o) {
        return this.role - o.getRole();
    }
}
