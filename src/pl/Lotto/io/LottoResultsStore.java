package pl.Lotto.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LottoResultsStore implements Serializable {
    private List<Integer> typesStore;
    private List <Integer> resultStore;
    private int result;
    private static final long serialVersionUID = 3957894669835000258L;
    public LottoResultsStore(List<Integer> typesStore, List<Integer> resultStore, int result) {
        this.typesStore = typesStore;
        this.resultStore = resultStore;
        this.result = result;
    }

    @Override
    public String toString() {
        return "LottoResultStore{" +
                "typesStore=" + typesStore +
                ", resultStore=" + resultStore +
                ", result=" + result +
                '}';
    }

    public List<Integer> getTypesStore() {
        return typesStore;
    }

    public List<Integer> getResultStore() {
        return resultStore;
    }

    public int getResult() {
        return result;
    }

    public String setKey(){
        return null;
    }


}
