package oop.ex6.main;

import oop.ex6.checkfile.CheckFile;
import oop.ex6.main.exception.IllegalNumOfArgsException;
import oop.ex6.main.exception.NotSJavaFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Sjavac {

    public static void main(String[] args){
        CheckFile myChecker = new CheckFile();
        try{
            if (args.length != 1){
                throw new IllegalNumOfArgsException();
            }else {
                String fileName = args[0];
                String extension = "";
                int i = fileName.lastIndexOf('.');
                if (i >= 0) { extension = fileName.substring(i+1); }

                if (!extension.equals("sjava")){
                    throw new NotSJavaFileException();
                }
            }
        }catch (IllegalNumOfArgsException | NotSJavaFileException e) {
            System.out.println(2);
            return;
        }
        try {
            List<String> sJavaLines = Files.readAllLines(Paths.get(args[0]));
            myChecker.checkValid(sJavaLines);
        } catch (IOException e) {
            System.out.println(2);
        }
    }
}
