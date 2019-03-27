package com.fonowizja.ox.game_elements;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

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

      board.putSignIntoBoard(sign, x, y);

      board.cleanBoard();
      board.putSignIntoBoard(sign, x, y);
      boolean result = board.putSignIntoBoard(sign, x1, y2);
      assertThat(result).isFalse();

   }

   @DataProvider(name = "putSighnInNotAllowedSpacesExperiment")
   public Object[][] putSighnInNotAllowedSpacesExperiment()
   {
      List<Object[]> obj = Arrays.asList(
            new Object[] { Sign.X, 0, 0, 0, 0 },
            new Object[] { Sign.X, 1, 1, 1, 1 },
            new Object[] { Sign.X, 2, 2, 2, 2 },
            new Object[] { Sign.O, 2, 1, 2, 1 },
            new Object[] { Sign.O, 4, 0, 4, 0 },
            new Object[] { Sign.X, 3, 2, 3, 2 },
            new Object[] { Sign.X, 4, 4, 4, 4 });
      return new Object[][] { { obj }
      };
   }

   @Test(dataProvider = "putSighnInNotAllowedSpacesExperiment")
   public void testPutSighnInNotAllowedSpacesExperiment(Sign sign, Integer x, Integer y, Integer x1, Integer y2, List<Object[]> args)
   {
      for (Object[] objs : args)
      {
         board.putSignIntoBoard((Sign) objs[0], (Integer) objs[1], (Integer) objs[2]);

         board.cleanBoard();
         board.putSignIntoBoard(sign, x, y);
         boolean result = board.putSignIntoBoard(sign, x1, y2);
         assertThat(result).isFalse();
      }
   }

   @Test
   public void testFillMapContainsAddedValues() throws Exception
   {
      board.fillMap(Sign.O, "[13,18,23]", Arrays.asList(13, 18, 23));
      board.fillMap(Sign.O, "[22,25,27]", Arrays.asList(22, 25, 27));
      board.fillMap(Sign.O, "[34,55,66]", Arrays.asList(34, 55, 66));
      board.fillMap(Sign.X, "[73,18,23]", Arrays.asList(73, 18, 23));
      board.fillMap(Sign.X, "[72,25,27]", Arrays.asList(72, 25, 27));
      board.fillMap(Sign.X, "[74,55,66]", Arrays.asList(74, 55, 66));

      assertThat(board.getHypotheticalWinningFieldsOOO()).containsOnlyKeys("[13,18,23]", "[22,25,27]", "[34,55,66]");
      assertThat(board.getHypotheticalWinningFieldsXXX()).containsOnlyKeys("[73,18,23]", "[72,25,27]", "[74,55,66]");
   }

   @Test
   public void testFillMapIsFilledThenEmptyByOtherSign() throws Exception
   {
      board.getHypotheticalWinningFieldsOOO().clear();
      board.getHypotheticalWinningFieldsXXX().clear();

      board.cleanBoard();
      board.fillMap(Sign.O, "[13,18,23]", Arrays.asList(13, 18, 23));
      board.fillMap(Sign.O, "[22,25,27]", Arrays.asList(22, 25, 27));
      board.fillMap(Sign.O, "[34,55,66]", Arrays.asList(34, 55, 66));
      board.fillMap(Sign.X, "[13,18,23]", Arrays.asList(13, 18, 23));
      board.fillMap(Sign.X, "[22,25,27]", Arrays.asList(22, 25, 27));
      board.fillMap(Sign.X, "[34,55,66]", Arrays.asList(34, 55, 66));

      assertThat(board.getHypotheticalWinningFieldsOOO()).isEmpty();
      assertThat(board.getHypotheticalWinningFieldsXXX()).isEmpty();
   }

   @Test
   public void testFillMapIsFilledButSomeValuesAreRemovedByAnotherSign() throws Exception
   {
      board.getHypotheticalWinningFieldsOOO().clear();
      board.getHypotheticalWinningFieldsXXX().clear();

      board.cleanBoard();
      //unique
      board.fillMap(Sign.O, "[13,18,23]", Arrays.asList(13, 18, 23));
      board.fillMap(Sign.O, "[22,25,27]", Arrays.asList(22, 25, 27));
      board.fillMap(Sign.O, "[34,55,66]", Arrays.asList(34, 55, 66));

      //duplicated
      board.fillMap(Sign.O, "[15,18,23]", Arrays.asList(13, 18, 23));
      board.fillMap(Sign.O, "[23,25,27]", Arrays.asList(22, 25, 27));

      //unique
      board.fillMap(Sign.X, "[73,18,23]", Arrays.asList(73, 18, 23));
      board.fillMap(Sign.X, "[72,25,27]", Arrays.asList(72, 25, 27));
      board.fillMap(Sign.X, "[74,55,66]", Arrays.asList(74, 55, 66));

      //duplicated
      board.fillMap(Sign.X, "[15,18,23]", Arrays.asList(13, 18, 23));
      board.fillMap(Sign.X, "[23,25,27]", Arrays.asList(22, 25, 27));
      //duplicated
      board.fillMap(Sign.X, "[83,18,23]", Arrays.asList(73, 18, 23));
      board.fillMap(Sign.X, "[82,25,27]", Arrays.asList(72, 25, 27));

      //duplicated
      board.fillMap(Sign.O, "[83,18,23]", Arrays.asList(73, 18, 23));
      board.fillMap(Sign.O, "[82,25,27]", Arrays.asList(72, 25, 27));

      assertThat(board.getHypotheticalWinningFieldsOOO()).containsOnlyKeys("[13,18,23]", "[22,25,27]", "[34,55,66]");
      assertThat(board.getHypotheticalWinningFieldsXXX()).containsOnlyKeys("[73,18,23]", "[72,25,27]", "[74,55,66]");
   }
}
