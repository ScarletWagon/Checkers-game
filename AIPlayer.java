import java.util.ArrayList;

public class AIPlayer extends Player {

   private String name;
   private Player opponent;
   
   public Player getOpponent(){
      return opponent;}
   
   public void setOpponent(Player opponent) {
      this.opponent = opponent;
   }

   public AIPlayer(String name, Player opponent) {
      this.name = name;
      this.opponent = opponent;
   }

   @Override
   public String toString() {
      return name + " (AI)";
   }

   public double minValue(MiniCheckers game, int depth) {
      if (game.checkWin(this)) {
         return 10.0;
      } else if (game.checkLose(this)) {
         return -10.0;
      } else if (depth < 1) {
         int numMyCheck = game.countChecker(name.toLowerCase().charAt(0));
         int numMyKin = game.countChecker(Character.toUpperCase(name.charAt(0)));
         int numOppCheck = game.countChecker(opponent.toString().toLowerCase().charAt(0));
         int numOppKin = game.countChecker(Character.toUpperCase(opponent.toString().charAt(0)));
         return (numMyCheck + 3 * numMyKin) - (numOppCheck + 3 * numOppKin);
      }
   
      double minValue = Double.POSITIVE_INFINITY;
   
      for (MiniCheckers possibleMove : game.possibleMoves(this)) {
         double val = maxValue(possibleMove, depth - 1);
         minValue = Math.min(minValue, val);
      }
   
      return minValue;
   }

   public double maxValue(MiniCheckers game, int depth) {
      if (game.checkWin(this)) {
         return 10.0;
      } else if (game.checkLose(this)) {
         return -10.0;
      } else if (depth < 1) {
         int numMyCheck = game.countChecker(name.toLowerCase().charAt(0));
         int numMyKin = game.countChecker(Character.toUpperCase(name.charAt(0)));
         int numOppCheck = game.countChecker(opponent.toString().toLowerCase().charAt(0));
         int numOppKin = game.countChecker(Character.toUpperCase(opponent.toString().charAt(0)));
         return (numMyCheck + 3 * numMyKin) - (numOppCheck + 3 * numOppKin);
      }
   
      double maxValue = Double.NEGATIVE_INFINITY;
   
      for (MiniCheckers possibleMove : game.possibleMoves(this)) {
         double val = minValue(possibleMove, depth - 1);
         maxValue = Math.max(maxValue, val);
      }
   
      return maxValue;
   }

   @Override
   public MiniCheckers chooseMove(MiniCheckers game) {
      ArrayList<MiniCheckers> possibleMoves = game.possibleMoves(this);
      double max = Double.NEGATIVE_INFINITY;
      MiniCheckers bestMove = null;
   
      for (MiniCheckers possibleMove : possibleMoves) {
         double val = minValue(possibleMove, 10);
         if (val > max) {
            max = val;
            bestMove = possibleMove;
         }
      }
   
      return bestMove;
   }

   @Override
   public double boardValue(MiniCheckers game) {
      return maxValue(game, 10);
   }
}
