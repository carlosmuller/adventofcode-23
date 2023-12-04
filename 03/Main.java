import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;

import static java.util.stream.Collectors.*;

public class Main {

    record Number(int line, int start, int end, int number, char c) {
    }

    static ArrayList<Number> numbers = new ArrayList<>();
    static Map<Integer, List<Number>> numberByLine = new HashMap<Integer, List<Number>>();

    public static char getNotDotChar(String string) {
        char first = '.';
        char last = '.';
        boolean f = false;
        for (char c : string.toCharArray()) {
            if (c != '.') {
                last = c;
                if (c == '*') {
                   return '*';
                }
                if(!f) {
                    first = c;
                }
            }
        }
        return first != last ? first : last;
    }

    public static long parte1(char[][] engine, List<String> allLines) {
        var sum = 0L;

        for (int i = 0; i < engine.length; i++) {
            String line = allLines.get(i);
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String numberString = matcher.group();
                int number = Integer.parseInt(numberString);
                int start = matcher.start();
                int end = matcher.end();
                // System.out.println("Linha %d: number %d start %d end %d".formatted(i, number, start, end));

                // char anterior
                if (start > 0 && engine[i][start - 1] != '.') {
                    // System.out.println(
                    //         "Linha %d: number: %d  char anterior: %s".formatted(i, number, engine[i][start - 1]));
                    sum += number;
                    numbers.add(new Number(i, start, end, number, engine[i][start - 1]));
                    continue;
                }
                // mesma linha próximo char
                if (end + 1 < line.length() && line.charAt(end) != '.') {
                    // System.out.println("Linha %d: number: %d  %d char posterior: %s".formatted(i, number,
                    //         end + 1, engine[i][end + 1]));
                    sum += number;
                    numbers.add(new Number(i, start, end, number, line.charAt(end)));
                    continue;
                }
                // sequencia embaixo do atual com diagonais
                if (i + 1 < engine.length) {
                    int startNextLine = start == 0 ? start : start - 1;
                    int endNextLine = end + 1 < line.length() ? end + 1 : end;
                    String substring = allLines.get(i + 1).substring(startNextLine, endNextLine);
                    if (!substring.matches("(\\.)+")) {
                        // System.out
                        //         .println("Linha %d: number: %d subtring da abaixo: %s".formatted(i, number, substring));
                        sum += number;

                        numbers.add(new Number(i, start, end, number,getNotDotChar(substring)));
                        continue;
                    }
                }
                // sequencia acima do atual com diagonais
                if (i > 0) {
                    int startNextLine = start == 0 ? start : start - 1;
                    int endNextLine = end + 1 < line.length() ? end + 1 : end;
                    String substring = allLines.get(i - 1).substring(startNextLine, endNextLine);
                    if (!substring.matches("(\\.)+")) {
                        // System.out
                        //         .println("Linha %d: number: %d subtring da acima: %s".formatted(i, number, substring));
                        sum += number;
                        numbers.add(new Number(i, start, end, number, getNotDotChar(substring)));
                        continue;
                    }
                }
            }
        }
        return sum;
    }

    public static long parte2(char[][] engine, List<String> allLines) {
        var sum = 0L;
        for (int i = 0; i < engine.length; i++) {
            String line = allLines.get(i);
            Pattern pattern = Pattern.compile("\\*");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                List<Number> numbers = new ArrayList<Number>();
                int start = matcher.start();
                int end = matcher.end();
                var numbersInSameLine = numberByLine.get(i);
                //checando na mesma linha
                if (numbersInSameLine != null) {
                    var possibleEngineParts = numbersInSameLine.stream().filter(n -> n.start == start - 1 || n.end == start - 1).toList();
                    numbers.addAll(possibleEngineParts);
                }
                var nextLineNumbers = numberByLine.get(i + 1);
                var beforeLines = numberByLine.get(i - 1);

                if (numbers.size() == 2) {
                    System.out.println("line %d: start %s %s".formatted(i, start, numbers));
                    sum = sum + (numbers.get(0).number * numbers.get(0).number);
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        String path = "input-sample.txt";
        List<String> allLines = Files.readAllLines(Paths.get(path));
        char[][] engine = new char[allLines.size()][allLines.get(0).length()];
        for (int i = 0; i < engine.length; i++) {
            engine[i] = allLines.get(i).toCharArray();
        }
        System.out.println("Part one: %d".formatted(parte1(engine, allLines)));
        numberByLine = numbers.stream().collect(groupingBy(Number::line));
        System.out.println(numberByLine);
        System.out.println("Part two: %d".formatted(parte2(engine, allLines)));
    }
}