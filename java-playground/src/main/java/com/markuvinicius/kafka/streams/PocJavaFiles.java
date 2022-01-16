package com.markuvinicius.kafka.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PocJavaFiles {

    public static void main(String[] args) throws IOException {
        String filesDir = args[0];
        walkFiles(filesDir,Integer.MAX_VALUE);
    }

    public static void walkFiles(String path, int depth) throws IOException {
      Files.walk(Paths.get(path),depth).forEach(s -> {
          if ( Files.isDirectory(s) ) {
              System.out.println("directory: " + s.toString());
          }else
              System.out.println("file: " + s.toString());
      });
    }
}
