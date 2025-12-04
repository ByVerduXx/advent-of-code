package es.verdu.advent.y2025;

import es.verdu.advent.utils.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day4 {

    public static void main(String[] args) {
        Solver<Day4> solver = new Solver<>(Day4.class);
        solver.handleProblem(solve);
        solver.handleProblem(solve2);
    }

    private static final Function<List<String>, Integer> solve = lines-> {

        Integer result = 0;

        List<List<String>> map = lines.stream()
                .map(line -> new ArrayList<>(Arrays.asList(line.split(""))))
                .collect(Collectors.toList());

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.getFirst().size(); j++) {
                if (!Objects.equals(map.get(i).get(j), "@")) continue;
                if (countAdjacentRolls(i, j, map) < 4) result++;
            }
        }

        return result;

    };

    private static final Function<List<String>, Integer> solve2 = lines-> {
        List<List<String>> map = lines.stream()
                .map(line -> new ArrayList<>(Arrays.asList(line.split(""))))
                .collect(Collectors.toList());

        return loop(0, map);

    };

    private static Integer loop(Integer result, List<List<String>> map) {

        boolean stop = true;

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.getFirst().size(); j++) {
                if (!Objects.equals(map.get(i).get(j), "@")) continue;
                if (countAdjacentRolls(i, j, map) < 4) {
                    stop = false;
                    map.get(i).set(j, "X");
                    result++;
                }
            }
        }

        if (stop) return result;
        return loop(result, map);
    }

    private static int countAdjacentRolls(int i, int j, List<List<String>> map) {
        String rollChar = "@";
        int counter = 0;

        int rows = map.size();
        int cols = map.getFirst().size();

        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                { 0, -1},          { 0, 1},
                { 1, -1}, { 1, 0}, { 1, 1}
        };

        for (int[] d : directions) {
            int ni = i + d[0];
            int nj = j + d[1];

            if (ni >= 0 && ni < rows && nj >= 0 && nj < cols) {
                if (rollChar.equals(map.get(ni).get(nj))) {
                    counter++;
                }
            }
        }

        return counter;

    }
}
