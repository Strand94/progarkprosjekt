package com.sketchy.game.Controllers;

import com.sketchy.game.Models.Lobby;
import com.sketchy.game.Models.Notepad;
import com.sketchy.game.Models.Player;
import com.sketchy.game.Models.Sheet;
import com.sketchy.game.SketchyGame;
import com.sketchy.game.Views.DrawView;
import com.sketchy.game.Views.GuessView;
import com.sketchy.game.Views.JoinView;
import com.sketchy.game.Views.LobbyView;
import com.sketchy.game.Views.LoginView;
import com.sketchy.game.Views.View;
import com.sketchy.game.communicator.Communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClientController {

    private Player player;
    private View view;
    private Communicator communicator;
    private SketchyGame game;
    private Lobby lobby;

    public ClientController(SketchyGame game) {
        this.game = game;
        this.communicator = new Communicator(this);

        showLogin();
    }

    //=========== GAME ==============\\
    public int getPlayerCount() {
        System.out.println("getPlayerCount() -> " + lobby.playerCount);
        return lobby.playerCount;
    }

    public void setPlayerCount(int playerCount) {
        lobby.playerCount = playerCount;
        System.out.println("setPlayerCount(%d)" + playerCount);
    }

    public void startGame() {
        System.out.println("clientController.startGame()");
        //TODO: Check if it's okay to change view
        showDraw();
    }

    public void endGame() {
    }

    public void beginRound(Sheet sheet) {
    }

    public void beginRound(Notepad notepad) {
    }

    public SketchyGame getGame() {
        return game;
    }


    //=========== END GAME ==============\\


    //=========== LOBBY ==============\\

    public void createLobby(String playerName) {
        System.out.format("clientController.createLobby('%s')\n", playerName);
        communicator.createLobby(playerName);
        lobby = Lobby.LOADING;

        //Todo: Check if it's OK to change view
        showLobby();
    }

    public void joinLobby(int lobbyId, String playerName) {
        System.out.format("clientController.joinLobby(%s, '%s')\n", lobbyId, playerName);
        communicator.joinLobby(lobbyId, playerName);

        //Todo: Check if it's OK to change view
        showLobby();
    }

    public void updateLobby(int lobbyId, List<Player> players) throws Exception {
        // TODO: adjust to Players (What does that mean?)

        if (lobby == Lobby.LOADING) lobby = new Lobby(lobbyId);
        else if (lobbyId != lobby.lobbyId)
            throw new Exception("Server and client disagree about lobby id");

        System.out.format("clientController.updateLobby(%s, players(%d))\n", lobbyId, players.size());
        setPlayerCount(players.size());

        List<String> names = new ArrayList<>();
        for (Player player : players) {
            names.add(player.getName());
        }

        if (view instanceof LobbyView) {
            ((LobbyView) view).updatePlayerList(names);
            ((LobbyView) view).setLobbyId(lobbyId);
        }

    }
    //=========== END LOBBY ==============\\


    //=========== PLAYER ==============\\
    public Player getPlayer() {
        System.out.println("clientController.getPlayer() -> " + player.getName());
        return player;
    }

    public void setPlayer(Player player) {
        System.out.println(String.format("clientController.setPlayer('%s')", player.getName()));
        this.player = player;
    }
    //=========== END PLAYER ==============\\


    //=========== VIEW ==============\\
    private void setView(View view) {
        game.setScreen(view);

        if (this.view != null) {
            this.view.dispose();
        }
        this.view = view;

        System.out.println("*SetView:" + view);
    }
    //=========== END VIEW ==============\\

    public void showLogin() {
        setView(new LoginView(this));
    }

    public void showJoin() {
        setView(new JoinView(this));
    }

    public void showLobby() {
        setView(new LobbyView(this));
    }

    public void showGuess(Stack<DrawView.Dots> drawing) {
        setView(new GuessView(this, drawing));
    }

    private void showDraw() {
        setView(new DrawView(this));
    }
}
