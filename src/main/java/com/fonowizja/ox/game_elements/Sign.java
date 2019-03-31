package com.fonowizja.ox.game_elements;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author krzysztof.kramarz
 */
@Getter(AccessLevel.PUBLIC)
public enum Sign
{
   O("o"),
   X("x"),
   EMPTY(" ");

   private final String sign;

   Sign(String sign)
   {
      this.sign = sign;
   }

}
