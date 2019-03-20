package com.fonowizja.ox.game_elements;

/**
 * @author krzysztof.kramarz
 */
public class GameElementsServiceImpl implements GameElementsService
{

   Board board;

   public GameElementsServiceImpl(Integer x, Integer y)
   {
      board = Board.builder().xxx(x).yyy(y).build();
   }

   @Override
   public String getBoardAsString()
   {
      return board.getBoardAsString();
   }

}
