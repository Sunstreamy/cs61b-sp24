package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private NGramMap ngm;

    public HistoryTextHandler(NGramMap map) {
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String res = "";
        for (String word : words) {
            TimeSeries wordHistory = this.ngm.weightHistory(word, startYear, endYear);

            // 3. 使用 + 号来拼接字符串
            res += word + ": " + wordHistory.toString() + "\n";
        }
        return res;
    }

}
