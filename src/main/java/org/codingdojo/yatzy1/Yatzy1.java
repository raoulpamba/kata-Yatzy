package org.codingdojo.yatzy1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Yatzy1 {

    public int chance() {
        return Arrays.stream(dice).sum();
    }

    public int yatzy() {
        if(chance() % 5 == 0 )
            return 50;
        return 0;
    }

    public int ones() {
        return sumDicewithSameGivenNumber(dice, 1);
    }

    public int twos() {
        return sumDicewithSameGivenNumber(dice, 2);
    }

    public int threes() {
        return sumDicewithSameGivenNumber(dice, 3);
    }

    public static int fours(int[] diceRoll) {
        return sumDicewithSameGivenNumber(diceRoll, 4);
    }

    public static int fives(int[] diceRoll) {
        return sumDicewithSameGivenNumber(diceRoll, 5);
    }

    public static int sixes(int[] diceRoll) {
        return sumDicewithSameGivenNumber(diceRoll, 6);
    }

    private static int sumDicewithSameGivenNumber(final int[] diceRoll, final int number)
    {
        return Arrays.stream(diceRoll)
                .filter(d -> d == number)
                .sum();
    }

    protected int[] dice;
    public Yatzy1() {}
    public Yatzy1(int d1, int d2, int d3, int d4, int d5)
    {
        super();
        dice = new int[5];
        dice[0] = d1;
        dice[1] = d2;
        dice[2] = d3;
        dice[3] = d4;
        dice[4] = d5;
    }

    public int score_pair() {
        final int[] sortedRoll = sortedInReverseOrder();
        for (int at = 1;  at < sortedRoll.length; at++) {
            if(sortedRoll[at-1] == sortedRoll[at])
                return sortedRoll[at] * 2;
        }
        return 0;
    }

    public int three_of_a_kind() {
        final int[] sortedRoll = sortedInReverseOrder();
        for (int at = 2;  at < sortedRoll.length; at++) {
            if(sortedRoll[at-2] == sortedRoll[at-1]
                    && sortedRoll[at-1] == sortedRoll[at])
                return sortedRoll[at] * 3;
        }
        return 0;
    }

    public int four_of_a_kind() {
        final int[] sortedRoll = sortedInReverseOrder();
        for (int at = 3;  at < sortedRoll.length; at++) {
            if(sortedRoll[at-3] == sortedRoll[at-2]
                    && sortedRoll[at-2] == sortedRoll[at-1]
                    && sortedRoll[at-1] == sortedRoll[at]
            )
                return sortedRoll[at] * 4;
        }
        return 0;
    }

    private int[] sortedInReverseOrder() {
        return Arrays.stream(dice)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public int two_pair() {
        Map<Integer, Long> countByNumber = Arrays.stream(dice)
                .boxed()
                .collect(Collectors.groupingBy(d -> d, Collectors.counting()));
        int size = countByNumber.entrySet().size();
        if (size != 2 && size != 3)
            return 0;
        int score = countByNumber.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 2 ||  entry.getValue() == 3)
                .mapToInt(Map.Entry::getKey)
                .sum();
        return score * 2;
    }

    public int smallStraight() {
        if (chance() == 15 )
            return 15;
        return 0;
    }

    public int largeStraight() {
        if (chance() == 20 )
            return 20;
        return 0;

    }

    public int fullHouse() {
        Map<Integer, Long> countByNumber = Arrays.stream(dice)
                .boxed()
                .collect(Collectors.groupingBy(d -> d, Collectors.counting()));
        if (countByNumber.entrySet().size() != 2)
            return 0;
        return countByNumber.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue().intValue())
                .sum();
    }
}


