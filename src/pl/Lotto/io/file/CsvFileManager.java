package pl.Lotto.io.file;

import pl.Lotto.io.ConsolePrinter;
import pl.Lotto.io.LottoGame;
import pl.Lotto.io.LottoResultsStore;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvFileManager {
    private final String TAKE_NUMBERS_FROM_USER = "Numbers.csv";
    private final String SAVE_RESULTS_FILE = "Results.csv";
    ConsolePrinter printer = new ConsolePrinter();




    public List<Integer> readTypesFromFile() {
        List<Integer> userNumbers = new ArrayList<>();
        LottoGame game = new LottoGame();
        try (FileReader fileReader = new FileReader(TAKE_NUMBERS_FROM_USER);
             BufferedReader reader = new BufferedReader(fileReader);
        ) {
            String nextLine = null;

            while ((nextLine = reader.readLine()) != null) {

                int nextNumber = Integer.parseInt(nextLine);
                if (nextNumber <= game.getMaxNumberValue()) {
                    userNumbers.add(nextNumber);
                } else {
                    printer.printText("Wprowadzono niepoprawną liczbę");
                    break;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game.lottoUserTypes = userNumbers;
        return game.lottoUserTypes;
    }

    public void importData() {

        try (var fileReader = new FileReader(SAVE_RESULTS_FILE);
             var reader = new BufferedReader(fileReader);) {
            String nextLine = null;
            while ((nextLine = reader.readLine()) != null) {
                printer.printText(nextLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void exportData(Map<LocalDateTime, LottoResultsStore> map) {

        try (
                var fileWriter = new FileWriter(SAVE_RESULTS_FILE, true);
                var writer = new BufferedWriter(fileWriter);
        ) {
            writer.newLine();
            writer.write(String.valueOf(map.entrySet()));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
