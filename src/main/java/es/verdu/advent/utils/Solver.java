package es.verdu.advent.utils;

import es.verdu.advent.utils.reader.AutomatedFileReader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Data
@Slf4j
public class Solver<C> {

    private final Class<C> clazz;

    private AutomatedFileReader automatedFileReader = new AutomatedFileReader();

    public <T> void handleProblem(Function<List<String>, T> solve) {
        List<String> lines = automatedFileReader.readList(clazz);

        T res = solve.apply(lines);
        log.info("Result: {}", res);

    }

    public <T> void handleProblemLine(Function<String, T> solve) {
        String line = automatedFileReader.readLine(clazz);

        T res = solve.apply(line);
        log.info("Result: {}", res);
    }
}
