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
   public boolean putSignIntoBoard(Sign sign, Integer positionX, Integer positionY)
   {

      return board.putSignIntoBoard(sign, positionX, positionY);
   }

   @Override
   public void cleanBoard()
   {
      board.cleanBoard();
   }

   @Override
   public void setBoardObservator(BoardObservator boardObservator)
   {
      board.setBoardObservator(boardObservator);

   }

}
