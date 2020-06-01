package pl.Lotto.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LottoGame implements Serializable {
    private List<Integer> numbers = new ArrayList<>();
    private List<Integer> lottoResult;
    public List<Integer> lottoUserTypes;
    private static int maxNumberValue = 49;
    private static int numbersCount = 6;

    private ConsolePrinter printer = new ConsolePrinter();

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public List<Integer> getLottoResult() {
        return lottoResult;
    }

    public void setLottoResult(List<Integer> lottoResult) {
        this.lottoResult = lottoResult;
    }

    public List<Integer> getLottoUserTypes() {
        return lottoUserTypes;
    }

    public void setLottoUserTypes(List<Integer> lottoUserTypes) {
        this.lottoUserTypes = lottoUserTypes;
    }

    public static int getMaxNumberValue() {
        return maxNumberValue;
    }

    public static void setMaxNumberValue(int maxNumberValue) {
        LottoGame.maxNumberValue = maxNumberValue;
    }

    public static int getNumbersCount() {
        return numbersCount;
    }

    public static void setNumbersCount(int numbersCount) {
        LottoGame.numbersCount = numbersCount;
    }

    public ConsolePrinter getPrinter() {
        return printer;
    }

    public void setPrinter(ConsolePrinter printer) {
        this.printer = printer;
    }

    public void generate() {
        for (int i = 1; i < maxNumberValue; i++) {
            numbers.add(i);

        }

    }



    public void randomize() {
        Collections.shuffle(numbers);
        lottoResult = numbers.subList(0, 6);

    }


    public List<Integer> typeNumbers() {
        boolean readComplete = false;

        List<Integer> userNumbers = new ArrayList<>();
        Scanner inputNumber = new Scanner(System.in);
        while (!readComplete) {
            System.out.println();
            printer.printText("Wprowadź liczbę z zakresu 1 - 49");
            String nextString = inputNumber.nextLine();

            if (!isInteger(nextString)) {
                continue;
            }
            int nextNumber = Integer.parseInt(nextString);

            if (nextNumber <= maxNumberValue) {
                userNumbers.add(nextNumber);
            } else printer.printText("Wprowadzono błędną wartość");

            if (userNumbers.size() >= numbersCount) {
                readComplete = true;
            }

        }
        lottoUserTypes = userNumbers;
        return lottoUserTypes;
    }

    public int checkResult(List<Integer> loteryNumbers) {
        int found = 0;
        try {
            for (int i = 0; i < numbersCount; i++) {

                if (loteryNumbers.contains(lottoResult.get(i))) {
                    found++;

                }
            }
        } catch (IndexOutOfBoundsException ee) {
            System.err.println(ee);
        }

        return found;
    }


    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
