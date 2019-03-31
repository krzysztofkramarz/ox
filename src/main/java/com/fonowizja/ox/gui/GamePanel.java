package com.fonowizja.ox.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.fonowizja.ox.game_elements.Sign;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krzysztof.kramarz
 */
final class GamePanel extends JPanel
{
   // private JButton[] buttons;
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

   private int alternate = 0;//if this number is a even, then put a X. If it's odd, then put an O

   GamePanel()
   {
      //X
      boardLenght = 3;
      //Y
      boardHeight = 3;
      boardSize = boardLenght * boardHeight;
      maxWinningSize = Integer.max(boardHeight, boardLenght);
      setLayout(new GridLayout(boardHeight, boardLenght, 1, 1));
      TitledBorder border = BorderFactory.createTitledBorder("GRA :)");
      border.setTitleColor(Color.BLUE);
      border.setBorder(new LineBorder(Color.BLUE, 1));
      setBorder(border);

      buttonsList = new ArrayList<>();
      // buttons = new JButton[boardSize];
      createButtons(boardSize);

      validate();

   }

   private void createButtons(Integer boardSize)
   {

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
      rebuildGamePanlel();
   }

   void resizeY(Integer newYsize)
   {
      removeButtons();
      boardHeight = newYsize;
      rebuildGamePanlel();
   }

   private void rebuildGamePanlel()
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

         JButton buttonClicked = (JButton) e.getSource(); //get the particular button that was clicked
         String text = buttonClicked.getText();
         if ("X".equals(text) || "O".equals(text))
         {
            return;
         }
         if (alternate % 2 == 0)
         {
            buttonClicked.setText("X");
         }
         else
         {
            buttonClicked.setText("O");
         }

         if (false)
         {
            JOptionPane.showConfirmDialog(null, "Game Over.");
            resetButtons();
         }

         alternate++;

      }

   }
}
