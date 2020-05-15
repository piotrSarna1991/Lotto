package pl.Lotto.io.file;

import pl.Lotto.app.LottoControler;
import pl.Lotto.io.LottoResultStore;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Integer.parseInt;

public class FileManagerBuilder implements FileManager {


    private final String FILE_NAME2 = "Results.o";


    public void exportData(Map<LocalDateTime,LottoResultStore> map) {
        try (var fos = new FileOutputStream(FILE_NAME2);
             var os = new ObjectOutputStream(fos);) {
            os.writeObject(map);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<LocalDateTime,LottoResultStore> importData() {
        Map<LocalDateTime,LottoResultStore> map = new HashMap<>();
        try (var fis = new FileInputStream(FILE_NAME2);
             var ois = new ObjectInputStream(fis);) {
            map= (HashMap<LocalDateTime, LottoResultStore>) ois.readObject();

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
