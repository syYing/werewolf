package com.lucien.werewolf;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class GameController {

    private Map<Integer, Room> roomMap = new HashMap<>();

    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public String createRoom(@RequestBody String input) throws HttpException {
        if (input == null) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid params");
        }

        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String, Object> map = jsonParser.parseMap(input);

        if (!(map.get("roles") instanceof ArrayList)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid params");
        }

        List<Integer> roleList = (ArrayList)map.get("roles");

        if (roleList.size() == 0) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "At least one role must exist");
        }

        Room room = new Room(roleList);
        roomMap.put(room.getRoomId(), room);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                roomMap.remove(room.getRoomId());
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 3600000);

        JSONObject res = new JSONObject();
        try {
            res.put("roomId", room.getRoomId());
            res.put("roles", room.getRoles());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res.toString();
    }

    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public String enterRoom(@RequestParam("roomId") int roomId) throws HttpException{
        if (!roomMap.containsKey(roomId)) {
            throw new HttpException(HttpStatus.NOT_FOUND, "The room does not exist");
        }

        JSONObject res = new JSONObject();
        try {
            res.put("roomId", roomId);
            res.put("roles", roomMap.get(roomId).getRoles());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res.toString();
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public int queryRole(@RequestParam("roomId") int roomId, @RequestParam("seatId") int seatId, @RequestParam("ownerKey") String ownerKey) throws HttpException {
        if (!roomMap.containsKey(roomId)) {
            throw new HttpException(HttpStatus.NOT_FOUND, "The room does not exist");
        }

        Player[] players = roomMap.get(roomId).getPlayers();

        if (seatId <= 0 || seatId > players.length) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid seat");
        }

        Player player = players[seatId - 1];

        if (player.getOwnerKey() == null) {
            player.setOwnerKey(ownerKey);
            return player.getRole();
        } else {
            if (player.getOwnerKey().equals(ownerKey)) {
                return player.getRole();
            } else {
                throw new HttpException(HttpStatus.CONFLICT, "The seat has been taken");
            }
        }
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity handleHttpException(HttpException e) {
        return new ResponseEntity(e.getMessage(), e.getStatus());
    }
}
