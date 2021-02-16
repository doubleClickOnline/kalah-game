//package org.kalah.game.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class GamesServiceCreateTest {
//
//    @Value( "${server.address}" )
//    private String host;
//    @Value( "${server.port}" )
//    private String port;
//    @Autowired
//    private GamesService service;
//
//    @Test
//    public void testCreateGame() {
//        // setup
//        var gameId = 123L;
//        // act
//        var gameDTO = service.createGame(gameId);
//        // assert
//        Assertions.assertEquals(gameId, Long.valueOf(gameDTO.getId()));
//        Assertions.assertEquals(String.format("http://%s:%s/games/%d", host, port, gameId), gameDTO.getURL());
//    }
//}
