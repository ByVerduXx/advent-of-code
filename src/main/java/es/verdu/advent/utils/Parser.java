package es.verdu.advent.utils;

public class Parser {

    /**
     * @param filePackage the package from which extract the year
     * @return the year in a package named some.other.package.yYYYY
     */
    public static String yearParser(String filePackage) {
        return filePackage.substring(filePackage.lastIndexOf('.') + 2); //.y2025 -> +2 to take only 2025
    }
}
