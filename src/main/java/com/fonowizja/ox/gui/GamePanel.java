package com.fonowizja.ox.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.fonowizja.ox.automatic_machine_game.AutomaticMachineServiceImpl;
import com.fonowizja.ox.game_elements.FieldIsNotEmptyException;
import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.GameElementsServiceImpl;
import com.fonowizja.ox.game_elements.IllegalSignException;
import com.fonowizja.ox.game_elements.Sign;
import com.fonowizja.ox.internationalization.MessageProvider;
import com.fonowizja.ox.internationalization.MessagesKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author krzysztof.kramarz
 */
@SuppressWarnings("ClassWithTooManyFields")
//todo powinna być final , ale nie pzejdzie wtedy Mokito
public  class GamePanel extends JPanel
{
   private static Logger logger = LogManager.getLogger();

   private static final long serialVersionUID = -2348212344905788119L;
   static final int MAXIMUM_BOARD_LENGTH = 30;
   static final int MINIMUM_BOARD_LENGTH = 3;
   static final int DEFAULT_WINNING_SIZE = 3;
   static final int DEFAULT_MAXIMUM_WINNING_SIZE = 3;
   static final int DEFAULT_BOARD_LENHTG = 3;
   static final int DEFAULT_BOARD_HEIGHT = 3;
   static final Sign DEFAULT_STARTING_SIGN = Sign.X;

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
   private Integer positionOnBoard;
   private boolean isDraw;
   private boolean isWinningMove;

   private TitledBorder gamePanelBorder;
   private String drawDialog;
   private String winnerDialog;
   private String automaticMessageDialog;
   @Setter(AccessLevel.PACKAGE)
   private RightSettingsPanel rightSettingsPanel;
   private boolean semiAutomaticGame;
   private boolean humanCanMakeMove;
   private AutomaticMachineServiceImpl automaticMachineService;
   private Sign semiGameMachinePlayer;
   private Sign semiGameHumanPlayer;
   private boolean semiGameFinished;

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
      gamePanelBorder = BorderFactory.createTitledBorder("TUTAJ SIĘ GRA!");
      gamePanelBorder.setTitleColor(Color.BLUE);
      gamePanelBorder.setBorder(new LineBorder(Color.ORANGE, 1));
      setBorder(gamePanelBorder);

      buttonsList = new ArrayList<>();
      createButtons(boardSize);
      validate();
      translateAllMessages();

