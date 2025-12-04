package es.verdu.advent.y2025;

import es.verdu.advent.utils.Solver;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class Day3 {

    public static void main(String[] args) {
        Solver<Day3> solver = new Solver<>(Day3.class);
        solver.handleProblem(solve(2));
        solver.handleProblem(solve(12));
    }

    private static Function<List<String>, Long> solve(int size) {
        return lines -> {

            long result = 0L;

            for (String line : lines) {
                List<Integer> bank = Arrays.stream(line.split("")).map(Integer::parseInt).toList();
                result += Long.parseLong(findCombo(bank, size));
            }

            return result;
        };
    }

    private static String findCombo(List<Integer> bank, int size) {
        return findCombo("", bank, size);
    }

    private static String findCombo(String actual, List<Integer> bank, int size) {
        Integer counter = 9;
        boolean found = false;
        int index = -1;
        int remainingLength = size - actual.length();
        while (!found) {
            index = bank.indexOf(counter);
            if (index != -1 && index < bank.size() - remainingLength + 1) {
                found = true;
            } else counter--;
        }

        if (remainingLength == 1) return actual + counter;

        List<Integer> newBank = bank.subList(index + 1, bank.size());
        return findCombo(actual + counter, newBank, size);
    }
}
