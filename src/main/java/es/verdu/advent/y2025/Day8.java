package es.verdu.advent.y2025;

import es.verdu.advent.utils.Solver;
import org.javatuples.Triplet;

import java.util.*;
import java.util.function.Function;

public class Day8 {

    public static void main(String[] args) {
        Solver<Day8> solver = new Solver<>(Day8.class);
        solver.handleProblem(solve);
        solver.handleProblem(solve2);
    }

    private static final Function<List<String>, Integer> solve = lines-> {
        List<List<Integer>> points = lines.stream().map(line -> Arrays.asList(Arrays.stream(line.split(",")).map(Integer::parseInt).toArray(Integer[]::new))).toList();
        List<Triplet<Integer,Integer,Double>> distanceList = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                distanceList.add(Triplet.with(i, j, euclideanDistance(points.get(i), points.get(j))));
            }
        }

        distanceList.sort(Comparator.comparing(Triplet::getValue2));

        List<List<Integer>> circuits = new ArrayList<>();
        Integer pairCounter = 0;

        while (pairCounter < 1000) {
            Triplet<Integer, Integer, Double> closestPoints = distanceList.getFirst();

            int firstCircuit = -1;
            int secondCircuit = -1;

            boolean sameCircuit = false;

            for (int i = 0; i < circuits.size(); i++) {
                List<Integer> circuit = circuits.get(i);
                if (circuit.contains(closestPoints.getValue0())) {
                    firstCircuit = i;
                }

                if (circuit.contains(closestPoints.getValue1())) {
                    secondCircuit = i;
                }

                if (circuit.contains(closestPoints.getValue0()) && circuit.contains(closestPoints.getValue1())) {
                    sameCircuit = true;
                }
            }

            if (!sameCircuit) {
                if (firstCircuit == -1 && secondCircuit == -1) {
                    circuits.add(new ArrayList<>(Arrays.asList(closestPoints.getValue0(), closestPoints.getValue1())));
                } else if (firstCircuit != -1 && secondCircuit == -1) {
                    circuits.get(firstCircuit).add(closestPoints.getValue1());
                } else if (firstCircuit == -1) {
                    circuits.get(secondCircuit).add(closestPoints.getValue0());
                } else {
                    circuits.get(firstCircuit).addAll(circuits.get(secondCircuit));
                    circuits.remove(secondCircuit);
                }
            }

            distanceList.removeFirst();
            pairCounter++;
        }

        circuits.sort((a, b) -> Integer.compare(b.size(), a.size()));

        return circuits.subList(0, 3).stream().map(List::size).reduce((a, b) -> a * b).orElse(0);
    };

    private static final Function<List<String>, Long> solve2 = lines-> {
        List<List<Integer>> points = lines.stream().map(line -> Arrays.asList(Arrays.stream(line.split(",")).map(Integer::parseInt).toArray(Integer[]::new))).toList();
        List<Triplet<Integer,Integer,Double>> distanceList = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                distanceList.add(Triplet.with(i, j, euclideanDistance(points.get(i), points.get(j))));
            }
        }

        distanceList.sort(Comparator.comparing(Triplet::getValue2));

        List<List<Integer>> circuits = new ArrayList<>();
        Integer pairCounter = 0;

        boolean stop = false;

        Long result = 0L;

        while (pairCounter < 10 || !stop) {
            Triplet<Integer, Integer, Double> closestPoints = distanceList.getFirst();

            int firstCircuit = -1;
            int secondCircuit = -1;

            boolean sameCircuit = false;

            for (int i = 0; i < circuits.size(); i++) {
                List<Integer> circuit = circuits.get(i);
                if (circuit.contains(closestPoints.getValue0())) {
                    firstCircuit = i;
                }

                if (circuit.contains(closestPoints.getValue1())) {
                    secondCircuit = i;
                }

                if (circuit.contains(closestPoints.getValue0()) && circuit.contains(closestPoints.getValue1())) {
                    sameCircuit = true;
                }
            }

            if (!sameCircuit) {
                if (firstCircuit == -1 && secondCircuit == -1) {
                    circuits.add(new ArrayList<>(Arrays.asList(closestPoints.getValue0(), closestPoints.getValue1())));
                } else if (firstCircuit != -1 && secondCircuit == -1) {
                    circuits.get(firstCircuit).add(closestPoints.getValue1());
                } else if (firstCircuit == -1) {
                    circuits.get(secondCircuit).add(closestPoints.getValue0());
                } else {
                    circuits.get(firstCircuit).addAll(circuits.get(secondCircuit));
                    circuits.remove(secondCircuit);
                }
            }

            if (circuits.size() == 1 && circuits.getFirst().size() == points.size()) {
                stop = true;
                result = ((long) points.get(closestPoints.getValue0()).getFirst() * points.get(closestPoints.getValue1()).getFirst());
            }

            distanceList.removeFirst();
            pairCounter++;
        }

        return result;
    };

    private static Double euclideanDistance(List<Integer> a, List<Integer> b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Points must have the same number of dimensions");
        }

        long sumOfSquares = 0;

        for (int i = 0; i < a.size(); i++) {
            long diff = a.get(i) - b.get(i);
            sumOfSquares += diff * diff;
        }

        return Math.sqrt(sumOfSquares);
    }
}
