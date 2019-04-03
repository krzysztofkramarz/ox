package com.fonowizja.ox.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.fonowizja.ox.game_elements.Sign;
import com.fonowizja.ox.internationalization.MessageProvider;
import com.fonowizja.ox.internationalization.MessagesKey;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author krzysztof.kramarz
 */
final class UpperSettingsPanel extends JPanel
{

   private JSlider boardLengthSlider;
   private JSlider winningSizeSlider;
   private JTextField boardLengthSliderResultTextField;
   private JTextField winningSizeResultTextField;
   private GamePanel gamePanel;

   private ButtonGroup whoIsFirstBtnGrp;
   private JRadioButton XisFirsRadioBtn;
   private JRadioButton OisFirstRadioBtn;
   private JPanel whoIsFistPanel;
   private JPanel boardLengthPanel;
   private JPanel winningSizePanel;

   @Getter(AccessLevel.PACKAGE)
   private Sign whoIsFirst;
   private TitledBorder winningSizeBorder;
   private TitledBorder boardLengthSliderBorder;
   private TitledBorder whoIsFirstBorder;

   UpperSettingsPanel(GamePanel gamePanel)
   {

      this.gamePanel = gamePanel;
      setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
      setBorder(new LineBorder(new Color(40), 1));
      setPreferredSize(new Dimension(4000, 200));
      whoIsFirst = GamePanel.DEFAULT_STARTING_SIGN;

      setBoardLengthSliderAttibutes();
      setWinningSizeSliderAttibutes();

      setBoardLengthPanel();
      setWinningSizePanel();
      setWhoIsFirstPanel();

      translateAllMessages();
   }

   void translateAllMessages()
   {
      boardLengthSliderBorder.setTitle(MessageProvider.getInstance().getMessage(MessagesKey.UPPER_PANEL_BOARD_LENGTH_SLIDER));
      winningSizeBorder.setTitle(MessageProvider.getInstance().getMessage(MessagesKey.UPPER_PANEL_WINNING_SIZE_SLIDER));
      whoIsFirstBorder.setTitle(MessageProvider.getInstance().getMessage(MessagesKey.UPPER_PANEL_WHO_FIRST));
      XisFirsRadioBtn.setText(MessageProvider.getInstance().getMessage(MessagesKey.UPPER_PANEL_WHO_FIRST_X));
      OisFirstRadioBtn.setText(MessageProvider.getInstance().getMessage(MessagesKey.UPPER_PANEL_WHO_FIRST_O));
   }

   private void setWinningSizePanel()
   {

      winningSizePanel = new JPanel();
      winningSizeResultTextField = new JTextField(String.valueOf(GamePanel.DEFAULT_WINNING_SIZE), 2);
      winningSizePanel.add(winningSizeSlider);
      winningSizePanel.add(winningSizeResultTextField);
      winningSizeBorder = BorderFactory.createTitledBorder("");
      winningSizeBorder.setTitleColor(Color.MAGENTA);
      winningSizeBorder.setBorder(new LineBorder(Color.MAGENTA, 1));
      winningSizePanel.setBorder(winningSizeBorder);
      winningSizePanel.setVisible(true);

      add(winningSizePanel);
   }

   private void setBoardLengthPanel()
   {
      boardLengthPanel = new JPanel();
      boardLengthSliderResultTextField = new JTextField(String.valueOf(GamePanel.DEFAULT_BOARD_LENHTG), 2);
      boardLengthPanel.add(boardLengthSlider);
      boardLengthPanel.add(boardLengthSliderResultTextField);

      boardLengthSliderBorder = BorderFactory.createTitledBorder("");
      boardLengthSliderBorder.setTitleColor(Color.blue);
      boardLengthSliderBorder.setBorder(new LineBorder(Color.BLUE, 1));
      boardLengthPanel.setBorder(boardLengthSliderBorder);
      boardLengthPanel.setVisible(true);

      add(boardLengthPanel);
   }

   private void setWhoIsFirstPanel()
   {

      whoIsFistPanel = new JPanel(new FlowLayout());

      whoIsFirstBtnGrp = new ButtonGroup();
      XisFirsRadioBtn = new JRadioButton("", true);
      OisFirstRadioBtn = new JRadioButton("", false);
      XisFirsRadioBtn.addActionListener(event -> whoIsFirst = Sign.X);
      OisFirstRadioBtn.addActionListener(event -> whoIsFirst = Sign.O);
      whoIsFirstBtnGrp.add(XisFirsRadioBtn);
      whoIsFirstBtnGrp.add(OisFirstRadioBtn);
      whoIsFistPanel.add(XisFirsRadioBtn);
      whoIsFistPanel.add(OisFirstRadioBtn);

      whoIsFirstBorder = BorderFactory.createTitledBorder("Kto zaczyna");
      whoIsFirstBorder.setTitleColor(Color.green);
      whoIsFirstBorder.setBorder(new LineBorder(Color.green, 1));
      whoIsFistPanel.setBorder(whoIsFirstBorder);
      add(whoIsFistPanel);
      whoIsFistPanel.setVisible(true);
   }

   private void setBoardLengthSliderAttibutes()
   {
      boardLengthSlider =
            new JSlider(JSlider.HORIZONTAL, GamePanel.DEFAULT_BOARD_LENHTG, GamePanel.MAXIMUM_BOARD_LENGTH, GamePanel.MINIMUM_BOARD_LENGTH);
      boardLengthSlider.addChangeListener(new boardLengthSliderListener());

      SliderHelper.setSliderAttibutes(boardLengthSlider);

   }

   public void recalculateWinningSizeSlider(int maximumWinningSize)
   {
      winningSizeSlider.setMaximum(maximumWinningSize);
   }

   public void changeElementsEnable(boolean isEnable)
   {
      boardLengthSlider.setEnabled(isEnable);
      winningSizeSlider.setEnabled(isEnable);
      boardLengthSliderResultTextField.setEnabled(isEnable);
      winningSizeResultTextField.setEnabled(isEnable);

      XisFirsRadioBtn.setEnabled(isEnable);
      OisFirstRadioBtn.setEnabled(isEnable);
   }

   private class boardLengthSliderListener implements ChangeListener
   {
      @Override
      public void stateChanged(ChangeEvent e)
      {
         JSlider sds = (JSlider) e.getSource();
         int ileButtonow = sds.getValue();
         boardLengthSliderResultTextField.setText(String.valueOf(ileButtonow));
         gamePanel.resizeX(ileButtonow);
         recalculateWinningSizeSlider(gamePanel.getMaxWinningSize());
         validate();
      }
   }

   private void setWinningSizeSliderAttibutes()
   {
      winningSizeSlider = new JSlider(JSlider.HORIZONTAL, 3, 3, 3);
      winningSizeSlider.addChangeListener(new WinningSizeSliderListener());
      winningSizeSlider.setMinimum(3);
      recalculateWinningSizeSlider(GamePanel.DEFAULT_MAXIMUM_WINNING_SIZE);
      SliderHelper.setSliderAttibutes(winningSizeSlider);
   }

   private class WinningSizeSliderListener implements ChangeListener
   {
      @Override
      public void stateChanged(ChangeEvent e)
      {
         JSlider source = (JSlider) e.getSource();
         int winningSize = source.getValue();
         gamePanel.setWinningSize(winningSize);
         winningSizeResultTextField.setText(String.valueOf(winningSize));
         validate();

      }
   }

}
