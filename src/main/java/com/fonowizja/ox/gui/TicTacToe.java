package com.fonowizja.ox.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TicTacToe extends JPanel
{

   public static void main(String[] args)
   {
      JFrame window = new JFrame("Tic-Tac-Toe");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.getContentPane().add(new TicTacToe());
      window.setBounds(1000, 1000, 1000, 1000);
      window.setVisible(true);
   }

   public TicTacToe()
   {
      setLayout(new GridLayout(10, 10));
      // setLayout(new FlowLayout(10));
      initializebuttons(9);
      sliderr();
   }

   // ##############################  SLIDER #####################################

   JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
         0, 15, 4);

   // Icon cross =  new ImageIcon("close.png");
   // Icon cross =  new ImageIcon("/epam/xo/gui/close.png");

   Image img;
   ImageIcon cross;

   void sliderr()
   {

      framesPerSecond.addChangeListener(new SliderLisinerek());

      //Turn on labels at major tick marks.
      framesPerSecond.setMajorTickSpacing(10);
      framesPerSecond.setMinorTickSpacing(1);
      framesPerSecond.setPaintTicks(true);
      framesPerSecond.setPaintLabels(true);
      framesPerSecond.setVisible(true);
      add(framesPerSecond);
   }

   private class SliderLisinerek implements ChangeListener
   {
      @Override
      public void stateChanged(ChangeEvent e)
      {
         JSlider sds = (JSlider) e.getSource();
         int ileButtonow = sds.getValue();
         initializebuttons(ileButtonow);
         validate();

      }
   }

   // ##############################  BUTTON  #####################################

   JButton buttons[] = new JButton[25];
   int alternate = 0;//if this number is a even, then put a X. If it's odd, then put an O

   public void initializebuttons(Integer ileButtonow)
   {

      // cross = new ImageIcon("/resources/close.png");
      //   img = ImageIO.read(getClass().getResource("/resources/close.png"));

      for (int i = 0; i <= ileButtonow; i++)
      {
         buttons[i] = new JButton();
         buttons[i].setText("");
         buttons[i].addActionListener(new buttonListener());

         add(buttons[i]); //adds this button to JPanel (note: no need for JPanel.add(...)
         //because this whole class is a JPanel already
      }
   }

   public void resetButtons()
   {
      for (int i = 0; i <= 8; i++)
      {
         buttons[i].setText("");
      }
   }

   // when a button is clicked, it generates an ActionEvent. Thus, each button needs an ActionListener. When it is clicked, it goes to this listener class that I have created and goes to the actionPerformed method. There (and in this class), we decide what we want to do.
   private class buttonListener implements ActionListener
   {

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
            buttonClicked.setIcon(cross);
            // buttonClicked.setText("O");
         }

         if (checkForWin() == true)
         {
            JOptionPane.showConfirmDialog(null, "Game Over.");
            resetButtons();
         }

         alternate++;

      }

      public boolean checkForWin()
      {
         /**   Reference: the button array is arranged like this as the board
          *      0 | 1 | 2
          *      3 | 4 | 5
          *      6 | 7 | 8
          */
         //horizontal win check
         if (checkAdjacent(0, 1) && checkAdjacent(1, 2)) //no need to put " == true" because the default check is for true
         {
            return true;
         }
         else if (checkAdjacent(3, 4) && checkAdjacent(4, 5))
         {
            return true;
         }
         else if (checkAdjacent(6, 7) && checkAdjacent(7, 8))
         {
            return true;
         }

         //vertical win check
         else if (checkAdjacent(0, 3) && checkAdjacent(3, 6))
         {
            return true;
         }
         else if (checkAdjacent(1, 4) && checkAdjacent(4, 7))
         {
            return true;
         }
         else if (checkAdjacent(2, 5) && checkAdjacent(5, 8))
         {
            return true;
         }

         //diagonal win check
         else if (checkAdjacent(0, 4) && checkAdjacent(4, 8))
         {
            return true;
         }
         else if (checkAdjacent(2, 4) && checkAdjacent(4, 6))
         {
            return true;
         }
         else
         {
            return false;
         }

      }

      public boolean checkAdjacent(int a, int b)
      {
         if (buttons[a].getText().equals(buttons[b].getText()) && !buttons[a].getText().equals(""))
         {
            return true;
         }
         else
         {
            return false;
         }
      }

   }

   enum Sign
   {
      X, O
   }
}



