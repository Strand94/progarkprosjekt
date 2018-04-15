const GameController = require('./controllers/gameController');
const LobbyController = require('./controllers/lobbyController');

const lobbyController = new LobbyController();

lobbyController.createLobby("per", "1337");
lobbyController.playerDisconnected("1337");