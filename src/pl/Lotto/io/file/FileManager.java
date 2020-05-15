package pl.Lotto.io.file;

import pl.Lotto.io.ConsolePrinter;
import pl.Lotto.io.LottoResultStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public interface FileManager {
    void exportData(Map<LocalDateTime,LottoResultStore> map );


}
