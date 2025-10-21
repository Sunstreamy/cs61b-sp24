package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public HistoryHandler(NGramMap map) {
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {

        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        // 注意：标签列表直接就是用户输入的单词列表！
        ArrayList<String> labels = new ArrayList<>(words);

        for (String word : words) {

            // 对于每个单词，从 NGramMap 中获取它的加权历史数据
            // 我们需要调用 weightHistory，因为它返回的是相对频率（小数），适合绘图
            TimeSeries wordHistory = this.ngm.weightHistory(word, startYear, endYear);

            // 将获取到的 TimeSeries 添加到数据列表中
            lts.add(wordHistory);
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }

}
