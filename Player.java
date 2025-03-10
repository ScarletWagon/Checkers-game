import java.util.ArrayList;
/*
* Names: Gurpreet Singh
* netID: gsingh38,
* G#: 01437947
* Lecture section: 201
* Lab section: 001
*/
public abstract class Player {
    public abstract MiniCheckers chooseMove(MiniCheckers game);

    public double boardValue(MiniCheckers game) {
        if (game.checkWin(this)) {
            return 1.0;
        } else if (game.checkLose(this)) {
            return -1.0;
        } else {
            return 0.0;
        }
    }
}