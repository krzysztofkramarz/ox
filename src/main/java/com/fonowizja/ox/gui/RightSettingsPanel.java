package com.fonowizja.ox.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.fonowizja.ox.game_elements.Sign;

/**
 * @author krzysztof.kramarz
 */
final class RightSettingsPanel extends JPanel
{
   private JSlider boardHeightySlider;
   private JTextField boardHeightSliderTextField;
   private GamePanel gamePanel;
   private UpperSettingsPanel upperSettingsPanel;

   private JPanel boardHeightPanel;
   private JLabel boardHeightLabel;

   private JButton gameStartBtn;
   private JButton automaticTestMachineStartBtn;
   private JButton gameWithAutomaticMachineStartBtn;

   RightSettingsPanel(GamePanel gamePanel, UpperSettingsPanel upperSettingsPanel)
   {
      this.gamePanel = gamePanel;
      this.upperSettingsPanel = upperSettingsPanel;
      setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
      setBorder(new LineBorder(new Color(40), 1));

      setBoardHeightSliderAttibutes();
      createBoardHeightPanel();

      createGameStartButton();
      createGameWithAutomaticMachineStartBtn();
      createAutomaticTestMachineStartBtn();

   }

   private void createAutomaticTestMachineStartBtn()
   {
      automaticTestMachineStartBtn = new JButton();
      automaticTestMachineStartBtn = new JButton("AUTOMATYCZNA ROZGRYWKA");
      automaticTestMachineStartBtn.setBackground(Color.PINK);
      automaticTestMachineStartBtn.setOpaque(true);
      add(automaticTestMachineStartBtn);
      automaticTestMachineStartBtn.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            upperSettingsPanel.changeElementsEnable(false);
            changeElementsEnable(false);
            Sign whoIsFirst = upperSettingsPanel.getWhoIsFirst();
            gamePanel.automaticTestMachineStart(whoIsFirst, false);
         }

      });
   }

   private void createGameWithAutomaticMachineStartBtn()
   {
      gameWithAutomaticMachineStartBtn = new JButton();
      gameWithAutomaticMachineStartBtn = new JButton("GRAJ Z AUTOMATEM");
      gameWithAutomaticMachineStartBtn.setBackground(Color.PINK);
      gameWithAutomaticMachineStartBtn.setOpaque(true);
      add(gameWithAutomaticMachineStartBtn);
      gameWithAutomaticMachineStartBtn.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {

         }

      });
   }

   private void createGameStartButton()
   {
      gameStartBtn = new JButton("START GRY");
      gameStartBtn.setBackground(Color.PINK);
      gameStartBtn.setOpaque(true);
      add(gameStartBtn);
      gameStartBtn.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            upperSettingsPanel.changeElementsEnable(false);
            changeElementsEnable(false);
            Sign whoIsFirst = upperSettingsPanel.getWhoIsFirst();
            gamePanel.startGame(whoIsFirst, true);
         }

      });
   }

   private void changeElementsEnable(boolean isEnable)
   {
      boardHeightySlider.setEnabled(isEnable);
      boardHeightSliderTextField.setEnabled(isEnable);
      gameStartBtn.setEnabled(isEnable);
   }

   private void setBoardHeightSliderAttibutes()
   {
      boardHeightySlider
            = new JSlider(JSlider.VERTICAL, GamePanel.DEFAULT_BOARD_LENHTG, GamePanel.MAXIMUM_BOARD_LENGTH, GamePanel.MINIMUM_BOARD_LENGTH);
      boardHeightySlider.addChangeListener(new BoardHeightSliderListener());

      SliderHelper.setSliderAttibutes(boardHeightySlider);
   }

   private void createBoardHeightPanel()
   {

      boardHeightPanel = new JPanel();
      boardHeightLabel = new JLabel("Ustaw wysokość tablicy");
      boardHeightSliderTextField = new JTextField(String.valueOf(GamePanel.DEFAULT_BOARD_HEIGHT), 2);

      boardHeightPanel.add(boardHeightLabel);
      boardHeightPanel.add(boardHeightySlider);
      boardHeightPanel.add(boardHeightSliderTextField);

      TitledBorder border = BorderFactory.createTitledBorder("Ustaw wysokość tablicy");
      border.setTitleColor(Color.blue);
      border.setBorder(new LineBorder(Color.BLUE, 1));
      boardHeightPanel.setBorder(border);
      boardHeightPanel.setVisible(true);

      add(boardHeightPanel);
   }

   private class BoardHeightSliderListener implements ChangeListener
   {
      @Override
      public void stateChanged(ChangeEvent e)
      {
         JSlider source = (JSlider) e.getSource();
         int y = source.getValue();
         boardHeightSliderTextField.setText(String.valueOf(y));
         gamePanel.resizeY(y);
         upperSettingsPanel.recalculateWinningSizeSlider(y);
         validate();

      }
   }

}
