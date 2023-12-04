import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;
import static java.util.stream.Collectors.*;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Main{
    private static final AtomicInteger i = new AtomicInteger(0);
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
        HashMap<Integer, AtomicInteger> cards = new HashMap<>();
        var lines = Files.readAllLines(Paths.get(path));
        int k = 1 ;
        for (String string : lines) {
            AtomicInteger currentCard = cards.computeIfAbsent(k, l -> new AtomicInteger(1)); 
            k++;
        }
        var sum = lines.stream()
                .map(line -> {
                    int index = i.incrementAndGet();
                    AtomicInteger currentCard = cards.get(index); 
                    String lineWithoutCard = line.split(":")[1];
                    String winnersString =  lineWithoutCard.split("\\|")[0];
                    HashSet<Integer> winners = findIntegers(winnersString);
                    String myCardsString =  lineWithoutCard.split("\\|")[1];
                    HashSet<Integer> myNumbers = findIntegers(myCardsString);
                    myNumbers.retainAll(winners);
                    int calculatePoints = calculatePoints(myNumbers);
                    // System.out.println("Part 1: %s %d Part 2: %d index value for current %s".formatted(myNumbers, myNumbers.size(), index, cards.get(0)));
                    // System.out.println("Part:2 index %d, mynumber.size() %d".formatted(index, myNumbers.size()));
                    // if(!myNumbers.isEmpty()){
                    //     currentCard.incrementAndGet();
                    // };
                    LinkedList<Integer> toBepoped = new LinkedList<>(myNumbers);
                    int j =1 ;
                    while (!toBepoped.isEmpty()&&toBepoped.remove() != null) {
                        cards.computeIfAbsent((j+index), l -> new AtomicInteger()).addAndGet(currentCard.get());
                        j++;
                    }
                    // for (Map.Entry<Integer, AtomicInteger>  card : cards.entrySet()) {
                    //     System.out.println("Line: %d cards total %d".formatted(card.getKey(), card.getValue().get()));
                    // }
                    return calculatePoints;
                }) 
                .mapToInt(line -> line)
                .sum();
        System.out.println("Part 1 " +sum);
        for (Map.Entry<Integer, AtomicInteger>  card : cards.entrySet()) {
            System.out.println("Line: %d cards total %d".formatted(card.getKey(), card.getValue().get()));
        }
        int totalCard = cards.values().stream().mapToInt(AtomicInteger::get).sum();
        System.out.println("Part 2: " +totalCard);
    }
}
