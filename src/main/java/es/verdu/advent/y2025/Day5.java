package es.verdu.advent.y2025;

import es.verdu.advent.utils.Solver;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Day5 {
    public static void main(String[] args) {
        Solver<Day5> solver = new Solver<>(Day5.class);
        solver.handleProblem(solve);
        solver.handleProblem(solve2);
    }

    private static final Function<List<String>, Integer> solve = lines-> {
        List<String> fresh = lines.subList(0, lines.indexOf(""));
        List<Long> available = lines.subList(lines.indexOf("") + 1, lines.size()).stream().map(Long::valueOf).toList();

        Integer result = 0;

        for (Long availableId : available) {
            for (String freshIdRange : fresh) {
                Long min = Long.parseLong(freshIdRange.substring(0, freshIdRange.indexOf("-")));
                Long max = Long.parseLong(freshIdRange.substring(freshIdRange.indexOf("-") + 1));

                if (isBetweenInclusive(min, max, availableId)) {
                    result++;
                    break;
                }
            }

        }

        return result;
    };

    private static final Function<List<String>, Long> solve2 = lines-> {
        List<List<Long>> fresh = lines.subList(0, lines.indexOf("")).stream().map(Day5::parseInterval).collect(Collectors.toList());
        Long result = 0L;
        int i = 0;

        do {

            List<Integer> toRemove = new ArrayList<>();

            for (int j = 0; j < fresh.size(); j++) {
                if (i == j) continue;
                List<Long> currentInterval = fresh.get(i);
                List<Long> checkedInterval = fresh.get(j);
                if (isBetweenInclusive(currentInterval.getFirst(), currentInterval.getLast(), checkedInterval.getFirst())) { // si el min del checked esta en el intervalo
                    Long newIntervalMin = currentInterval.getFirst();
                    Long newIntervalMax = currentInterval.getLast() > checkedInterval.getLast() ? currentInterval.getLast() : checkedInterval.getLast();
                    fresh.set(i, new ArrayList<>(Arrays.asList(newIntervalMin, newIntervalMax)));
                    toRemove.add(j);
                }

                else if (isBetweenInclusive(currentInterval.getFirst(), currentInterval.getLast(), checkedInterval.getLast())) { // si el max del checked esta en el intervalo
                    Long newIntervalMin = currentInterval.getFirst() < checkedInterval.getFirst() ? currentInterval.getFirst() : checkedInterval.getFirst();
                    Long newIntervalMax = currentInterval.getLast();
                    fresh.set(i, new ArrayList<>(Arrays.asList(newIntervalMin, newIntervalMax)));
                    toRemove.add(j);
                }
            }

            if (toRemove.isEmpty()) {
                i++;
            }

            for (int index : toRemove.reversed()) {
                fresh.remove(index);
            }

        } while (i < fresh.size());


        for (List<Long> interval : fresh) {
            result += interval.getLast() - interval.getFirst() + 1;
        }

        return result;
    };

    /*private static final Function<List<String>, Integer> solve2 = lines-> {
        List<String> fresh = lines.subList(0, lines.indexOf(""));
        Map<Long, Long> checkedIntervals = new HashMap<>();
        Integer result = 0;

        for (String freshIdRange : fresh) {
            Long min = Long.parseLong(freshIdRange.substring(0, freshIdRange.indexOf("-")));
            Long max = Long.parseLong(freshIdRange.substring(freshIdRange.indexOf("-") + 1));
            for (long i = min; i <= max; i++) {
                boolean isInMap = false;
                Iterator<Map.Entry<Long, Long>> intervals = checkedIntervals.entrySet().iterator();
                while(!isInMap && intervals.hasNext()) {
                    Map.Entry<Long, Long> interval = intervals.next();
                    isInMap = isBetweenInclusive(interval.getKey(), interval.getValue(), i);
                }
                if (!isInMap) result++;
            }

            checkedIntervals.put(min, max);
        }


        return result;
    };*/

    private static ArrayList<Long> parseInterval(String interval) {
        Long min = Long.parseLong(interval.substring(0, interval.indexOf("-")));
        Long max = Long.parseLong(interval.substring(interval.indexOf("-") + 1));
        return new ArrayList<>(Arrays.asList(min, max));
    }

    private static boolean isBetweenInclusive(Long min, Long max, Long value) {
        return value >= min && value <= max;
    }
}
