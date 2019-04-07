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
//todo zefaktoyzować na mniejsze klasy i otestować
   private final Map<String, List<Integer>> allEmptyWinningCombinationsThatCanBeUsed;
   //todo to jest kopia powyzszego po prostu, czy potrzebna?
   private final Map<String, List<Integer>> allPossibleWinningCombinationsForThisBoardCopyOf = new HashMap<>();
   private final GameElementsService gameElementsService;
   private final GamePanel gamePanel;
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
   private List<Integer> choosenCombinationToPlay = new ArrayList<>();
   private final Map<String, List<Integer>> hypotheticalWinningFieldsForO;
   private final Map<String, List<Integer>> hypotheticalWinningFieldsForX;
   private Integer winningSize;
   private boolean defense;
   private boolean stillCanWin;
   private List<String> someHopeToWin = new ArrayList<>();

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
      defense = false;
      stillCanWin = true;
      tryToWinOnChoosenCombination = true;
      indexFromChoosenCombinationToWin = 0;
      setCombinationToWin(allEmptyWinningCombinationsThatCanBeUsed.keySet(), defense);
   }

   private void setCombinationToWin(Set<String> keys, boolean defense)
   {
      choosenCombinationToPlay.clear();
      if (!keys.isEmpty())
      {
         List<String> kluczeLista = new ArrayList<>(keys);
         Collections.shuffle(kluczeLista);
         if (defense)
         {
            switch (semiGameMachinePlayer)
            {
               case O:
                  choosenCombinationToPlay = hypotheticalWinningFieldsForX.get(kluczeLista.get(0));
                  System.out.println("XXX: " + hypotheticalWinningFieldsForX);
                  break;
               case X:
                  choosenCombinationToPlay = hypotheticalWinningFieldsForO.get(kluczeLista.get(0));
                  System.out.println("OOO: " + hypotheticalWinningFieldsForO);
                  break;
            }
         }
         else
         {
            // if (!someHopeToWin.isEmpty())
            // {
            //    List<String> collect = new ArrayList<>();
            //    for (String someHopeOnePosition : someHopeToWin)
            //    {
            //       collect =
            //             kluczeLista.stream().filter((it) -> it.contains(someHopeOnePosition)).collect(Collectors.toList());
            //    }
            //    choosenCombinationToPlay = allEmptyWinningCombinationsThatCanBeUsed.get(collect.get(0));
            //    someHopeToWin.clear();
            //
            // }
            // else
            // {
            //    choosenCombinationToPlay = allEmptyWinningCombinationsThatCanBeUsed.get(kluczeLista.get(0));
            //
            // }
            choosenCombinationToPlay = allEmptyWinningCombinationsThatCanBeUsed.get(kluczeLista.get(0));
            System.out.println("ALL: " + allEmptyWinningCombinationsThatCanBeUsed);
         }
      }
   }

   //todo po co ta kopia mapy, lepiej na referencji działać
   //todo kopia mapy potrzebna tylko do pełni autommatyczne rozgrywo
   private void refreshWinningMaps()
   {
      //todo czy na pewno czyscic jest potrzeba
      allPossibleWinningCombinationsForThisBoardCopyOf.clear();
      //todo czy nie prosciej dodac sama referencje
      // allPossibleWinningCombinationsForThisBoardCopyOf.putAll(allEmptyWinningCombinationsThatCanBeUsed);

      allPossibleWinningCombinationsForThisBoardCopyOf.putAll(gameElementsService.getAllEmptyWinningCombinationsThatCanBeUsed());
   }

   void makeSemiAutomaticMove()
   {//todo zaorać i jeszcze raz napisać czytelniej
      refreshWinningMaps();

      if (allPossibleWinningCombinationsForThisBoardCopyOf.isEmpty())
      {
         System.out.println(("DEENSE: " + defense));
         defense = true;
      }
      //ciśnij wygraną na wybranej kombinacji, dopóki nie zablokuje przeciwnik

      for (Integer positionOnBoard : choosenCombinationToPlay)
      {

         if (buttonsList.get(positionOnBoard).getText().equals(semiGameMachinePlayer.getOppositePlayer().getSign()))
         {
            stillCanWin = false;
         }
      }

      if (!defense && !stillCanWin)
      {
         setCombinationToWin(allPossibleWinningCombinationsForThisBoardCopyOf.keySet(), defense);
         stillCanWin=true;
      }
      for (Integer positionOnBoard : choosenCombinationToPlay)
      {

         if (buttonsList.get(positionOnBoard).getText().equals(Sign.EMPTY.getSign()))
         {
            someHopeToWin.add(String.valueOf(positionOnBoard));
            gamePanel.playLikeHuman(positionOnBoard);
            return;
         }
      }

      if (defense)
      {

         switch (semiGameMachinePlayer)
         {
            case O:
               setCombinationToWin(hypotheticalWinningFieldsForX.keySet(), defense);

               for (Integer positionOnBoard : choosenCombinationToPlay)
               {
                  if (buttonsList.get(positionOnBoard).getText().equals(Sign.EMPTY.getSign()))
                  {
                     gamePanel.playLikeHuman(positionOnBoard);
                     return;
                  }
               }
               break;

            case X:
               setCombinationToWin(hypotheticalWinningFieldsForO.keySet(), defense);

               for (Integer positionOnBoard : choosenCombinationToPlay)
               {
                  if (buttonsList.get(positionOnBoard).getText().equals(Sign.EMPTY.getSign()))
                  {
                     gamePanel.playLikeHuman(positionOnBoard);
                     return;
                  }
               }
               break;
         }
      }
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
