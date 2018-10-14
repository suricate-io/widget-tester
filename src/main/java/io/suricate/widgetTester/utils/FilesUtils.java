package io.suricate.widgetTester.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class FilesUtils {


    /**
     * Method used to list all folder inside a root folder
     * @param rootFolder the root folder used to find folder
     * @return the list of folder
     * @throws IOException exeception with file
     */
    public static List<File> getFolders(File rootFolder) throws IOException {
        if (rootFolder != null) {
            try (Stream<Path> list = Files.list(rootFolder.toPath())) {
                return list.filter(Files::isDirectory)
                        .map(Path::toFile)
                        .sorted()
                        .collect(Collectors.toList());

            }
        }
        return null;
    }

    /**
     * Method used to list all files inside a root folder
     * @param rootFolder the root folder used to find files
     * @return the list of folder
     * @throws IOException exception with file
     */
    public static List<File> getFiles(File rootFolder) throws IOException {
        if (rootFolder != null) {
            try (Stream<Path> list = Files.list(rootFolder.toPath())) {
                return list.filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toList());
            }
        }
        return null;
    }

    private FilesUtils() {
    }
}
