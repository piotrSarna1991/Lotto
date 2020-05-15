package pl.Lotto.app;

import pl.Lotto.io.ConsolePrinter;

public class LottoApp {
    private static LottoControler lotto = new LottoControler();
    private static ConsolePrinter printer = new ConsolePrinter();
    public static void main(String[] args) {
//
       printer.printText("Lotto build 1.4");
       printer.printText("Witam w losowaniu Lotto");
        lotto.mainLoop();


    }


}
