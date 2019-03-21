package com.fonowizja.ox.game_elements;

import com.fonowizja.ox.game_elements.game.BoardObservator;

/**
 * @author krzysztof.kramarz
 */
public class GameElementsServiceImpl implements GameElementsService
{

   Board board;

   public GameElementsServiceImpl(Integer x, Integer y, Integer numberOfWiningSigns)
   {
      board = Board.builder().xxx(x).ooo(y).winningSize(numberOfWiningSigns).build();
   }

   @Override
   public String getBoardAsString()
   {
      return board.getBoardAsString();
   }

   @Override
   public boolean putSignIntoBoard(Sign sign, Integer positionInRow, Integer howManyFullRows)
   {

      return board.putSignIntoBoard(sign, positionInRow, howManyFullRows);
   }

   @Override
   public void setEmptyBoard()
   {
      board.setEmptyBoard();
   }

   @Override
   public void setBoardObservator(BoardObservator boardObservator)
   {
      board.setBoardObservator(boardObservator);

   }

}
