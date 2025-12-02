package es.verdu.advent.y2025;

import es.verdu.advent.utils.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Day2 {

    public static void main(String[] args) {
        Solver<Day2> solver = new Solver<>(Day2.class);
        solver.handleProblemLine(solve);
        solver.handleProblemLine(solve2);
    }

    private static final Function<String, Long> solve = line -> {

        List<String> idRanges = Arrays.stream(line.split(",")).toList();
        Long result = 0L;

        for (String range : idRanges) {
            List<String> ids = Arrays.stream(range.split("-")).toList();
            Long start = Long.parseLong(ids.getFirst());
            Long end = Long.parseLong(ids.getLast());

            for (long i = start; i <= end; i++) {
                if (isInvalid(i)) result += i;
            }
        }

        return result;
    };

    private static final Function<String, Long> solve2 = line -> {

        List<String> idRanges = Arrays.stream(line.split(",")).toList();
        Long result = 0L;

        for (String range : idRanges) {
            List<String> ids = Arrays.stream(range.split("-")).toList();
            Long start = Long.parseLong(ids.getFirst());
            Long end = Long.parseLong(ids.getLast());

            for (long i = start; i <= end; i++) {
                if (isInvalid2(i)) result += i;
            }
        }

        return result;
    };

    private static boolean isInvalid(Long id) {
        String stringId = id.toString();
        if (stringId.length() % 2 != 0) return false;
        String firstPart = stringId.substring(0, stringId.length() / 2);
        String secondPart = stringId.substring(stringId.length() / 2);
        return firstPart.equals(secondPart);
    }

    private static boolean isInvalid2(Long id) {
        String stringId = id.toString();
        List<Integer> divisors = findDivisors(stringId.length());
        boolean isInvalid = false;
        for (Integer divisor : divisors) {
            List<String> parts = split(stringId, divisor);
            isInvalid = parts.stream().allMatch(s -> s.equals(parts.getFirst()));

            if (isInvalid) break;
        }
        return isInvalid;
    }

    public static List<String> split(String text, Integer n) {
        String[] results = text.split("(?<=\\G.{" + n + "})");

        return Arrays.asList(results);
    }

    public static List<Integer> findDivisors(Integer number) {
        List<Integer> divisors = new ArrayList<>();
        for (int i = number - 1; i > 0; i--) {
            if (number % i == 0) divisors.add(i);
        }

        return divisors;
    }
}
