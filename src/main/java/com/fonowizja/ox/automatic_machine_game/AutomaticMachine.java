package com.fonowizja.ox.automatic_machine_game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.Sign;
import com.fonowizja.ox.gui.GamePanel;

/**
 * @author krzysztof.kramarz
 */
class AutomaticMachine
{
   private final Map<String, List<Integer>> allEmptyWinningNotAlreadyUsedInGame;
   private final Map<String, List<Integer>> allPossibleWinningCombinationsForThisBoard = new HashMap<>();
   private final GameElementsService gameElementsService;
   private GamePanel gamePanel;
   private Set<String> fieldsForWinnerMapKeys;
   private Set<Integer> indexesForEnemyList = new HashSet<>();
   private List<Integer> fieldsForWinner;
   private Sign winningSign;
   private Sign enemySign;

   AutomaticMachine(GameElementsService gameElementsService, GamePanel gamePanel)
   {
      this.gameElementsService = gameElementsService;
      this.gamePanel = gamePanel;
      allEmptyWinningNotAlreadyUsedInGame = gameElementsService.getAllEmptyWinningCombinationsThatCanBeUsed();
      allPossibleWinningCombinationsForThisBoard.putAll(allEmptyWinningNotAlreadyUsedInGame);
   }

   public void automaticTestMachineStart(Sign sign, Integer winningSize)
   {

      Thread thread = Thread.currentThread();
      System.out.println("automaticTest " + thread);

      winningSign = sign;
      enemySign = sign.getOppositePlayer();
      fieldsForWinnerMapKeys = allPossibleWinningCombinationsForThisBoard.keySet(); //zestaw kluczy do mapy z wolnymi polami

      Iterator<String> fieldsForWinnerMapKeysIterator = fieldsForWinnerMapKeys.iterator();

      while (fieldsForWinnerMapKeysIterator.hasNext())
      {
         //make winner fields
         String keyForWinningFields = fieldsForWinnerMapKeysIterator.next(); //biore klucz do mapy
         fieldsForWinner = allPossibleWinningCombinationsForThisBoard.get(keyForWinningFields); //biore intigery spod klucza
         System.out.println(fieldsForWinner);
         // Iterator<Integer> fieldForWinnerIterator = fieldsForWinner.iterator();

         //make enemy fields
         Collection<List<Integer>> values = allPossibleWinningCombinationsForThisBoard.values();
         List<Integer> allFieldsForEnemy = values.stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
         //usuniecie pol przeznaczonych do wyrania
         for (Integer field : fieldsForWinner)
         {
            allFieldsForEnemy.remove(field);
         }
         Collections.shuffle(allFieldsForEnemy);

         // indexesForEnemyList = new Random().ints(winningSize, 0, allFieldsForEnemy.size()).boxed().collect(Collectors.toSet());
         Iterator<Integer> allFieldsForEnemyIterator = allFieldsForEnemy.iterator();
         int index = 0;
         while (index < fieldsForWinner.size())
         {
            Integer positionInBoardForWinner = fieldsForWinner.get(index++);
            System.out.println("x goes " + positionInBoardForWinner);
            if (gamePanel.makeAutomaticTestMachineMove(winningSign, positionInBoardForWinner))
            {
               break; //wyjdz z iteracji
            }
            Integer positionOfO = allFieldsForEnemyIterator.next();
            System.out.println("O goes " + positionOfO);
            try
            {
               Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }
            gamePanel.makeAutomaticTestMachineMove(enemySign, positionOfO);
         }
         gamePanel.validatePanel();
         try
         {
            Thread.sleep(5000);
            System.out.println("KOLEJNY MECZ!!");
         }
         catch (InterruptedException e)
         {
            e.printStackTrace();
         }
         gameElementsService.revertBoardToBeginningState();

      }
   }
}
