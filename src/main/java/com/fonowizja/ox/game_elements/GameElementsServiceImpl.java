package com.fonowizja.ox.game_elements;

import com.fonowizja.ox.game.BoardObservator;

/**
 * @author krzysztof.kramarz
 */
public class GameElementsServiceImpl implements GameElementsService
{

   Board board;

   // todo javadoc i wyjasnic co jest co
   public GameElementsServiceImpl(Integer boardSize, Integer boardLenght, Integer winningSize)
   {
      board = Board.builder().boardSize(boardSize).boardLenght(boardLenght).winningSize(winningSize).build();
   }

   @Override
   @Deprecated
   public String getBoardAsString()
   {
      return board.getBoardAsString();
   }

   @Override
   public boolean isWinningMove(Sign sign, Integer boardPosition) throws Exception
   {

      return board.isWinningMove(sign, boardPosition);
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

   @Override
   public boolean isDraw()
   {
      return board.isDraw();
   }

}
