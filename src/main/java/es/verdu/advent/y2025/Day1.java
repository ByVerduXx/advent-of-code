package es.verdu.advent.y2025;

import es.verdu.advent.utils.Solver;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
public class Day1 {

    public static void main(String[] args) {
        Solver<Day1> solver = new Solver<>(Day1.class);
        solver.handleProblem(solve);
        solver.handleProblem(solve2);
    }

    private static final Function<List<String>, Integer> solve = lines -> {

        Map<String, BiFunction<Integer, Integer, Integer>> actions = Map.of(
                "R", (current, adder) ->  (current + adder) % 100,
                "L", (current, sub) ->  ((current - sub) % 100 + 100) % 100
        );

        Integer currentPos = 50;
        Integer password = 0;

        for (String line : lines) {
            String command = line.substring(0, 1);
            Integer quantity = Integer.valueOf(line.substring(1));

            currentPos = actions.get(command).apply(currentPos, quantity);

            if (currentPos.equals(0)) password += 1;
        }

        return password;
    };

    /*private static final Function<List<String>, Integer> solve2 = lines -> {

        Map<String, BiFunction<Integer, Integer, Integer>> actions = Map.of(
                "R", (current, adder) ->  (current + adder) % 100,
                "L", (current, sub) ->  (current - sub + 100) % 100
        );

        Integer currentPos = 50;
        Integer password = 0;

        for (String line : lines) {
            String command = line.substring(0, 1);
            Integer quantity = Integer.valueOf(line.substring(1));
            for (int i = 0; i < quantity; i++) {
                currentPos = actions.get(command).apply(currentPos, 1);

                if (currentPos.equals(0)) password += 1;
            }


        }

        return password;
    };*/


    private static final Function<List<String>, Integer> solve2 = lines -> {

        Map<String, BiFunction<Integer, Integer, Pair<Integer, Integer>>> actions = Map.of(
                "R", (current, adder) ->  {
                    Integer res = (current + adder) % 100;
                    Integer addition = (current + adder) / 100;

                    return new Pair<>(res, addition);
                },
                "L", (current, sub) ->  {
                    Integer res = ((current - sub) % 100 + 100) % 100;
                    Integer addition = current > sub ? 0 : (Math.abs(current - sub) + 100) / 100;
                    if (current == 0) addition -= 1;
                    return new Pair<>(res, addition);
                }
        );

        Integer currentPos = 50;
        Integer password = 0;

        for (String line : lines) {
            String command = line.substring(0, 1);
            Integer quantity = Integer.valueOf(line.substring(1));

            Pair<Integer, Integer> calc = actions.get(command).apply(currentPos, quantity);
            currentPos = calc.getValue0();

            password += calc.getValue1();
        }

        return password;
    };

}
