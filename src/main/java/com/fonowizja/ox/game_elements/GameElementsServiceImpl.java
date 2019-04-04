package com.fonowizja.ox.game_elements;

import java.util.List;
import java.util.Map;

/**
 * @author krzysztof.kramarz
 */
public class GameElementsServiceImpl implements GameElementsService
{

   private Board board;

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
   public boolean isWinningMove(Sign sign, Integer boardPosition) throws FieldIsNotEmptyException, IllegalSignException
   {

      return board.isWinningMove(sign, boardPosition);
   }

   @Override
   public void revertBoardToBeginningState()
   {
      board.revertBoardToBeginningState();
   }

   @Override
   public boolean isDraw()
   {
      return board.isDraw();
   }

   @Override
   public Map<String, List<Integer>> getHypotheticalWinningFieldsForX()
   {
      return board.getHypotheticalWinningFieldsForX();
   }

   @Override
   public Map<String, List<Integer>> getHypotheticalWinningFieldsForO()
   {
      return board.getHypotheticalWinningFieldsForO();
   }

   //todo czy potrzebna?? usunac jak coś
   @Override
   public Map<String, List<Integer>> getAllEmptyWinningCombinationsThatCanBeUsed()
   {
      return board.getAllEmptyWinningCombinationsThatCanBeUsed();
   }

}
