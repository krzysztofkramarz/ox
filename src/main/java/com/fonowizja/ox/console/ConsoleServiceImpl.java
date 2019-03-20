package com.fonowizja.ox.console;

/**
 * @author krzysztof.kramarz
 */
class ConsoleServiceImpl implements ConsoleService
{
   @Override
   public void printBoard(String board)
   {
      System.out.println(board);
   }
}
