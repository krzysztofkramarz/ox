package com.fonowizja.ox;

import java.util.Arrays;

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
