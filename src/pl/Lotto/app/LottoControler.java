package pl.Lotto.app;

import pl.Lotto.io.ConsolePrinter;
import pl.Lotto.io.LottoGame;
import pl.Lotto.io.LottoResultsStore;
import pl.Lotto.io.file.CsvFileManager;
import pl.Lotto.io.file.SerializableFileManager;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class LottoControler implements Serializable {


    private Map<LocalDateTime, LottoResultsStore> lottoAllResults = new HashMap<>();
    LottoGame game = new LottoGame();


    private Scanner sc = new Scanner(System.in);
    private SerializableFileManager fileManager = new SerializableFileManager();
    private CsvFileManager csvFileManager = new CsvFileManager();
    private ConsolePrinter printer = new ConsolePrinter();
    private DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-ss");
    private LocalDateTime date = LocalDateTime.now();


    void mainLoop() {
        Options options;
        do {
            game.generate();
            game.randomize();
            printOptions();
            printer.printText("Wybierz opcję");
            options = getOption();
            switch (options) {
                case READ_NUMBERS_FROM_FILE:
                    take(csvFileManager.readTypesFromFile());
                    break;
                case READ_NUMBERS_FROM_USER:
                    take(game.typeNumbers());
                    break;
                case PRINT_RESULTS_FROM_CSV:
                    printResultsFromCsvFile();
                    break;
                case PRINT_RESULTS_FROM_SERIAL:
                    printResultsFromSerial();
                    break;
                case EXIT:
                    printer.printText("Zakończono program. Zapraszam ponownie.");
                    break;
            }
        } while (options != Options.EXIT);

    }

    private void printResultsFromCsvFile() {
        csvFileManager.importData();
    }

    private void printResultsFromSerial() {
        lottoAllResults = fileManager.importData();
        printer.printFromSerialFile(lottoAllResults);
    }


    private void take(List<Integer> integers) {
        game.lottoUserTypes = integers;
        executeLottoProcedure(game.getLottoResult());
    }

    private void executeLottoProcedure(List<Integer> lottoResult) {
        int result;
        result = game.checkResult(game.lottoUserTypes);
        printer.printLottoResults(game.lottoUserTypes, lottoResult, result);
        List<Integer> mirrowUserNumbers = new ArrayList<>(game.lottoUserTypes);
        List<Integer> mirrowLottoResults = new ArrayList<>(lottoResult);
        lottoAllResults.put(date, new LottoResultsStore(mirrowUserNumbers, mirrowLottoResults, result));
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


    public enum Options {
        EXIT(0, " - Wyjdź z programu"),
        READ_NUMBERS_FROM_FILE(1, " - Wprowadź liczby z pliku"),
        READ_NUMBERS_FROM_USER(2, " - Wprowadź liczby od użytkownika"),
        PRINT_RESULTS_FROM_CSV(3, " - Wyświetl wszystkie wyniki z pliku CSV"),
        PRINT_RESULTS_FROM_SERIAL(4, " - Wyświetl wszystkkie wyniki z pliku serial");


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