package es.verdu.advent.utils.reader;

import es.verdu.advent.utils.Constants;
import es.verdu.advent.utils.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.StringJoiner;

public class AutomatedFileReader extends FileReader {

    public <T> List<String> readList(Class<T> clazz) {
        return readList(getPath(clazz));
    }

    public <T> String readLine(Class<T> clazz) {
        return readLine(getPath(clazz));
    }

    private List<String> readList(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        try {
            return this.readLinesFromInputStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readLine(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        try {
            return this.readFromInputStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> String getPath(Class<T> clazz) {
        StringJoiner strJoiner = new StringJoiner("/");
        strJoiner.add(Constants.INPUT_DIR_NAME); //input
        strJoiner.add(Parser.yearParser(clazz.getPackageName()));// year
        strJoiner.add(clazz.getSimpleName().toLowerCase()); //day
        strJoiner.add(Constants.INPUT_FILE_NAME); //input

        return strJoiner.toString();
    }
}
