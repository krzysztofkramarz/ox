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
import com.fonowizja.ox.internationalization.LanguagesKey;
import com.fonowizja.ox.internationalization.MessageProvider;
import com.fonowizja.ox.internationalization.MessagesKey;

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
   private JPanel buttonPanel;
   private JPanel languagesComboBoxPanel;

   private JButton gameStartBtn;
   private JButton automaticTestMachineStartBtn;
   private JButton gameWithAutomaticMachineStartBtn;

   private TitledBorder boardHeightBorder;

   private JComboBox<LanguagesKey> languagesComboBox;

   RightSettingsPanel(GamePanel gamePanel, UpperSettingsPanel upperSettingsPanel)
   {
      this.gamePanel = gamePanel;
      this.upperSettingsPanel = upperSettingsPanel;
      setLayout(new BorderLayout(10, 10));
      // setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
      setBorder(new LineBorder(new Color(40), 1));

      setBoardHeightSliderAttibutes();
      createBoardHeightPanel();

      createGameStartButton();
      createGameWithAutomaticMachineStartBtn();
      createAutomaticTestMachineStartBtn();
      createButtonPanel();

      createLanguagesComboBoxPanel();
      translateAllMessages();

   }

   void translateAllMessages()
   {
      boardHeightBorder.setTitle(MessageProvider.getInstance().getMessage(MessagesKey.RIGHT_PANEL_BOARD_HEIGHT_SLIDER));
      gameStartBtn.setText(MessageProvider.getInstance().getMessage(MessagesKey.RIGHT_PANEL_START_GAME_BUTON));
      gameWithAutomaticMachineStartBtn.setText(MessageProvider.getInstance().getMessage(MessagesKey.RIGHT_PANEL_GAME_WITH_AUTOMAT_BUTTON));
      automaticTestMachineStartBtn.setText(MessageProvider.getInstance().getMessage(MessagesKey.RIGHT_PANEL_AUTOMATIC_GAME_BUTTON));
   }

   private void createLanguagesComboBoxPanel()
   {
      languagesComboBoxPanel = new JPanel();
      languagesComboBox = new JComboBox<>();
      languagesComboBox.setEditable(false);
      languagesComboBox.setForeground(Color.pink);
      languagesComboBox.addItem(LanguagesKey.ENGLISH);
      languagesComboBox.addItem(LanguagesKey.POLISH);
      languagesComboBox.setSelectedItem(LanguagesKey.ENGLISH);
      languagesComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {

            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            LanguagesKey language = (LanguagesKey) cb.getSelectedItem();
            MessageProvider.getInstance().changeLanguageBundle(language);
            translateAllMessages();
            upperSettingsPanel.translateAllMessages();
            gamePanel.translateAllMessages();
         }
      });
      translateAllMessages();
      languagesComboBoxPanel.add(languagesComboBox);
      buttonPanel.add(languagesComboBoxPanel);
   }

   private void createAutomaticTestMachineStartBtn()
   {
      automaticTestMachineStartBtn = new JButton();
      automaticTestMachineStartBtn = new JButton("");
      automaticTestMachineStartBtn.setBackground(Color.PINK);
      automaticTestMachineStartBtn.setOpaque(true);
      automaticTestMachineStartBtn.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            changeAllElementsEnable(false);
            Sign whoIsFirst = upperSettingsPanel.getWhoIsFirst();
            gamePanel.automaticTestMachineStart(whoIsFirst);
         }

      });
   }

   private void createGameWithAutomaticMachineStartBtn()
   {
      gameWithAutomaticMachineStartBtn = new JButton();
      gameWithAutomaticMachineStartBtn = new JButton("");
      gameWithAutomaticMachineStartBtn.setBackground(Color.PINK);
      gameWithAutomaticMachineStartBtn.setOpaque(true);
      gameWithAutomaticMachineStartBtn.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            changeAllElementsEnable(false);
            Sign whoIsFirst = upperSettingsPanel.getWhoIsFirst();
            gamePanel.startSemiAutomaticGame(whoIsFirst, true);
         }

      });
   }

   private void createGameStartButton()
   {
      gameStartBtn = new JButton("");
      gameStartBtn.setBackground(Color.PINK);
      gameStartBtn.setOpaque(true);
      gameStartBtn.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {

            changeAllElementsEnable(false);
            Sign whoIsFirst = upperSettingsPanel.getWhoIsFirst();
            gamePanel.startGame(whoIsFirst, true);
         }

      });
   }

   private void createButtonPanel()
   {
      buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

      buttonPanel.add(gameStartBtn);
      buttonPanel.add(gameWithAutomaticMachineStartBtn);
      buttonPanel.add(automaticTestMachineStartBtn);
      gameStartBtn.setAlignmentX(CENTER_ALIGNMENT);
      gameWithAutomaticMachineStartBtn.setAlignmentX(CENTER_ALIGNMENT);
      automaticTestMachineStartBtn.setAlignmentX(CENTER_ALIGNMENT);
      add(buttonPanel, BorderLayout.CENTER);
   }

   void changeAllElementsEnable(boolean isEnable)
   {
      upperSettingsPanel.changeElementsEnable(isEnable);

      boardHeightySlider.setEnabled(isEnable);
      boardHeightSliderTextField.setEnabled(isEnable);
      gameStartBtn.setEnabled(isEnable);
      gameWithAutomaticMachineStartBtn.setEnabled(isEnable);
      automaticTestMachineStartBtn.setEnabled(isEnable);
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
      boardHeightSliderTextField = new JTextField(String.valueOf(GamePanel.DEFAULT_BOARD_HEIGHT), 2);

      boardHeightPanel.add(boardHeightySlider);
      boardHeightPanel.add(boardHeightSliderTextField);

      boardHeightBorder = BorderFactory.createTitledBorder("");
      boardHeightBorder.setTitleColor(Color.blue);
      boardHeightBorder.setBorder(new LineBorder(Color.BLUE, 1));
      boardHeightPanel.setBorder(boardHeightBorder);
      boardHeightPanel.setVisible(true);

      add(boardHeightPanel, BorderLayout.NORTH);
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
