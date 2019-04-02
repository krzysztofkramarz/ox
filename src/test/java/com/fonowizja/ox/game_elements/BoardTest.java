package com.fonowizja.ox.game_elements;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
@SuppressWarnings("LocalVariableHidesMemberVariable")
public class BoardTest
{

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
            { Sign.X, 0 },
            { Sign.X, 1 },
            { Sign.X, 2 },
            { Sign.O, 3 },
            { Sign.O, 4 },
            { Sign.X, 5 },
            { Sign.X, 15 },
            { Sign.X, 14 },
            { Sign.O, 9 },
            { Sign.O, 24 }
      };
   }

   @Test(dataProvider = "putSighnInEmptySpaces")
   public void testPutSignIntoBoard(Sign sign, Integer boardPosition)
   {
      Board board = Board.builder().boardSize(25).boardLenght(5).winningSize(3).build();
      board.cleanBoard();
      boolean result = board.putSignIntoBoard(sign, boardPosition);
      assertThat(result).isTrue();
   }

   @DataProvider(name = "putSighnInNotAllowedSpaces")
   public Object[][] putSighnInNotAllowedSpaces()
   {
      return new Object[][] {
            { Sign.X, 0, 0 },
            { Sign.X, 1, 1 },
            { Sign.X, 2, 2 },
            { Sign.O, 7, 7 },
            { Sign.O, 4, 4 },
            { Sign.X, 13, 13 },
            { Sign.X, 24, 24 }

      };
   }

   @Test(dataProvider = "putSighnInNotAllowedSpaces")
   public void testPutSighnInNotAllowedSpaces(Sign sign, Integer boardPosition1, Integer boardPosition2)
   {
      Board board = Board.builder().boardSize(25).boardLenght(5).winningSize(3).build();
      board.cleanBoard();
      board.putSignIntoBoard(sign, boardPosition1);
      boolean result = board.putSignIntoBoard(sign, boardPosition2);
      assertThat(result).isFalse();

   }

   // @DataProvider(name = "putSighnInNotAllowedSpacesExperiment")
   // public Object[][] putSighnInNotAllowedSpacesExperiment()
   // {
   //    List<Object[]> obj = Arrays.asList(
   //          new Object[] { Sign.X, 0, 0, 0, 0 },
   //          new Object[] { Sign.X, 1, 1, 1, 1 },
   //          new Object[] { Sign.X, 2, 2, 2, 2 },
   //          new Object[] { Sign.O, 2, 1, 2, 1 },
   //          new Object[] { Sign.O, 4, 0, 4, 0 },
   //          new Object[] { Sign.X, 3, 2, 3, 2 },
   //          new Object[] { Sign.X, 4, 4, 4, 4 });
   //    return new Object[][] { { obj }
   //    };
   // }
   //
   // @Test(dataProvider = "putSighnInNotAllowedSpacesExperiment")
   // public void testPutSighnInNotAllowedSpacesExperiment(List<Object[]> args)
   // {
   //    for (Object[] objs : args)
   //    {
   //       board.cleanBoard();
   //       board.putSignIntoBoard((Sign) objs[0], (Integer) objs[1]);
   //
   //       boolean result = board.putSignIntoBoard((Sign) objs[2], (Integer) objs[3]);
   //       assertThat(result).isFalse();
   //    }
   // }

   @Test
   public void testFillMapContainsAddedValues() throws Exception
   {
      Board board = Board.builder().boardSize(16).boardLenght(4).winningSize(3).build();
      board.fillMap(Sign.O, "[0, 5, 10]", Arrays.asList(0, 5, 10));
      board.fillMap(Sign.O, "[4, 9, 14]", Arrays.asList(4, 9, 14));
      board.fillMap(Sign.O, "[0, 4, 8]", Arrays.asList(0, 4, 8));
      board.fillMap(Sign.X, "[7, 11, 15]", Arrays.asList(7, 11, 15));
      board.fillMap(Sign.X, "[6, 10, 14]", Arrays.asList(6, 10, 14));
      board.fillMap(Sign.X, "[13, 14, 15]", Arrays.asList(13, 14, 15));

      assertThat(board.getHypotheticalWinningFieldsForO()).containsOnlyKeys("[0, 5, 10]", "[4, 9, 14]", "[0, 4, 8]");
      assertThat(board.getHypotheticalWinningFieldsForX()).containsOnlyKeys("[7, 11, 15]", "[6, 10, 14]", "[13, 14, 15]");
   }

   @Test
   public void testFillMapIsFilledThenEmptyByOtherSign() throws Exception
   {
      Board board = Board.builder().boardSize(25).boardLenght(5).winningSize(3).build();

      board.fillMap(Sign.O, "[0, 5, 10]", Arrays.asList(0, 5, 10));
      board.fillMap(Sign.O, "[4, 9, 14]", Arrays.asList(4, 9, 14));
      board.fillMap(Sign.O, "[0, 4, 8]", Arrays.asList(0, 4, 8));
      board.fillMap(Sign.X, "[0, 5, 10]", Arrays.asList(0, 5, 10));
      board.fillMap(Sign.X, "[4, 9, 14]", Arrays.asList(4, 9, 14));
      board.fillMap(Sign.X, "[0, 4, 8]", Arrays.asList(0, 4, 8));

      assertThat(board.getHypotheticalWinningFieldsForO()).isEmpty();
      assertThat(board.getHypotheticalWinningFieldsForX()).isEmpty();
      assertThat(board.getAllEmptyWinningCombinationsThatCanBeUsed()).doesNotContainKeys("[0, 5, 10]", "[4, 9, 14]", "[0, 4, 8]");
   }

   @Test
   public void testFillMapIsFilledButSomeValuesAreRemovedByAnotherSign() throws Exception
   {
      Board board = Board.builder().boardSize(25).boardLenght(5).winningSize(3).build();
      //duplicated
      board.fillMap(Sign.O, "[0, 5, 10]", Arrays.asList(0, 5, 10));
      board.fillMap(Sign.O, "[4, 9, 14]", Arrays.asList(4, 9, 14));
      board.fillMap(Sign.O, "[1, 7, 13]", Arrays.asList(0, 5, 10));
      //unique
      board.fillMap(Sign.O, "[3, 8, 13]", Arrays.asList(3, 8, 13));
      board.fillMap(Sign.O, "[5, 6, 7]", Arrays.asList(5, 6, 7));
      //duplicated
      board.fillMap(Sign.X, "[0, 5, 10]", Arrays.asList(0, 5, 10));
      board.fillMap(Sign.X, "[4, 9, 14]", Arrays.asList(4, 9, 14));
      board.fillMap(Sign.X, "[1, 7, 13]", Arrays.asList(1, 7, 13));
      //unique
      board.fillMap(Sign.X, "[12, 13, 14]", Arrays.asList(12, 13, 14));
      board.fillMap(Sign.X, "[12, 16, 20]", Arrays.asList(4, 5, 6));

      Map<String, List<Integer>> hypotheticalWinningFieldsOOO = board.getHypotheticalWinningFieldsForO();
      Map<String, List<Integer>> hypotheticalWinningFieldsXXX = board.getHypotheticalWinningFieldsForX();
      assertThat(hypotheticalWinningFieldsOOO).containsOnlyKeys("[3, 8, 13]", "[5, 6, 7]");
      assertThat(hypotheticalWinningFieldsXXX).containsOnlyKeys("[12, 13, 14]", "[12, 16, 20]");
      assertThat(board.getAllEmptyWinningCombinationsThatCanBeUsed()).doesNotContainKeys("[0, 5, 10]", "[4, 9, 14]", "[0, 4, 8]");
   }

   @DataProvider(name = "testWinningGame")
   public Object[][] testWinningGame()
   {
      return new Object[][] {
            { Arrays.asList(
                  Arrays.asList(Sign.O, 0),
                  Arrays.asList(Sign.X, 2),
                  Arrays.asList(Sign.O, 3),
                  Arrays.asList(Sign.X, 6),
                  Arrays.asList(Sign.O, 4),
                  Arrays.asList(Sign.X, 7)
            ), Sign.O, 8 }

      };
   }

   @Test(dataProvider = "testWinningGame")
   public void testWinningGame(List<List<Object>> movesForWin, Sign sign, Integer boardPosition) throws Exception
   {
      Board board = Board.builder().boardSize(9).boardLenght(3).winningSize(3).build();
      board.cleanBoard();

      for (List<Object> move : movesForWin)
      {
         board.isWinningMove((Sign) move.get(0), (Integer) move.get(1));
      }

      //final move
      boolean result = board.isWinningMove(sign, boardPosition);
      boolean draw = board.isDraw();
      assertThat(result).isTrue();
      assertThat(draw).isFalse();

   }

   @DataProvider(name = "testWinningGame16")
   public Object[][] testWinningGame16()
   {
      return new Object[][] {
            { Arrays.asList(
                  Arrays.asList(Sign.X, 10),
                  Arrays.asList(Sign.O, 3),
                  Arrays.asList(Sign.X, 14),
                  Arrays.asList(Sign.O, 11),
                  Arrays.asList(Sign.X, 0),
                  Arrays.asList(Sign.O, 8),
                  Arrays.asList(Sign.X, 5),
                  Arrays.asList(Sign.O, 15),
                  Arrays.asList(Sign.X, 12),
                  Arrays.asList(Sign.O, 2),
                  Arrays.asList(Sign.X, 4)
            ), Sign.O, 7 }

      };
   }

   @Test(dataProvider = "testWinningGame16")
   public void testWinningGame16(List<List<Object>> movesForWin, Sign sign, Integer boardPosition) throws Exception
   {
      Board board = Board.builder().boardSize(16).boardLenght(4).winningSize(4).build();
      board.cleanBoard();

      for (List<Object> move : movesForWin)
      {
         board.isWinningMove((Sign) move.get(0), (Integer) move.get(1));
      }

      //final move
      boolean result = board.isWinningMove(sign, boardPosition);
      boolean draw = board.isDraw();
      assertThat(result).isTrue();
      assertThat(draw).isFalse();

   }

   @DataProvider(name = "testWinningGameBackSlashSize9")
   public Object[][] testWinningGameBackSlashSize9()
   {
      return new Object[][] {
            { Arrays.asList(
                  Arrays.asList(Sign.X, 0),
                  Arrays.asList(Sign.O, 1),
                  Arrays.asList(Sign.X, 4),
                  Arrays.asList(Sign.O, 5),
                  Arrays.asList(Sign.X, 7),
                  Arrays.asList(Sign.O, 6),
                  Arrays.asList(Sign.X, 3),
                  Arrays.asList(Sign.O, 2)
            ), Sign.X, 8 }

      };
   }

   @Test(dataProvider = "testWinningGameBackSlashSize9")
   public void testWinningGameBackSlashSize9(List<List<Object>> movesForWin, Sign sign, Integer boardPosition) throws Exception
   {
      Board board = Board.builder().boardSize(9).boardLenght(3).winningSize(3).build();
      board.cleanBoard();

      for (List<Object> move : movesForWin)
      {
         board.isWinningMove((Sign) move.get(0), (Integer) move.get(1));
      }

      //final move
      boolean result = board.isWinningMove(sign, boardPosition);
      boolean draw = board.isDraw();
      assertThat(result).isTrue();
      assertThat(draw).isFalse();

   }

   @DataProvider(name = "testDraw")
   public Object[][] testDraw()
   {
      return new Object[][] {
            { Arrays.asList(
                  Arrays.asList(Sign.X, 10),
                  Arrays.asList(Sign.O, 3),
                  Arrays.asList(Sign.X, 14),
                  Arrays.asList(Sign.O, 11),
                  Arrays.asList(Sign.X, 0),
                  Arrays.asList(Sign.O, 8),
                  Arrays.asList(Sign.X, 5),
                  Arrays.asList(Sign.O, 15),
                  Arrays.asList(Sign.X, 12),
                  Arrays.asList(Sign.O, 2),
                  Arrays.asList(Sign.X, 4),
                  Arrays.asList(Sign.O, 6),
                  Arrays.asList(Sign.X, 7)
            ), Sign.O, 1 }

      };
   }

   @Test(dataProvider = "testDraw")
   public void testDraw(List<List<Object>> movesForWin, Sign sign, Integer boardPosition) throws Exception
   {
      Board board = Board.builder().boardSize(16).boardLenght(4).winningSize(4).build();
      board.cleanBoard();

      for (List<Object> move : movesForWin)
      {
         board.isWinningMove((Sign) move.get(0), (Integer) move.get(1));
      }

      //final move
      boolean result = board.isWinningMove(sign, boardPosition);
      boolean draw = board.isDraw();
      assertThat(result).isFalse();
      assertThat(draw).isTrue();

   }

   @DataProvider(name = "testForAutomation")
   public Object[][] testForAutomation()
   {
      return new Object[][] {
            { Arrays.asList(
                  Arrays.asList(Sign.X, 0),
                  Arrays.asList(Sign.X, 1),
                  Arrays.asList(Sign.X, 2),
                  Arrays.asList(Sign.X, 3)
            ), Arrays.asList(Arrays.asList(12, 13, 14, 15), Arrays.asList(4, 5, 6, 7), Arrays.asList(8, 9, 10, 11)) }

      };
   }

   @Test(dataProvider = "testForAutomation")
   public void testForAutomation(List<List<Object>> movesForWin, List<List<Integer>> expect) throws Exception
   {
      Board board = Board.builder().boardSize(16).boardLenght(4).winningSize(4).build();
      board.cleanBoard();

      for (List<Object> move : movesForWin)
      {
         board.isWinningMove((Sign) move.get(0), (Integer) move.get(1));
      }

      Map<String, List<Integer>> expectWinningFields = new HashMap<>();

      for (List<Integer> all : expect)
      {
         expectWinningFields.put(all.toString(), all);
      }

      Map<String, List<Integer>> result = board.getAllEmptyWinningCombinationsThatCanBeUsed();
      assertThat(result).containsAllEntriesOf(expectWinningFields);
   }

   @DataProvider(name = "testForAutomationLength3")
   public Object[][] testForAutomationLength3()
   {
      return new Object[][] {
            { Arrays.asList(
                  Arrays.asList(Sign.X, 0),
                  Arrays.asList(Sign.X, 1),
                  Arrays.asList(Sign.X, 2)
            ), Arrays.asList(
                  Arrays.asList(4, 5, 6),
                  Arrays.asList(5, 6, 7),
                  Arrays.asList(8, 9, 10),
                  Arrays.asList(9, 10, 11),
                  Arrays.asList(12, 13, 14),
                  Arrays.asList(13, 14, 15),
                  Arrays.asList(3, 7, 11),
                  Arrays.asList(4, 8, 12),
                  Arrays.asList(5, 9, 13),
                  Arrays.asList(6, 10, 14),
                  Arrays.asList(7, 11, 15),
                  Arrays.asList(4, 9, 14),
                  Arrays.asList(5, 10, 15),
                  Arrays.asList(3, 6, 9),
                  Arrays.asList(6, 9, 12),
                  Arrays.asList(7, 10, 13))
            }

      };
   }

   @Test(dataProvider = "testForAutomationLength3")
   public void testForAutomationLength3(List<List<Object>> movesForWin, List<List<Integer>> expect) throws Exception
   {
      Board board = Board.builder().boardSize(16).boardLenght(4).winningSize(3).build();
      board.cleanBoard();

      for (List<Object> move : movesForWin)
      {
         board.isWinningMove((Sign) move.get(0), (Integer) move.get(1));
      }

      Map<String, List<Integer>> expectWinningFields = new HashMap<>();

      for (List<Integer> all : expect)
      {
         expectWinningFields.put(all.toString(), all);
      }

      Map<String, List<Integer>> result = board.getAllEmptyWinningCombinationsThatCanBeUsed();
      assertThat(result).containsAllEntriesOf(expectWinningFields);
   }
}
