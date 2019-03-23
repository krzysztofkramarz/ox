package com.fonowizja.ox.game_elements;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author krzysztof.kramarz
 */
@Getter(AccessLevel.PACKAGE)
enum Sign
{
   O("o"),
   X("x"),
   EMPTY(" ");

   private final String sign;

   Sign(String sign)
   {
      this.sign = sign;
   }

   public String getSign()
   {
      return sign;
   }}
