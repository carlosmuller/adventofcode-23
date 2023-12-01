import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

class Main {
    record StringToDigit(String from, String to) {
    }

    final static Map<String, Character> stringToNumber = Map.of(
            // "zero", '0',
            "one", '1',
            "two", '2',
            "three", '3',
            "four", '4',
            "five", '5',
            "six", '6',
            "seven", '7',
            "eight", '8',
            "nine", '9');

    public static void reset(HashSet<Character> hash) {
        hash.add('z');
        hash.add('o');
        hash.add('t');
        hash.add('f');
        hash.add('s');
        hash.add('e');
        hash.add('n');
    }

    public static String getReplacedString(String line) {
        StringBuilder replacedString = new StringBuilder();
        HashSet<Character> nextPossible = new HashSet<Character>();

        for (int i = 0; i < line.length(); i++) {
            char possibleDigit = line.charAt(i);
            if (Character.isDigit(possibleDigit)) {
                replacedString.append(possibleDigit);
                continue;
            }
            if (nextPossible.contains(possibleDigit)) {
                
            } else {
                reset(nextPossible);
            }
        }
        return replacedString.toString();
    }

    public static Character[] resolve(String line) {
        var chars = line.toCharArray();
        boolean firstDigit = true;
        Character[] response = new Character[2];
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                if (firstDigit) {
                    firstDigit = false;
                    response[0] = chars[i];
                    response[1] = chars[i];
                } else {
                    response[1] = chars[i];
                }
            }
        }
        return response;
    }

    static Character findNumberInString(String substring) {
        try {
            Integer.parseInt(substring);
            return substring.charAt(0);
        } catch (NumberFormatException ex) {
            return stringToNumber.get(substring);
        }
    }

    public static Character[] resolvePartTwo(String line) {
        Character[] response = new Character[2];
        boolean shouldBreak =  false;
        for (int i = 0; i < line.length(); i++) {
            for (int j = i; j < line.length(); j++) {
                Character maybeMatch = findNumberInString(line.substring(i, j + 1));
                if (maybeMatch != null) {
                    shouldBreak = true;
                    response[0] = maybeMatch;
                    break ;
                }
            }
            if (shouldBreak) {
                break;
            }
        }
        shouldBreak = false;

        for (int i = line.length() - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                Character maybeMatch = findNumberInString(line.substring(j, i + 1));
                if (maybeMatch != null) {
                    shouldBreak = true;
                    response[1] = maybeMatch;
                    break;
                }
            }
            if (shouldBreak) {
                break;
            }
        }
        System.out.println(line + " " + response[0] + response[1]);
        return response;
    }

    public static void main(String[] args) throws IOException {
        var part1 = false;
        var sum = Files.readAllLines(Paths.get("input-part2.txt")).stream()
                .map(line -> {
                    if (part1) {
                        var solutionForLine = resolve(line);
                        return new String(solutionForLine[0] + "" + solutionForLine[1]);
                    }
                    var solutionForLine = resolvePartTwo(line);
                    return new String(solutionForLine[0] + "" + solutionForLine[1]);
                })
                .mapToInt(line -> Integer.parseInt(line))
                .sum();
        System.out.println(sum);
    }
}