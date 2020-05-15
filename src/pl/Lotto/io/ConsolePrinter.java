package pl.Lotto.io;

import java.util.List;

public class ConsolePrinter {
    public void printText(String text) {
        System.out.println(text);
    }

    public void printLottoResults(List<Integer> types,List<Integer> getResults, int result) {
        System.out.println("Twoje liczby: " + types);
        System.out.println("Wynik losowania: " + getResults);
        System.out.println("Liczba trafień: " + result);
    }

}