      automaticMachineService = new AutomaticMachineServiceImpl();

   }

   void translateAllMessages()
   {
      gamePanelBorder.setTitle(MessageProvider.getInstance().getMessage(MessagesKey.GAME_PANEL_TITTLE));
      drawDialog = MessageProvider.getInstance().getMessage(MessagesKey.GAME_PANEL_DRAW_DIALOG);
      winnerDialog = MessageProvider.getInstance().getMessage(MessagesKey.GAME_PANEL_WINNER_DIALOG);
      automaticMessageDialog = MessageProvider.getInstance().getMessage(MessagesKey.GAME_PANEL_AUTOMATIC_WINNER_DIALOG);
   }

   private void createButtons(Integer boardSize)
   {
      buttonsList.clear();
      for (int i = 0; i < boardSize; i++)
      {
         JButton button = new JButton();
         button.setText(Sign.EMPTY.getSign());
         button.addActionListener(new gameButtonsListener());
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

   private class gameButtonsListener implements ActionListener
   {

      @Override
      public void actionPerformed(ActionEvent e)
      {
         JButton buttonClicked = (JButton) e.getSource();
         String buttonText = buttonClicked.getText();
         positionOnBoard = buttonsList.indexOf(buttonClicked);
         //todo refactor na switche,moze uzyć enumów na EnumTypePlayers HUMAN, MACHINE
         if (canPlayersPlay && !semiAutomaticGame)
         {

            if (Sign.X.getSign().equals(buttonText) || Sign.O.getSign().equals(buttonText))
            {
               return;
            }
            if (whoHasATurn == Sign.X)
            {
               buttonClicked.setText(Sign.X.getSign());

               makeMove(positionOnBoard);
            }
            else
            {
               buttonClicked.setText(Sign.O.getSign());
               makeMove(positionOnBoard);
            }
         }
         else if (canPlayersPlay && semiAutomaticGame)
         {

            if (humanCanMakeMove)
            {

               if (Sign.X.getSign().equals(buttonText) || Sign.O.getSign().equals(buttonText))
               {
                  return;
               }
               if (semiGameHumanPlayer == Sign.X)
               {
                  buttonClicked.setText(Sign.X.getSign());
                  makeMove(positionOnBoard);
                  humanCanMakeMove = false;
                  if (!semiGameFinished)
                  {
                     automaticMachineService.makeSemiAutomaticMove();
                  }

               }
               else if (semiGameHumanPlayer == Sign.O)
               {
                  buttonClicked.setText(Sign.O.getSign());
                  makeMove(positionOnBoard);
                  humanCanMakeMove = false;
                  if (!semiGameFinished)
                  {
                     automaticMachineService.makeSemiAutomaticMove();
                  }
               }
            }

         }
      }

   }

   /**
    * Make possible to act like click on button board
    * i.e. mark button by sign and check if it is winning move
    *
    * @param positionOnBoard
    *       where to play
    */
   public void playLikeHuman(Integer positionOnBoard)
   {
      try
      {
         Thread.sleep(500);
      }
      catch (InterruptedException e1)
      {
         logger.error(e1);
      }
      buttonsList.get(positionOnBoard).setText(semiGameMachinePlayer.getSign());
      makeMove(positionOnBoard);
      humanCanMakeMove = true;
   }

   private void makeMove(Integer positionOnBoard)
   {
      try
      {
         isWinningMove = gameElementsService.isWinningMove(whoHasATurn, positionOnBoard);
      }
      catch (FieldIsNotEmptyException e2)
      {
         logger.error(e2);
      }
      catch (IllegalSignException e)
      {
         logger.error(e);
      }
      isDraw = gameElementsService.isDraw();
      if (isDraw)
      {
         JOptionPane.showMessageDialog(null, drawDialog);
         resetButtons();
         changeAllElementsEnable(true);
      }

      if (isWinningMove)
      {
         JOptionPane.showMessageDialog(null, winnerDialog + whoHasATurn.getSign());
         resetButtons();
         changeAllElementsEnable(true);
         semiGameFinished = true;
      }
      else
      {
         whoHasATurn = whoHasATurn.getOppositePlayer();

      }
   }

   /**
    * Plays autoamtic test machine game
    * and presents moves on the gui board
    *
    * @param sign
    *       sign to put
    * @param positionOnBoard
    *       where put sign
    * @return
    */
   public boolean makeAutomaticTestMachineMove(Sign sign, Integer positionOnBoard)
   {

      buttonsList.get(positionOnBoard).setText(sign.getSign());
      validate();

      try
      {
         isWinningMove = gameElementsService.isWinningMove(sign, positionOnBoard);
      }
      catch (FieldIsNotEmptyException e)
      {
         logger.error(e);
      }
      catch (IllegalSignException e)
      {
         logger.error(e);
      }

      if (isWinningMove)
      {
         JOptionPane.showMessageDialog(null, automaticMessageDialog + whoHasATurn.getSign());
         resetButtons();
         return true;
      }
      else
      {
         return false;
      }

   }

   void startGame(Sign whoHasATurn, boolean canPlayersPlay)
   {
      resetButtons();
      semiAutomaticGame = false;
      this.whoHasATurn = whoHasATurn;
      this.canPlayersPlay = canPlayersPlay;
      isDraw = false;
      gameElementsService = new GameElementsServiceImpl(boardSize, boardLenght, winningSize);
   }

   void startSemiAutomaticGame(Sign whoHasATurn, boolean canPlayersPlay, boolean humanCanMakeMove)
   {
      semiGameFinished = false;
      resetButtons();
      this.humanCanMakeMove = humanCanMakeMove;
      semiAutomaticGame = true;
      this.whoHasATurn = whoHasATurn;
      this.canPlayersPlay = canPlayersPlay;
      if (whoHasATurn == Sign.O && humanCanMakeMove)
      {
         semiGameHumanPlayer = Sign.O;
         semiGameMachinePlayer = Sign.X;
      }
      else if (whoHasATurn == Sign.X && !humanCanMakeMove)
      {
         semiGameHumanPlayer = Sign.O;
         semiGameMachinePlayer = Sign.X;
      }
      else
      {
         semiGameHumanPlayer = Sign.X;
         semiGameMachinePlayer = Sign.O;
      }

      isDraw = false;
      gameElementsService = new GameElementsServiceImpl(boardSize, boardLenght, winningSize);
      automaticMachineService.createAutomaticMachine(gameElementsService, this);
      automaticMachineService.semiAutomaticGameStart(buttonsList, semiGameMachinePlayer, winningSize);

      if (!humanCanMakeMove)
      {
         automaticMachineService.makeSemiAutomaticMove();
         //noinspection UnusedAssignment
         humanCanMakeMove = true;
      }

   }

   void automaticTestMachineStart(Sign whoHasATurn)
   {
      resetButtons();
      semiAutomaticGame = false;
      this.whoHasATurn = whoHasATurn;
      canPlayersPlay = false;
      isDraw = false;
      gameElementsService = new GameElementsServiceImpl(boardSize, boardLenght, winningSize);
      automaticMachineService.createAutomaticMachine(gameElementsService, this);
      automaticMachineService.automaticTestMachineStart(whoHasATurn);

      // resetButtons();
   }

   /**
    * Validate all panel
    */
   public void validatePanel()
   {
      validate();
   }

   /**
    * Change if all adjustable elements are enabled
    *
    * @param isEnabled
    *       is enabled
    */
   public void changeAllElementsEnable(boolean isEnabled)
   {
      rightSettingsPanel.changeAllElementsEnable(isEnabled);

   }

}
