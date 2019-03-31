package com.fonowizja.ox.game_elements;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author krzysztof.kramarz
 */
@Getter(AccessLevel.PUBLIC)
public enum Sign
{
   O("o")
         {
            @Override
            public Sign getOppositePlayer()
            {
               return X;
            }
         },
   X("x")
         {
            @Override
            public Sign getOppositePlayer()
            {
               return O;
            }
         },
   EMPTY(" ")
         {
            @Override
            public Sign getOppositePlayer()
            {
               return EMPTY;
            }
         };

   private final String sign;

   Sign(String sign)
   {
      this.sign = sign;
   }

   public abstract Sign getOppositePlayer();

}
