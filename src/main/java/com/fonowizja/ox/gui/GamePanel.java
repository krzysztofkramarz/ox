package com.fonowizja.ox.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.fonowizja.ox.game_elements.FieldIsNotEmptyException;
import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.GameElementsServiceImpl;
import com.fonowizja.ox.game_elements.IllegalSignException;
import com.fonowizja.ox.game_elements.Sign;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krzysztof.kramarz
 */
final class GamePanel extends JPanel
{

   static final int MAXIMUM_BOARD_LENGTH = 30;
   static final int MINIMUM_BOARD_LENGTH = 3;
   static final int DEFAULT_WINNING_SIZE = 3;
   static final int DEFAULT_MAXIMUM_WINNING_SIZE = 3;
   static final int DEFAULT_BOARD_LENHTG = 3;
   static final int DEFAULT_BOARD_HEIGHT = 3;
   static final Sign DEFAULT_STARTING_SIGN = Sign.X;
   ;
   private List<JButton> buttonsList;
   @Getter(AccessLevel.PACKAGE)
   private Integer boardLenght;

   @Getter(AccessLevel.PACKAGE)
   private Integer boardHeight;

   private Integer boardSize;

   @Setter(AccessLevel.PACKAGE)
   private Integer winningSize;
   @Getter(AccessLevel.PACKAGE)
   private Integer maxWinningSize;
   private boolean canPlayersPlay;
   private GameElementsService gameElementsService;
   //game elements
   private Sign whoHasATurn;
   private Integer boardPosition;
   private boolean isDraw;
   private boolean isWinningMove;

   GamePanel()
   {
      //X
      boardLenght = DEFAULT_BOARD_LENHTG;
      //Y
      boardHeight = DEFAULT_BOARD_HEIGHT;
      boardSize = boardLenght * boardHeight;
      maxWinningSize = DEFAULT_MAXIMUM_WINNING_SIZE;
      winningSize = DEFAULT_WINNING_SIZE;
      canPlayersPlay = false;
      setLayout(new GridLayout(boardHeight, boardLenght, 1, 1));
      TitledBorder border = BorderFactory.createTitledBorder("TUTAJ SIĘ GRA!");
      border.setTitleColor(Color.BLUE);
      border.setBorder(new LineBorder(Color.ORANGE, 1));
      setBorder(border);

      buttonsList = new ArrayList<>();
      createButtons(boardSize);

      validate();

   }

   private void createButtons(Integer boardSize)
   {
      buttonsList.clear();
      for (int i = 0; i < boardSize; i++)
      {
         JButton button = new JButton();
         button.setText(Sign.EMPTY.getSign());
         button.addActionListener(new GamePanel.buttonListener());
         add(button);
         buttonsList.add(button);
      }
   }

   private void removeButtons()
   {

      for (JButton button : buttonsList)
      {
         remove(button);
         validate();
      }
   }

   void resizeX(Integer newXsize)
   {
      removeButtons();
      boardLenght = newXsize;
      rebuildGamePanel();
   }

   void resizeY(Integer newYsize)
   {
      removeButtons();
      boardHeight = newYsize;
      rebuildGamePanel();
   }

   private void rebuildGamePanel()
   {
      boardSize = boardLenght * boardHeight;
      maxWinningSize = Integer.max(boardHeight, boardLenght);
      setLayout(new GridLayout(boardHeight, boardLenght, 1, 1));
      createButtons(boardSize);
      validate();
   }

   private void resetButtons()
   {
      for (JButton button : buttonsList)
      {
         button.setText(Sign.EMPTY.getSign());
         validate();
      }
   }

   private class buttonListener implements ActionListener
   {

      @Override
      public void actionPerformed(ActionEvent e)
      {
         if (canPlayersPlay)
         {
            JButton buttonClicked = (JButton) e.getSource();
            String text = buttonClicked.getText();
            boardPosition = buttonsList.indexOf(buttonClicked);
            System.out.println("klikniety button: " + boardPosition);

            if (Sign.X.getSign().equals(text) || Sign.O.getSign().equals(text))
            {
               return;
            }
            if (whoHasATurn == Sign.X)
            {
               buttonClicked.setText(Sign.X.getSign());

               makeMove(boardPosition);
            }
            else
            {
               buttonClicked.setText(Sign.O.getSign());
               makeMove(boardPosition);
            }
         }
      }

   }

   boolean startGame(Sign whoHasATurn, boolean canPlayersPlay)
   {
      this.whoHasATurn = whoHasATurn;
      this.canPlayersPlay = canPlayersPlay;
      isDraw = false;
      gameElementsService = new GameElementsServiceImpl(boardSize, boardLenght, winningSize);
      gameElementsService.cleanBoard();
      resetButtons();
      return true; //todo ustalic co ma warunek miec i jaki
   }

   boolean makeMove(Integer boardPosition)
   {
      try
      {
         isWinningMove = gameElementsService.isWinningMove(whoHasATurn, boardPosition);
      }
      catch (FieldIsNotEmptyException e)
      {
         //todo logger
         e.printStackTrace();
      }
      catch (IllegalSignException e)
      {

         //todo loger
         e.printStackTrace();
      }
      isDraw = gameElementsService.isDraw();

      if (isWinningMove)
      {
         JOptionPane.showConfirmDialog(null, "Game Over!! Wygrał ." + whoHasATurn.getSign());
         resetButtons();
      }
      else
      {
         whoHasATurn = whoHasATurn.getOppositePlayer();

      }
      return true; //todo ustalic co ma warunek miec i jaki
   }
}
