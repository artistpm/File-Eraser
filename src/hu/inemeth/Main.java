package hu.inemeth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    /**
     * .*\\.meta
     *
     * @param args
     */
    public static void main(String[] args) {

        for(String s : args){
            System.out.println(s);
        }

        if(null == args[0])
            return;

        String directory = args[0];

        System.out.println("File Names : ");
        if(null == args[1])
            return;
        printFileNames(directory, args[1]);

        System.out.println("Folder Names : ");
        printFolderNames(directory);

        System.out.println("Filtering name by a pattern \"Line\": ");
        filterByPattern(directory);

    }

    private static void printFileNames(String directory, String extension) {

        try (Stream<Path> walk = Files.walk(Paths.get(directory))) {

            List<String> fileNamesList = walk
                    .filter(Files::isRegularFile)
                    .map(x -> x.toString())
                    .collect(Collectors.toList());

            List<String> collectedFiles = fileNamesList
                    .stream()
                    .filter(file ->
                    {
                        System.out.println("Current file name is " + Paths.get(file).getFileName());
                        //return file.matches(".*\\.meta");
                        return file.matches(extension);
                    })
                    .collect(Collectors.toList());


            collectedFiles.forEach(file -> {
                System.out.printf("File (" + Paths.get(file).getFileName() + ") is going to be deleted.");
                Paths.get(file).toFile().delete();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printFolderNames(String directory) {

        // Reading the folder and getting Stream.
        try (Stream<Path> walk = Files.walk(Paths.get(directory))) {

            // Filtering the paths by a folder and adding into a list.
            List<String> folderNamesList = walk.filter(Files::isDirectory).map(x -> x.toString())
                    .collect(Collectors.toList());

            // printing the folder names
            folderNamesList.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void filterByPattern(String directory) {

        // Reading the folder and getting Stream.
        try (Stream<Path> walk = Files.walk(Paths.get(directory))) {

            // Filtering the paths by a folder and adding into a list.

            List<String> fileNamesList = walk.map(x -> x.toString()).filter(f -> f.contains("Line"))
                    .collect(Collectors.toList());

            // printing the folder names
            fileNamesList.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
