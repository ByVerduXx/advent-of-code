package es.verdu.advent.y2025;

import es.verdu.advent.utils.Solver;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class Day7 {

    private final static char VOID_CHAR = '.';
    private final static char BEAM_CHAR = '|';
    private final static char SPLITTER_CHAR = '^';

    public static void main(String[] args) {
        Solver<Day7> solver = new Solver<>(Day7.class);
        solver.handleProblem(solve);
        solver.handleProblem(solve2);
    }

    private static final Function<List<String>, Integer> solve = lines-> {

        List<String> mutableLines = new ArrayList<>(lines);

        Integer result = 0;

        StringBuilder builder = new StringBuilder(mutableLines.get(1));
        builder.setCharAt(mutableLines.getFirst().indexOf('S'), BEAM_CHAR);
        mutableLines.set(1, builder.toString());

        for (int i = 2; i < mutableLines.size(); i++) {
            StringBuilder line = new StringBuilder(mutableLines.get(i));
            for (int j = 0; j < mutableLines.get(i).length(); j++) {
                if (mutableLines.get(i-1).charAt(j) == BEAM_CHAR) {
                    if (mutableLines.get(i).charAt(j) == VOID_CHAR) {
                        line.setCharAt(j, BEAM_CHAR);
                    }
                    else if (mutableLines.get(i).charAt(j) == SPLITTER_CHAR) {
                        result++;
                        line.setCharAt(j - 1, BEAM_CHAR);
                        line.setCharAt(j + 1, BEAM_CHAR);
                    }
                }
            }
            mutableLines.set(i, line.toString());
        }

        return result;
    };

    private static final Function<List<String>, Long> solve2 = lines-> {

        List<String> mutableLines = new ArrayList<>(lines);

        StringBuilder builder = new StringBuilder(mutableLines.get(1));
        builder.setCharAt(mutableLines.getFirst().indexOf('S'), BEAM_CHAR);
        mutableLines.set(1, builder.toString());

        return backtrackingSolve(2, mutableLines, new HashMap<>());
    };

    private static Long backtrackingSolve(int level, List<String> currentLines, Map<List<Integer>, Long> splitterMap) {
        if (level == currentLines.size()) return 1L;

        Long result = 0L;

        int previousBeamIndex = currentLines.get(level - 1).indexOf(BEAM_CHAR);
        StringBuilder line = new StringBuilder(currentLines.get(level));

        List<String> linesToChange = new ArrayList<>(currentLines);

        if (currentLines.get(level).charAt(previousBeamIndex) == VOID_CHAR) {
            line.setCharAt(previousBeamIndex, BEAM_CHAR);
            linesToChange.set(level, line.toString());
            return backtrackingSolve(level + 1, linesToChange, splitterMap);
        }
        else {  //splitter

            Long splitterTimelines = splitterMap.get(List.of(level, previousBeamIndex));

            if (splitterTimelines != null) {
                return splitterTimelines;
            }

            //izquierda
            line.setCharAt(previousBeamIndex - 1, BEAM_CHAR);
            linesToChange.set(level, line.toString());
            result = result + backtrackingSolve(level + 1, linesToChange, splitterMap);

            //derecha
            line.setCharAt(previousBeamIndex - 1, VOID_CHAR);
            line.setCharAt(previousBeamIndex + 1, BEAM_CHAR);
            linesToChange.set(level, line.toString());
            result = result + backtrackingSolve(level + 1, linesToChange, splitterMap);

            splitterMap.put(List.of(level, previousBeamIndex), result);

        }

        return result;
    }
}
