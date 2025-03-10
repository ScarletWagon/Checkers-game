import java.util.ArrayList;
/*
* Names: Gurpreet Singh
* netID: gsingh38,
* G#: 01437947
* Lecture section: 201
* Lab section: 001
*/
public class MiniCheckers {
   private char[][] board;
   private Player red;
   private Player black;

   public MiniCheckers(Player red, Player black) {
      this.red = red;
      this.black = black;
      this.board = new char[][]
         {
         {'r', '.', 'r', '.', 'r'},
         {'.', '_', '.', '_', '.'},
         {'_', '.', '_', '.', '_'},
         {'.', '_', '.', '_', '.'},
         {'b', '.', 'b', '.', 'b'}
         };
   
     
   }

   public char[][] getBoard() {
      return board;
   }

   public void setBoard(char[][] board) {
      this.board = board;
   }

   public Player getRed() {
      return red;
   }

   public void setRed(Player red) {
      this.red = red;
   }

   public Player getBlack() {
      return black;
   }

   public void setBlack(Player black) {
      this.black = black;
   }

   public int countChecker(char color) {
      int count = 0;
      for (char[] row : board) {
         for (char cell : row) {
            if (cell == color || (Character.isUpperCase(color) && Character.toUpperCase(cell) == color)) {
               count++;
            }
         }
      }
      return count; 
     
   }

   public boolean checkWin(Player player) {
      char opponentChecker = (player == red) ? 'b' : 'r';
      return countChecker(opponentChecker) == 0;  
   }

   public boolean checkLose(Player player) {
    
      char opponentChecker = (player == red) ? 'b' : 'r';
      return countChecker(player == red ? 'b' : 'r') == 0 && countChecker(opponentChecker) > 0;        
   }

