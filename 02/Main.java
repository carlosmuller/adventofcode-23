import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;
public class Main{
    public static enum Colors{
        blue(14),
        red(12),
        green(13);
        private int max;
        private Colors(int max){
            this.max = max;
        }
        boolean isbiggerThanMax(int qtde){
            return qtde > max;
        }
    }

    public static Integer getGameNumber(String line){
        Pattern pattern = Pattern.compile("Game (\\d+):");
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return Integer.parseInt(matcher.group(1));
    }

    public static boolean isPossible(String line, boolean part1){
        Pattern pattern = Pattern.compile("((\\d+) (blue|red|green))");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            if(Colors.valueOf(matcher.group(3)).isbiggerThanMax(Integer.parseInt(matcher.group(2)))){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        var part1 = true;
        String path = part1 ? "input-sample.txt" : "input-part2.txt";
        var sum = Files.readAllLines(Paths.get(path)).stream()
                .map(line -> {
                   Integer gameNumber =  getGameNumber(line);
                   boolean possible = isPossible(line,part1);
                   System.out.println(line +" " + possible);
                   return possible? gameNumber : 0;
                })
                .mapToInt(line -> line)
                .sum();
        System.out.println(sum);
    }
}