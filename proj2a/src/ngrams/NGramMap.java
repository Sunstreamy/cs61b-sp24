package ngrams;

import edu.princeton.cs.algs4.In;

import java.sql.Time;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private Map<String, TimeSeries> wordData;
    private TimeSeries totalCounts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordData = new HashMap<>(); // 或者 new TreeMap<>()
        this.totalCounts = new TimeSeries();

        In wordsIn = new In(wordsFilename);
        while (wordsIn.hasNextLine()) {
            String line = wordsIn.readLine();
            String[] words = line.split("\t");

            String word = words[0];
            int year = Integer.parseInt(words[1]);
            double count = Double.parseDouble(words[2]);

            if (!wordData.containsKey(word)) {
                wordData.put(word, new TimeSeries());
            }
            TimeSeries ts = wordData.get(word);
            ts.put(year, count);
        }
        In countsIn = new In(countsFilename);
        while (countsIn.hasNextLine()) {
            String line = countsIn.readLine();
            String[] parts = line.split(",");

            int year = Integer.parseInt(parts[0]);
            double count = Double.parseDouble(parts[1]);

            totalCounts.put(year, count);
        }
    }


    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries original = getSeriesForWord(word);
        return new TimeSeries(original, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries original = getSeriesForWord(word);
        return new TimeSeries(original);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalCounts);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordCounts = countHistory(word, startYear, endYear);
        TimeSeries totalCountsInrange = new TimeSeries(totalCountHistory(), startYear, endYear);
        return wordCounts.dividedBy(totalCountsInrange);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries totalSumWeight = new TimeSeries();

        TimeSeries totalCountsInRange = new TimeSeries(totalCountHistory(), startYear, endYear);

        for (String word : words) {
            TimeSeries wordCounts = countHistory(word, startYear, endYear);
            TimeSeries wordWeights = wordCounts.dividedBy(totalCountsInRange);
            totalSumWeight = totalSumWeight.plus(wordWeights);
        }

        return totalSumWeight;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

    private TimeSeries getSeriesForWord(String word) {
        if (!wordData.containsKey(word)) {
            return new TimeSeries();
        }
        return wordData.get(word);
    }
}
