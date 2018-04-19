const Notepad = require("./notepad");
const guessWords = require("../guessWords");
const Drawing = require("./drawing");
const Guess = require("./guess");
const Sheet = require("./sheet");   // needed for instanceof.. for some reason

class Game {
    constructor(players) {
        this.players = players;
        this.notepads = [];

        // fills inn notepads with unique routes
        for (let i = 0; i < this.players.length; i++) {
            const clone = this.players.slice(0);
            this.notepads.push(
                new Notepad(this.getWord(), clone)
            );
            this.rotatePlayers();
        }
    }

    nextStep() {
        if (this.notepads[0].hasNoSheets()) {   // first round
            this.firstHandleNotepads();
            return false;
        } else if (this.notepads[0].routeIsFinished()) {    // game over
            return true;
        } else {
            this.normalHandleNotepads();
            return false;
        }
    }

    getNotepads() {
        return this.notepads;
    }

    addNotepad(newNotepad) {
        this.notepads.forEach(notepad => {
            if (notpad.originalWord === newNotepad.originalWord) {
                this.notepads = this.notepads.filter(item => item !== notepad);
                break;
            }
        })
        this.notepads.push(notepad);
    }

    // private functions

    getWord() {
        return guessWords.pop();
    }

    rotatePlayers() {
        const tmp = this.players.shift();
        this.players.push(tmp);
    }

    normalHandleNotepads() {
        // partly fills inn and pushes new sheet to each notepad
        this.notepads.forEach(notepad => {
            const previousSheet = notepad.pop();
            if (previousSheet instanceof Drawing) {
                notepad.push(previousSheet);
                notepad.push(new Guess(
                    previousSheet.getObjectiveWord(),
                    previousSheet.getDrawing(),
                    notepad.nextOnRoute()
                ));
            } else if (previousSheet instanceof Guess) {
                notepad.push(previousSheet);
                notepad.push(new Drawing(
                    previousSheet.getAnswer(),
                    notepad.nextOnRoute()
                ))
            } else {
                throw (new Error("sheet that is neither Guess or Drawing in notepad"));
            }
        })
    }

    firstHandleNotepads() {
        this.notepads.forEach(notepad => {
            notepad.push(new Drawing(
                notepad.originalWord,
                notepad.nextOnRoute()
            ))
        })
    }
}

module.exports = Game;
