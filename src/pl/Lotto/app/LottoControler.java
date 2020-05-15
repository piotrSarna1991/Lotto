package pl.Lotto.app;

import pl.Lotto.io.ConsolePrinter;
import pl.Lotto.io.LottoResultStore;
import pl.Lotto.io.file.CsvFileManager;
import pl.Lotto.io.file.FileManagerBuilder;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class LottoControler implements Serializable {

    private List<Integer> numbers = new ArrayList<>();
    private List<Integer> lottoResult;
    public List<Integer> lottoUserTypes;
    private Map<LocalDateTime, LottoResultStore> lottoAllResults = new HashMap<>();
    private static int maxNumberValue = 49;
    private static int numbersCount = 6;

    private Scanner sc = new Scanner(System.in);
    private FileManagerBuilder fileManager = new FileManagerBuilder();
    private CsvFileManager csvFileManager = new CsvFileManager();
    private ConsolePrinter printer = new ConsolePrinter();
    private DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-ss");
    private LocalDateTime date = LocalDateTime.now();


    public static int getMaxNumberValue() {
        return maxNumberValue;
    }

    public static void setMaxNumberValue(int maxNumberValue) {
        LottoControler.maxNumberValue = maxNumberValue;
    }

    public static int getNumbersCount() {

        return numbersCount;
    }

    public static void setNumbersCount(int numbersCount) {
        LottoControler.numbersCount = numbersCount;
    }

    void mainLoop() {
        Options options;
        do {
            generate();
            randomize();
            printOptions();
            printer.printText("Wybierz opcję");
            options = getOption();
            switch (options) {
                case FILE:
                    take(csvFileManager.readTypesFromFile());
                    break;
                case USER:
                    take(typeNumbers());
                    break;
                case PRINT_RESULTS:
                    csvFileManager.importData();
                    break;
                case EXIT:
                    printer.printText("Zakończono program. Zapraszam ponownie.");
                    break;
                default:
                    throw new IllegalStateException("Wybrano błędną opcję. Spróbuj ponownie. " + options);
            }
        } while (options != Options.EXIT);

    }

    private void take(List<Integer> integers) {
        lottoUserTypes = integers;
        executeLottoProcedure(lottoResult);
    }

    private void executeLottoProcedure(List<Integer> lottoResult) {
        int result;
        result = checkResult(lottoUserTypes);
        printer.printLottoResults(lottoUserTypes, lottoResult, result);
        List<Integer> mirrowUserNumbers = new ArrayList<>(lottoUserTypes);
        List<Integer> mirrowLottoResults = new ArrayList<>(lottoResult);
        lottoAllResults.put(date, new LottoResultStore(mirrowUserNumbers, mirrowLottoResults, result));
        fileManager.exportData(lottoAllResults);
        csvFileManager.exportData(lottoAllResults);

    }


    private Options getOption() {
        boolean optionOk = false;
        Options option = null;
        while (!optionOk) {
            try {
                option = Options.createFromInt(getInt());
                optionOk = true;
            } catch (InputMismatchException | Options.NoSuchOptionException ignore) {
                System.out.println("Wprowadzono wartość która nie jest liczbą. Spróbuj ponownie");
            }
        }

        return option;
    }

    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }

    private void printOptions() {
        printer.printText("Wybierz opcje: ");
        for (Options option : Options.values()) {
            printer.printText(option.toString());
        }

    }

    private void generate() {
        for (int i = 1; i < maxNumberValue; i++) {
            numbers.add(i);

        }

    }

    public List<Integer> GetLottoResult() {
        return lottoResult;
    }

    public List<Integer> GetLottoUserTypes() {
        return lottoUserTypes;
    }


    private void randomize() {
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

    public enum Options {

        FILE(0, " - Pobierz liczby z pliku"),
        USER(1, " - Wprowadź liczby od użytkownika"),
        PRINT_RESULTS(2, " - Wyświetl wszystkie wyniki"),
        EXIT(3, " - Wyjdź z programu");

        private int value;
        private String description;

        Options(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + " " + description;
        }

        static Options createFromInt(int option) throws NoSuchOptionException {
            try {
                return values()[option];
            } catch (ArrayIndexOutOfBoundsException ee) {
                throw new NoSuchOptionException("Brak opcji o id " + option);
            }
        }


        static class NoSuchOptionException extends Throwable {
            public NoSuchOptionException(String s) {
            }
        }
    }
}