import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;

public class Main {
    public static enum Colors {
        blue(14),
        red(12),
        green(13);

        private int max;

        private Colors(int max) {
            this.max = max;
        }

        boolean isbiggerThanMax(int qtde) {
            return qtde > max;
        }
    }

    public static class Color {
        public int min = Integer.MIN_VALUE;

        public void isBigger(int qtde){
                min = min < qtde? qtde : min;
            }

    }

    public static Integer getGameNumber(String line) {
        Pattern pattern = Pattern.compile("Game (\\d+):");
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return Integer.parseInt(matcher.group(1));
    }

    public static Matcher getMatches(String line) {
        Pattern pattern = Pattern.compile("((\\d+) (blue|red|green))");
        Matcher matcher = pattern.matcher(line);
        return matcher;
    }

    public static boolean isPossible(String line, boolean part1) {
        Matcher matcher = getMatches(line);
        while (matcher.find()) {
            if (Colors.valueOf(matcher.group(3)).isbiggerThanMax(Integer.parseInt(matcher.group(2)))) {
                return false;
            }
        }
        return true;
    }

    public static int getMinValuesMultiplied(String line, boolean part1){
        var blue = new Color();
        var green = new Color();
        var red = new Color();
        Matcher matcher = getMatches(line);
        while (matcher.find()) {
            switch(Colors.valueOf(matcher.group(3))){
                case Colors.blue -> blue.isBigger(Integer.parseInt(matcher.group(2)));
                case Colors.red ->  red.isBigger(Integer.parseInt(matcher.group(2)));
                case Colors.green ->  green.isBigger(Integer.parseInt(matcher.group(2)));
            }
        }
        System.out.println(line + " [" +  red.min +" red "+ green.min + " green "+ blue.min + " blue ] cube" + blue.min * red.min * green.min);
        return blue.min * red.min * green.min;
    }

    public static void main(String[] args) throws IOException {
        var part1 = false;
        String path = "input.txt" ;
        var sum = Files.readAllLines(Paths.get(path)).stream()
                .map(line -> {
                    Integer gameNumber = getGameNumber(line);
                    if (part1) {
                        boolean possible = isPossible(line, part1);
                        System.out.println(line + " " + possible);
                        return possible ? gameNumber : 0;
                    }
                    int cube = getMinValuesMultiplied(line, part1);
                   
                    return cube;

                })
                .mapToInt(line -> line)
                .sum();
        System.out.println(sum);
    }
}