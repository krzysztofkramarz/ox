package com.fonowizja.ox.game_elements;

import lombok.Getter;

/**
 * @author krzysztof.kramarz
 */
@Getter
public enum Sign
{
   O("o"),
   X("x"),
   EMPTY(" ");

   private String sign;

   Sign(String sign)
   {
      this.sign = sign;
   }
}