   public String toString() {
      String res = "";
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            res += board[i][j];
         }
         res += "\n";
      }
      return res;
   }

   private MiniCheckers makeClone() {
      /* provided code. You may call this method, but you should NOT modify it */
      MiniCheckers newMiniCheckers = new MiniCheckers(this.red, this.black);
      char[][] newBoard = newMiniCheckers.getBoard();
      char[][] curBoard = this.getBoard();
      for (int i = 0; i < curBoard.length; i++) {
         for (int j = 0; j < curBoard[i].length; j++) {
            newBoard[i][j] = curBoard[i][j];
         }
      }
      newMiniCheckers.setBoard(newBoard);
      return newMiniCheckers;
   }

   private MiniCheckers movePiece(int startRow, int startCol, int endRow, int endCol){
      /* provided code. You may call this method, but you should NOT modify it */
      MiniCheckers move = this.makeClone();
      char[][] newBoard = move.getBoard();
      char tmpPiece = newBoard[startRow][startCol];
      newBoard[startRow][startCol] = '_';
      newBoard[endRow][endCol] = tmpPiece;
      if((tmpPiece=='r' && endRow==newBoard.length-1)||(tmpPiece=='b'&&endRow==0)){
         newBoard[endRow][endCol] = Character.toUpperCase(newBoard[endRow][endCol]);
      }
      return move;
   }

   private static void removePiece(char[][] board, int i, int j){
      /* provided code. You may call this method, but you should NOT modify it */
      board[i][j] = '_';
   }

   private static boolean validIndex(char[][] board, int i, int j){
      /* provided code. You may call this method, but you should NOT modify it */
      if(i<0 || j<0 || i>=board.length || j>=board[0].length) 
         return false;
      return true;
   }

   private static boolean redCanMoveHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
   
      if (!validIndex(board, startRow, startCol) || !validIndex(board, endRow, endCol)) {
         return false;
      }
   
      char startCell = board[startRow][startCol];
      char endCell = board[endRow][endCol];
   
      if (startCell != 'r' && startCell != 'R') {
         return false;
      }
   
      if (endCell != '_') {
         return false; 
      }
   
      if (startCell == 'r' && endRow <= startRow) {
         return false; 
      }

      return Math.abs(endRow - startRow) == 1 && Math.abs(endCol - startCol) == 1;     
     
     
     
     
     
   }
   private static boolean redCanJumpHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
      /* provided code. You may call this method, but you should NOT modify it */
      if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) 
         return false;
      if(Math.abs(startRow-endRow)!=2 || Math.abs(startCol-endCol)!=2) 
         return false;
      if(board[startRow][startCol] == 'r'){
         if(endRow != startRow+2) 
            return false;
         if(board[endRow][endCol] != '_') 
            return false;
         if( (endCol == startCol+2 && (board[startRow+1][startCol+1] == 'b' || board[startRow+1][startCol+1] == 'B')) ||
            (endCol == startCol-2 && (board[startRow+1][startCol-1] == 'b' || board[startRow+1][startCol-1] == 'B'))){
            return true;
         } else {
            return false;
         }
      } else if(board[startRow][startCol] == 'R'){
         if(board[endRow][endCol] != '_') 
            return false;
         if(endRow > startRow && endCol > startCol){
            //down-right
            if(board[startRow+1][startCol+1]=='b' || board[startRow+1][startCol+1]=='B') 
               return true;
            else 
               return false;
         } else if(endRow < startRow && endCol > startCol){
            //up-right
            if(board[startRow-1][startCol+1]=='b' || board[startRow-1][startCol+1]=='B') 
               return true;
            else 
               return false;
         } else if(endRow > startRow && endCol < startCol){
            //down-left
            if(board[startRow+1][startCol-1]=='b' || board[startRow+1][startCol-1]=='B') 
               return true;
            else 
               return false;
         } else if(endRow < startRow && endCol < startCol){
            //up-left
            if(board[startRow-1][startCol-1]=='b' || board[startRow-1][startCol-1]=='B') 
               return true;
            else 
               return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
   private static boolean blackCanMoveHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
     
      if (!validIndex(board, startRow, startCol) || !validIndex(board, endRow, endCol)) {
        return false; 
    }

    char startCell = board[startRow][startCol];
    char endCell = board[endRow][endCol];

    if (startCell != 'b' && startCell != 'B') {
        return false; 
    }

    if (endCell != '_') {
        return false; 
    }

    if (startCell == 'b' && endRow >= startRow) {
        return false; 
    }

   
    return Math.abs(endRow - startRow) == 1 && Math.abs(endCol - startCol) == 1;
     
        
     
   }
   private static boolean blackCanJumpHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
      /* provided code. You may call this method, but you should NOT modify it */
      if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) 
         return false;
      if(Math.abs(startRow-endRow)!=2 || Math.abs(startCol-endCol)!=2) 
         return false;
      if(board[startRow][startCol] == 'b'){
         if(endRow != startRow-2) 
            return false;
         if(board[endRow][endCol] != '_') 
            return false;
         if( (endCol == startCol+2 && (board[startRow-1][startCol+1] == 'r' || board[startRow-1][startCol+1] == 'R')) ||
            (endCol == startCol-2 && (board[startRow-1][startCol-1] == 'r' || board[startRow-1][startCol-1] == 'R'))){
            return true;
         } else {
            return false;
         }
      } else if(board[startRow][startCol] == 'B'){
         if(board[endRow][endCol] != '_') 
            return false;
         if(endRow > startRow && endCol > startCol){
            //down-right
            if(board[startRow+1][startCol+1]=='r' || board[startRow+1][startCol+1]=='R') 
               return true;
            else 
               return false;
         } else if(endRow < startRow && endCol > startCol){
            //up-right
            if(board[startRow-1][startCol+1]=='r' || board[startRow-1][startCol+1]=='R') 
               return true;
            else 
               return false;
         } else if(endRow > startRow && endCol < startCol){
            //down-left
            if(board[startRow+1][startCol-1]=='r' || board[startRow+1][startCol-1]=='R') 
               return true;
            else 
               return false;
         } else if(endRow < startRow && endCol < startCol){
            //up-left
            if(board[startRow-1][startCol-1]=='r' || board[startRow-1][startCol-1]=='R') 
               return true;
            else 
               return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public ArrayList<MiniCheckers> possibleMoves(Player player) {
      /* provided code. You may call this method, but you should NOT modify it */
      char[][] curBoard = this.getBoard();
      ArrayList<MiniCheckers> rv = new ArrayList<MiniCheckers>();
      if(player == this.red){
         boolean couldJump = false;
         for(int i=0;i<curBoard.length;i++){
            for(int j=0;j<curBoard[i].length;j++){
               if(board[i][j]=='r' || board[i][j] =='R'){
                  if(redCanJumpHere(board,i,j,i-2,j-2)){
                     MiniCheckers newBoard = movePiece(i,j,i-2,j-2);
                     removePiece(newBoard.board,i-1,j-1);
                     rv.add(newBoard);
                     couldJump = true;
                  }
                  if(redCanJumpHere(board,i,j,i-2,j+2)){
                     MiniCheckers newBoard = movePiece(i,j,i-2,j+2);
                     removePiece(newBoard.board,i-1,j+1);
                     rv.add(newBoard);
                     couldJump = true;
                  }
                  if(redCanJumpHere(board,i,j,i+2,j-2)){
                     MiniCheckers newBoard = movePiece(i,j,i+2,j-2);
                     removePiece(newBoard.board,i+1,j-1);
                     rv.add(newBoard);
                     couldJump = true;
                  }
                  if(redCanJumpHere(board,i,j,i+2,j+2)){
                     MiniCheckers newBoard = movePiece(i,j,i+2,j+2);
                     removePiece(newBoard.board,i+1,j+1);
                     rv.add(newBoard);
                     couldJump = true;
                  }
               }
            }
         }
         if(!couldJump){
            for(int i=0;i<curBoard.length;i++){
               for(int j=0;j<curBoard[i].length;j++){
                  if(board[i][j]=='r' || board[i][j]=='R'){                            
                     if(redCanMoveHere(board,i,j,i-1,j-1)){
                        rv.add(movePiece(i,j,i-1,j-1));
                     }
                     if(redCanMoveHere(board,i,j,i-1,j+1)){
                        rv.add(movePiece(i,j,i-1,j+1));
                     }
                     if(redCanMoveHere(board,i,j,i+1,j-1)){
                        rv.add(movePiece(i,j,i+1,j-1));
                     }
                     if(redCanMoveHere(board,i,j,i+1,j+1)){
                        rv.add(movePiece(i,j,i+1,j+1));
                     }
                  }
               }
            }
         }
      } else if(player==this.black){
         boolean couldJump = false;
         //check for jumps first
         for(int i=0;i<curBoard.length;i++){
            for(int j=0;j<curBoard[i].length;j++){
               if(board[i][j]=='b' || board[i][j] =='B'){
                  if(blackCanJumpHere(board,i,j,i-2,j-2)){
                     MiniCheckers newBoard = movePiece(i,j,i-2,j-2);
                     removePiece(newBoard.board,i-1,j-1);
                     rv.add(newBoard);
                     couldJump = true;
                  }
                  if(blackCanJumpHere(board,i,j,i-2,j+2)){
                     MiniCheckers newBoard = movePiece(i,j,i-2,j+2);
                     removePiece(newBoard.board,i-1,j+1);
                     rv.add(newBoard);
                     couldJump = true;
                  }
                  if(blackCanJumpHere(board,i,j,i+2,j-2)){
                     MiniCheckers newBoard = movePiece(i,j,i+2,j-2);
                     removePiece(newBoard.board,i+1,j-1);
                     rv.add(newBoard);
                     couldJump = true;
                  }
                  if(blackCanJumpHere(board,i,j,i+2,j+2)){
                     MiniCheckers newBoard = movePiece(i,j,i+2,j+2);
                     removePiece(newBoard.board,i+1,j+1);
                     rv.add(newBoard);
                     couldJump = true;
                  }
               }
            }
         }
         if(!couldJump){
            for(int i=0;i<curBoard.length;i++){
               for(int j=0;j<curBoard[i].length;j++){
                  if(board[i][j]=='b' || board[i][j]=='B'){
                     if(blackCanMoveHere(board,i,j,i-1,j-1)){
                        rv.add(movePiece(i,j,i-1,j-1));
                     }
                     if(blackCanMoveHere(board,i,j,i-1,j+1)){
                        rv.add(movePiece(i,j,i-1,j+1));
                     }
                     if(blackCanMoveHere(board,i,j,i+1,j-1)){
                        rv.add(movePiece(i,j,i+1,j-1));
                     }
                     if(blackCanMoveHere(board,i,j,i+1,j+1)){
                        rv.add(movePiece(i,j,i+1,j+1));
                     }
                  }
               }
            }
         }
      } else {
         System.out.println("Unrecognized player?!");
      }
      return rv;
   }
}