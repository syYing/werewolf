package com.lucien.werewolf;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class GameController {

    private Map<Integer, Room> roomMap = new HashMap<>();

    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public String createRoom(@RequestBody String input) {
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String, Object> map = jsonParser.parseMap(input);
        List<Integer> roleList = (ArrayList)map.get("roles");
        Room room = new Room(roleList);
        roomMap.put(room.getRoomId(), room);

        JSONObject res = new JSONObject();
        try {
            res.put("id", room.getRoomId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res.toString();
    }

    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public List<Integer> enterRoom(@RequestParam("roomId") int roomId) {
        List<Integer> roles = roomMap.get(roomId).getRoles();
        Collections.sort(roles);

        return roles;
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public int queryRole(@RequestParam("roomId") int roomId, @RequestParam("seatId") int seatId, @RequestParam("ownerKey") String ownerKey) {
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
