package com.fonowizja.ox.game_elements;

import com.fonowizja.ox.game_elements.Board;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
public class BoardTest
{
   Board board;

   @BeforeTest
   public void setUp()
   {
      board = new Board();
   }

   @Test
   public void testBoardCells()
   {

      board.setSize(5, 5);
   }


}
