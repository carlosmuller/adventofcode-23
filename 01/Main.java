import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Main {

    final static Map<String, Character> partTwo = new HashMap<>();
    final static Map<String, Character> partOne = new HashMap<>();
    static {
        partOne.put("1", '1');
        partOne.put("2", '2');
        partOne.put("3", '3');
        partOne.put("4", '4');
        partOne.put("5", '5');
        partOne.put("6", '6');
        partOne.put("7", '7');
        partOne.put("8", '8');
        partOne.put("9", '9');
        partTwo.putAll(partOne);
        partTwo.put("one", '1');
        partTwo.put("two", '2');
        partTwo.put("three", '3');
        partTwo.put("four", '4');
        partTwo.put("five", '5');
        partTwo.put("six", '6');
        partTwo.put("seven", '7');
        partTwo.put("eight", '8');
        partTwo.put("nine", '9');
    }

    static Character translateStringToNumber(String substring, Map<String, Character> translator) {
        return translator.get(substring);
    }

    public static Character[] genericResolverWithDoublePass(String line, Map<String, Character> translator) {
        Character[] response = new Character[2];
        boolean shouldBreak = false;
        for (int i = 0; i < line.length(); i++) {
            for (int j = i; j < line.length(); j++) {
                Character possibleNumber = translateStringToNumber(line.substring(i, j + 1), translator);
                if (possibleNumber != null) {
                    shouldBreak = true;
                    response[0] = possibleNumber;
                    break;
                }
            }
            if (shouldBreak) {
                break;
            }
        }
        shouldBreak = false;

        for (int i = line.length() - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                Character possibleNumber = translateStringToNumber(line.substring(j, i + 1), translator);
                if (possibleNumber != null) {
                    shouldBreak = true;
                    response[1] = possibleNumber;
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

    public static Character[] resolvePartTwoWithDoublePass(String line) {
        return genericResolverWithDoublePass(line, partTwo);
    }

    public static Character[] resolvePartTwoWithOnePass(String line) {
        var chars = line.toCharArray();
        boolean firstDigit = true;
        Character[] response = new Character[2];
        for (int i = 0; i < chars.length; i++) {
            for (int j = i; j < line.length(); j++) {
                Character possibleNumber = translateStringToNumber(line.substring(i, j + 1), partTwo);
                if (possibleNumber != null) {
                    if (firstDigit) {
                        firstDigit = false;
                        response[0] = possibleNumber;
                        response[1] = possibleNumber;
                    } else {
                        response[1] = possibleNumber;
                    }

                }
            }
        }
        System.out.println(line + " " + response[0] + response[1]);
        return response;
    }

    public static Character[] resolvePartOneWithMapAndDoublePass(String line) {
        return genericResolverWithDoublePass(line, partOne);
    }

    public static Character[] resolvePartOneWithoutMapAndOnePass(String line) {
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
        System.out.println(line + " " + response[0] + response[1]);
        return response;
    }

    public static Character[] resolvePartOneWithMapAndOnePass(String line) {
        var chars = line.toCharArray();
        boolean firstDigit = true;
        Character[] response = new Character[2];
        for (int i = 0; i < chars.length; i++) {
            Character number = translateStringToNumber(chars[i] + "", partOne);
            if (number != null) {
                if (firstDigit) {
                    firstDigit = false;
                    response[0] = number;
                    response[1] = number;
                } else {
                    response[1] = number;
                }
            }
        }
        System.out.println(line + " " + response[0] + response[1]);
        return response;
    }

    public static void main(String[] args) throws IOException {
        var part1 = false;
        String path = part1 ? "example-input.txt" : "input-part2.txt";
        var sum = Files.readAllLines(Paths.get(path)).stream()
                .map(line -> {
                    if (part1) {
                        var solutionForLine = resolvePartOneWithMapAndDoublePass(line);
                        return new String(solutionForLine[0] + "" + solutionForLine[1]);
                    }
                    var solutionForLine = resolvePartTwoWithOnePass(line);
                    return new String(solutionForLine[0] + "" + solutionForLine[1]);
                })
                .mapToInt(line -> Integer.parseInt(line))
                .sum();
        System.out.println(sum);
    }
}