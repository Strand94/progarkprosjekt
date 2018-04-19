const LobbyController = require('./controllers/lobbyController');
const Player = require("./models/player");
const Notepad = require("./models/notepad");


const lobbyController = new LobbyController();
lobbyController.createLobby("per");

const players = [];
for (let i = 0; i < 7; i++) {
    let player = new Player(i.toString(), i.toString());
    players.push(player);
    lobbyController.joinLobby(0, player);
}

/*

lobbyController.startGame(0);
const gameController = lobbyController.lobbies[0].gameController;

for (let i = 0; i < 7; i++) {
    gameController.recieveNotepad(new Notepad(i.toString(), players[i]));
}

*/

lobbyController.communicator.notifyPlayer(1111, "test");
