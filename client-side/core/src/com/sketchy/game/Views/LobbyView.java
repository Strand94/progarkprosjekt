package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.SketchyGame;

import java.util.Stack;


public class LobbyView extends View {
    final SketchyGame game;

    TextButton startGame;
    float remaining = 5;
    boolean startGame_r = false;
    int playerCount = 0;
    Label numberOfPlayers;
    Table buttonTable;


    public LobbyView(SketchyGame game) {
        this.game = game;

        // Hardcoded numbers, should be given by serverside
        String lobbyID = "1337";

        Label gameidLabel = new Label("LobbyID:"+" "+lobbyID, uiSkin);
        startGame = new TextButton("Start Game", uiSkin);

        // Todo: obtain from Model
        Stack<String> players = new Stack<String>();
        players.add("some_dude");
        players.add("random");
        players.add("TDT4240slayer");
        playerCount = playerCount+players.size();
        numberOfPlayers = new Label(playerCount+"/8", uiSkin);

        table.add(gameidLabel);
        table.row();

        for (String player : players) {
            addPerson(player);
        }
        buttonTable = new Table();
        buttonTable.setPosition(screenWidth/2, screenHeight*0.1f);
        table.setFillParent(true);
        stage.addActor(buttonTable);

        buttonTable.add(startGame);
        buttonTable.row();
        buttonTable.add(numberOfPlayers);

        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Start game");
                startGameCounter();
            }
        });
    }

    private void addPerson(String name){
        Label playerName = new Label(name, uiSkin);
        table.add(playerName);
        table.row();
    }

    private void startGameCounter(){
        startGame_r = true;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(246.0f/256, 195.0f/256, 42.0f/256, 1);


        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            addPerson("foo");
            playerCount = playerCount+1;
            numberOfPlayers.setText(playerCount+"/8");
        }

        // Countdown. Todo: Move to startGameCounter
        if (remaining > 0.1 && startGame_r == true) {
            float deltaTime = Gdx.graphics.getDeltaTime();
            remaining -= deltaTime;
            startGame.setText(String.format("Start in %.0fs", remaining));
            game.clientController.setView(new DrawView(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}