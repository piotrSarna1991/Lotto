package pl.Lotto.io.file;

import pl.Lotto.io.LottoResultsStore;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Integer.parseInt;

public class SerializableFileManager implements FileManager {


    private final String FILE_NAME2 = "Results.o";


    public void exportData(Map<LocalDateTime, LottoResultsStore> map) {
        try (var fos = new FileOutputStream(FILE_NAME2);
             var os = new ObjectOutputStream(fos);) {
            os.writeObject(map);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<LocalDateTime, LottoResultsStore> importData() {
        Map<LocalDateTime, LottoResultsStore> map = new HashMap<>();
        try (var fis = new FileInputStream(FILE_NAME2);
             var ois = new ObjectInputStream(fis);) {
            map= (HashMap) ois.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return map;

    }
}
