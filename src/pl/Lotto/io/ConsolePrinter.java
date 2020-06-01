package pl.Lotto.io;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConsolePrinter {
    public void printText(String text) {
        System.out.println(text);
    }

    public void printLottoResults(List<Integer> types,List<Integer> getResults, int result) {
        System.out.println("Twoje liczby: " + types);
        System.out.println("Wynik losowania: " + getResults);
        System.out.println("Liczba trafień: " + result);
    }
    public void printFromSerialFile(Map<LocalDateTime, LottoResultsStore> lottoAllResults) {
        Set set = lottoAllResults.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key: "+ mentry.getKey() + " & Value: ");
            System.out.println(mentry.getValue());
        }
    }
}
