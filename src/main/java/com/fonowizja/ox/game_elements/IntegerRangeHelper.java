package com.fonowizja.ox.game_elements;

import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author krzysztof.kramarz
 */
class IntegerRangeHelper
{

   static List<Integer> generateRangeOfSearching(Integer startingPosition, IntUnaryOperator intUnaryOperator, Integer limit)
   {
      return IntStream.iterate(startingPosition, intUnaryOperator)
            .limit(limit)
            .boxed()
            .collect(Collectors.toList());
   }
}
