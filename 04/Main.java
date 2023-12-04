import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;
import static java.util.stream.Collectors.*;


public class Main{

     public static   HashSet<Integer> findIntegers(String stringToSearch) {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(stringToSearch);

            HashSet<Integer> numbers = new HashSet<>();
            while (matcher.find()) {
                String numberString = matcher.group();
                int number = Integer.parseInt(numberString);
                numbers.add(number);
            }
            return numbers;
        }

    public static int calculatePoints(Set<Integer> winners){
        if(winners.isEmpty())  return 0;
        int sum = 1;
        if(winners.size() ==1) return sum;
        for(int i = 1;  i< winners.size(); i++){
            sum*=2;
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        var part1 = false;
        String path = "input.txt" ;
        var sum = Files.readAllLines(Paths.get(path)).stream()
                .map(line -> {
                    String lineWithoutCard = line.split(":")[1];
                    String winnersString =  lineWithoutCard.split("\\|")[0];
                    HashSet<Integer> winners = findIntegers(winnersString);
                    String myCardsString =  lineWithoutCard.split("\\|")[1];
                    HashSet<Integer> myNumbers = findIntegers(myCardsString);
                    myNumbers.retainAll(winners);
                    int calculatePoints = calculatePoints(myNumbers);
                    System.out.println("%s %d".formatted(myNumbers, calculatePoints));
                    return calculatePoints;
                }) 
                .mapToInt(line -> line)
                .sum();
        System.out.println(sum);
    }
}
