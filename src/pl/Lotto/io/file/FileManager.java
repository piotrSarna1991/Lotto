package pl.Lotto.io.file;

import pl.Lotto.io.LottoResultsStore;

import java.time.LocalDateTime;
import java.util.Map;

public interface FileManager {
    void exportData(Map<LocalDateTime, LottoResultsStore> map );


}
