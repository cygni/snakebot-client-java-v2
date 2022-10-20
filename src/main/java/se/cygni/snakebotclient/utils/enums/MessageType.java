package se.cygni.snakebotclient.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

public enum MessageType {
    // Exceptions
    @JsonProperty("se.cygni.snake.api.exception.InvalidMessage")
    INVALID_MESSAGE,
    @JsonProperty("se.cygni.snake.api.exception.InvalidPlayerName")
    INVALID_PLAYER_NAME,
    @JsonProperty("se.cygni.snake.api.exception.NoActiveTournament")
    NO_ACTIVE_TOURNAMENT,
    @JsonProperty("se.cygni.snake.api.exception.InvalidArenaName")
    INVALID_ARENA_NAME,
    @JsonProperty("se.cygni.snake.api.exception.ArenaIsFull")
    ARENA_IS_FULL,

    // Responses
    @JsonProperty("se.cygni.snake.api.response.HeartBeatResponse")
    HEARTBEAT_RESPONSE,
    @JsonProperty("se.cygni.snake.api.response.PlayerRegistered")
    PLAYER_REGISTERED,

    // Events
    @JsonProperty("se.cygni.snake.api.event.GameLinkEvent")
    GAME_LINK,

    @JsonProperty("se.cygni.snake.api.event.GameStartingEvent")
    GAME_STARTING,
    @JsonProperty("se.cygni.snake.api.event.MapUpdateEvent")
    MAP_UPDATE,
    @JsonProperty("se.cygni.snake.api.event.SnakeDeadEvent")
    SNAKE_DEAD,
    @JsonProperty("se.cygni.snake.api.event.GameResultEvent")
    GAME_RESULT,
    @JsonProperty("se.cygni.snake.api.event.GameEndedEvent")
    GAME_ENDED,
    @JsonProperty("se.cygni.snake.api.event.TournamentEndedEvent")
    TOURNAMENT_ENDED,

    // Requests
    @JsonProperty("se.cygni.snake.api.request.ClientInfo")
    CLIENT_INFO,
    @JsonProperty("se.cygni.snake.api.request.StartGame")
    START_GAME,
    @JsonProperty("se.cygni.snake.api.request.RegisterPlayer")
    REGISTER_PLAYER,
    @JsonProperty("se.cygni.snake.api.request.RegisterMove")
    REGISTER_MOVE,
    @JsonProperty("se.cygni.snake.api.request.HeartBeatRequest")
    HEARTBEAT_REQUEST;
    
}
