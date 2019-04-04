package com.fonowizja.ox.automatic_machine_game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.*;

import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.Sign;
import com.fonowizja.ox.gui.GamePanel;

/**
 * @author krzysztof.kramarz
 */
class AutomaticMachine
{

   private final Map<String, List<Integer>> allEmptyWinningCombinationsThatCanBeUsed;
   //todo to jest kopia powyzszego po prostu, czy potrzebna?
   private final Map<String, List<Integer>> allPossibleWinningCombinationsForThisBoardCopyOf = new HashMap<>();
   private final GameElementsService gameElementsService;
   private GamePanel gamePanel;
   private Set<String> fieldsForWinnerMapKeys;
   private List<Integer> fieldsForWinner;
   private Sign winningSign;
   private Sign enemySign;
   private List<JButton> buttonsList;
   private Integer positionOnBoard;
   private Sign semiGameMachinePlayer;
   private boolean tryToWinOnChoosenCombination;
   private Integer indexFromChoosenCombinationToWin;
   private Set<String> kluczeWszystkichpotencjalnych;
   private List<Integer> choosenCombinationToWin = new ArrayList<>();
   private Map<String, List<Integer>> hypotheticalWinningFieldsForO;
   private Map<String, List<Integer>> hypotheticalWinningFieldsForX;
   private Integer winningSize;

   AutomaticMachine(GameElementsService gameElementsService, GamePanel gamePanel)
   {
      this.gameElementsService = gameElementsService;
      this.gamePanel = gamePanel;
      //todo czy na peo mozna usunac te ponizse
      allEmptyWinningCombinationsThatCanBeUsed = gameElementsService.getAllEmptyWinningCombinationsThatCanBeUsed();
      // allPossibleWinningCombinationsForThisBoardCopyOf.putAll(allEmptyWinningNotAlreadyUsedInGame);

      hypotheticalWinningFieldsForO = gameElementsService.getHypotheticalWinningFieldsForO();
      hypotheticalWinningFieldsForX = gameElementsService.getHypotheticalWinningFieldsForX();
   }

   void semiAutomaticGameStart(List<JButton> buttonsList, Sign semiGameMachinePlayer, Integer winningSize)
   {
      this.buttonsList = buttonsList;
      this.semiGameMachinePlayer = semiGameMachinePlayer;
      this.winningSize = winningSize;
      tryToWinOnChoosenCombination = true;
      indexFromChoosenCombinationToWin = 0;
      setCombinationToWin(allEmptyWinningCombinationsThatCanBeUsed.keySet());
   }

   private void setCombinationToWin(Set<String> keys)
   {
      choosenCombinationToWin.clear();
      if (!keys.isEmpty())
      {
         List<String> kluczeLista = new ArrayList<>(keys);
         Collections.shuffle(kluczeLista);
         choosenCombinationToWin = allEmptyWinningCombinationsThatCanBeUsed.get(kluczeLista.get(0));
      }
   }

   private void refreshWinningMaps()
   {
      //todo czy na pewno czyscic jest potrzeba
      allPossibleWinningCombinationsForThisBoardCopyOf.clear();
      //todo czy nie prosciej dodac sama referencje
      // allPossibleWinningCombinationsForThisBoardCopyOf.putAll(allEmptyWinningCombinationsThatCanBeUsed);

      allPossibleWinningCombinationsForThisBoardCopyOf.putAll(gameElementsService.getAllEmptyWinningCombinationsThatCanBeUsed());
   }

   void makeSemiAutomaticMove()
   {
      refreshWinningMaps();

      if (allPossibleWinningCombinationsForThisBoardCopyOf.containsKey(choosenCombinationToWin))
      {
         if (indexFromChoosenCombinationToWin < winningSize)
         {
            positionOnBoard = choosenCombinationToWin.get(indexFromChoosenCombinationToWin);
            gamePanel.playLikeHuman(positionOnBoard);
            indexFromChoosenCombinationToWin++;
            return;
         }
      }
      else
      {
         indexFromChoosenCombinationToWin = 0;
         setCombinationToWin(allEmptyWinningCombinationsThatCanBeUsed.keySet());
      }

      switch (semiGameMachinePlayer)
      {
         case O:
            setCombinationToWin(hypotheticalWinningFieldsForX.keySet());

            for (Integer positionOnBoard : choosenCombinationToWin)
            {
               if (buttonsList.get(positionOnBoard).getText() == Sign.EMPTY.getSign())
               {
                  gamePanel.playLikeHuman(positionOnBoard);
               }
            }
            break;

         case X:
            setCombinationToWin(hypotheticalWinningFieldsForX.keySet());

            for (Integer positionOnBoard : choosenCombinationToWin)
            {
               if (buttonsList.get(positionOnBoard).getText() == Sign.EMPTY.getSign())
               {
                  gamePanel.playLikeHuman(positionOnBoard);
               }
            }
            break;
      }
      return;

   }

   void automaticTestMachineStart(Sign sign)
   {
      winningSign = sign;
      enemySign = sign.getOppositePlayer();
      refreshWinningMaps();
      fieldsForWinnerMapKeys = allPossibleWinningCombinationsForThisBoardCopyOf.keySet(); //zestaw kluczy do mapy z wolnymi polami

      Iterator<String> fieldsForWinnerMapKeysIterator = fieldsForWinnerMapKeys.iterator();

      while (fieldsForWinnerMapKeysIterator.hasNext())
      {
         //make winner fields
         String keyForWinningFields = fieldsForWinnerMapKeysIterator.next(); //biore klucz do mapy
         fieldsForWinner = allPossibleWinningCombinationsForThisBoardCopyOf.get(keyForWinningFields); //biore intigery spod klucza

         //make enemy fields
         Collection<List<Integer>> values = allPossibleWinningCombinationsForThisBoardCopyOf.values();
         List<Integer> allFieldsForEnemy = values.stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
         //usuniecie pol przeznaczonych do wyrania
         for (Integer field : fieldsForWinner)
         {
            allFieldsForEnemy.remove(field);
         }
         Collections.shuffle(allFieldsForEnemy);

         Iterator<Integer> allFieldsForEnemyIterator = allFieldsForEnemy.iterator();
         int index = 0;
         while (index < fieldsForWinner.size())
         {
            Integer positionInBoardForWinner = fieldsForWinner.get(index++);
            if (gamePanel.makeAutomaticTestMachineMove(winningSign, positionInBoardForWinner))
            {
               break;
            }
            Integer positionOfO = allFieldsForEnemyIterator.next();

            gamePanel.makeAutomaticTestMachineMove(enemySign, positionOfO);
         }
         gamePanel.validatePanel();

         gameElementsService.revertBoardToBeginningState();

      }
      gamePanel.changeAllElementsEnable(true);
   }
}
