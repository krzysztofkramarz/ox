package com.fonowizja.ox.game_elements;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
@SuppressWarnings("LocalVariableHidesMemberVariable")
public class BoardTest
{
   Board board = Board.builder().x(5).y(5).winningSize(3).build();

   @Test
   public void testSetSize()
   {
      int expectedSize = 25;

      // assertThat()  n//TODO nie ma co testować, bo Board hermetyzuje dostep do tablicy
   }

   @DataProvider(name = "putSighnInEmptySpaces")
   public Object[][] putSighnInEmptySpaces()
   {
      return new Object[][] {
            { Sign.X, 0, 0 },
            { Sign.X, 1, 0 },
            { Sign.X, 2, 0 },
            { Sign.O, 3, 0 },
            { Sign.O, 4, 0 },
            { Sign.X, 0, 1 },
            { Sign.X, 1, 3 },
            { Sign.X, 2, 4 },
            { Sign.O, 4, 2 },
            { Sign.O, 4, 4 }
      };
   }

   @Test(dataProvider = "putSighnInEmptySpaces")
   public void testPutSignIntoBoard(Sign sign, Integer x, Integer y)
   {
      board.cleanBoard();
      boolean result = board.putSignIntoBoard(sign, x, y);
      assertThat(result).isTrue();
   }

   @DataProvider(name = "putSighnInNotAllowedSpaces")
   public Object[][] putSighnInNotAllowedSpaces()
   {
      return new Object[][] {
            { Sign.X, 0, 0, 0, 0 },
            { Sign.X, 1, 1, 1, 1 },
            { Sign.X, 2, 2, 2, 2 },
            { Sign.O, 2, 1, 2, 1 },
            { Sign.O, 4, 0, 4, 0 },
            { Sign.X, 3, 2, 3, 2 },
            { Sign.X, 4, 4, 4, 4 }
      };
   }

   @Test(dataProvider = "putSighnInNotAllowedSpaces")
   public void testPutSighnInNotAllowedSpaces(Sign sign, Integer x, Integer y, Integer x1, Integer y2)
   {
      board.cleanBoard();
      board.putSignIntoBoard(sign, x, y);
      boolean result = board.putSignIntoBoard(sign, x1, y2);
      assertThat(result).isFalse();
   }

   @DataProvider(name = "makeHash")
   public Object[][] makeHash()
   {
      return new Object[][] {
            { "x11", Arrays.asList(1, 2, 3) },
            { "x11", Arrays.asList(1, 2, 3) }
      };
   }

   @Test(dataProvider = "makeHash")
   public void testMakeHas(String key, List<Integer> value)
   {
      // boolean b = board.fillMap(key, value);
      // System.out.println("as");
   }

   // @DataProvider(name = "horizontalWinnerFields")
   // public Object[][] horizontalWinnerFields()
   // {
   //    return new Object[][] {
   //          { 5, 5, 3, 1, Arrays.asList(1, 2, 3) },
   //          { 5, 5, 5, 1, Arrays.asList(1, 2, 3, 4, 5) },
   //          { 20, 20, 4, 21, Arrays.asList(21, 22, 23, 24) },
   //          { 9, 9, 4, 6, Arrays.asList(6, 7, 8, 9) },
   //    };
   //
   // }
   //
   // @Test(dataProvider = "horizontalWinnerFields")
   // public void testHorizontalWinnerFields(Integer xxx, Integer ooo, Integer winningSize, Integer startingPosition, List<Integer> expected)
   // {
   //    Board board = Board.builder().xxx(5).ooo(5).winningSize(winningSize).build();
   //    IntUnaryOperator intUnaryOperator = n -> n + 1;
   //
   //    List<Integer> result = board.generateRangeOfSearching(startingPosition, intUnaryOperator, winningSize);
   //    assertThat(result).isEqualTo(expected);
   //
   // }

   // @DataProvider(name = "verticalWinnerFields")
   // public Object[][] verticalWinnerFields()
   // {
   //    return new Object[][] {
   //          { 5, 5, 3, 1, Arrays.asList(1, 6, 11) },
   //          { 5, 5, 3, 6, Arrays.asList(6, 11, 16) }
   //    };
   //
   // }
   //
   // @Test(dataProvider = "verticalWinnerFields")
   // public void testVerticalWinnerFields(Integer xxx, Integer ooo, Integer winningSize, Integer startingPosition, List<Integer> expected)
   // {
   //    Board board = Board.builder().xxx(5).ooo(5).winningSize(winningSize).build();
   //    IntUnaryOperator intUnaryOperator = n -> n + xxx;
   //
   //    List<Integer> result = board.generateRangeOfSearching(startingPosition, intUnaryOperator, winningSize);
   //
   //    assertThat(result).isEqualTo(expected);
   //
   // }
   //
   // @DataProvider(name = "slashWinnerFields")
   // public Object[][] slashWinnerFields()
   // {
   //    return new Object[][] {
   //          { 6, 6, 3, 2, Arrays.asList(2, 7, 12) },
   //          { 6, 6, 3, 9, Arrays.asList(9, 14, 19) },
   //          { 6, 6, 3, 10, Arrays.asList(10, 15, 20) },
   //          { 6, 6, 3, 17, Arrays.asList(17, 22, 27) },
   //    };
   //
   // }
   //
   // @Test(dataProvider = "slashWinnerFields")
   // public void testSlashWinnerFields(Integer x, Integer y, Integer winningSize, Integer startingPosition, List<Integer> expected)
   // {
   //    Board board = Board.builder().xxx(5).ooo(5).winningSize(winningSize).build();
   //    IntUnaryOperator intUnaryOperator = n -> n + x - 1;
   //
   //    List<Integer> result = board.generateRangeOfSearching(startingPosition, intUnaryOperator, winningSize);
   //
   //    assertThat(result).isEqualTo(expected);
   //
   // }

}
