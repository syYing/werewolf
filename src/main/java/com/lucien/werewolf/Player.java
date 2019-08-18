package com.lucien.werewolf;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Player implements Comparable<Player> {

    private int role;
    private String ownerKey;

    @Override
    public int compareTo(Player o) {
        return this.role - o.getRole();
    }
}
