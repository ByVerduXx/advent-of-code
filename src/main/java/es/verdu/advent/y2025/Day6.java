package es.verdu.advent.y2025;

import es.verdu.advent.utils.Solver;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Day6 {
    public static void main(String[] args) {
        Solver<Day6> solver = new Solver<>(Day6.class);
        solver.handleProblem(solve);
        solver.handleProblem(solve2);
    }

    private static final Function<List<String>, Long> solve = lines-> {
        Long result = 0L;
        List<String> ops = Arrays.stream(lines.getLast().split(" ")).map(String::trim).filter(s -> !s.isBlank()).toList();
        List<List<Integer>> numbers = lines.subList(0, lines.size() - 1)
                .stream()
                .map(line -> Arrays.stream(line.split(" ")).filter(s -> !s.isBlank()).map(number -> Integer.valueOf(number.trim())).toList())
                .toList();

        for (int i = 0; i < numbers.getFirst().size(); i++) {
            List<Integer> list = new ArrayList<>();
            for (List<Integer> row : numbers) {
                list.add(row.get(i));
            }

            result += op(ops.get(i), list);
        }

        return result;
    };

    private static final Function<List<String>, Long> solve2 = lines-> {
        Long result = 0L;
        List<String> ops = Arrays.stream(lines.getLast().split(" ")).map(String::trim).filter(s -> !s.isBlank()).toList();
        List<Integer> opsPosition = new ArrayList<>();

        for (int i = 0; i < lines.getLast().length(); i++) {
            if (lines.getLast().charAt(i) == '+' || lines.getLast().charAt(i) == '*') {
                opsPosition.add(i);
            }
        }
        List<String> problemRows = lines.subList(0, lines.size() - 1);

        List<List<Integer>> problems = new ArrayList<>();

        for (int i = 1; i <= opsPosition.size(); i++) {
            List<String> currentProblemString = new ArrayList<>();
            for (String row : problemRows) {
                String currentOp;
                if (i == opsPosition.size()) {
                    currentOp = row.substring(opsPosition.get(i - 1));
                } else {
                    currentOp = row.substring(opsPosition.get(i - 1), opsPosition.get(i) - 1);
                }
                currentProblemString.add(currentOp);
            }

            List<Integer> currentProblem = new ArrayList<>();

            for (int j = currentProblemString.getFirst().length() - 1; j >= 0; j--) {
                StringBuilder arrangedOp = new StringBuilder();
                for (String s : currentProblemString) {
                    char ch = s.charAt(j);
                    if (ch == ' ') continue;
                    arrangedOp.append(ch);
                }

                currentProblem.add(Integer.parseInt(arrangedOp.toString()));
            }

            problems.add(currentProblem);
        }

        for (int i = 0; i < problems.size(); i++) {
            result += op(ops.get(i), problems.get(i));
        }

        return result;
    };


    private static Long op(String op, List<Integer> list) {
        List<Long> longs = list.stream().map(Long::valueOf).toList();
        return op.equals("*") ? times(longs) : sum(longs);
    }

    private static Long sum(List<Long> list) {
        return list.stream().reduce(Long::sum).orElse(0L);
    }

    private static Long times(List<Long> list) {
        return list.stream().reduce((x, y) -> x * y).orElse(0L);
    }
}
