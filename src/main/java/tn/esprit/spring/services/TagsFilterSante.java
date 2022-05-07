package tn.esprit.spring.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TagsFilterSante {
	private static int largestWordLength = 0;

    private static Map<String, String[]> Tags = new HashMap<String, String[]>();

    static int getTagsText(final String input) {
        loadWords();
        if (input == null) {
            return 0;
        }

        String modifiedInput = input;
        modifiedInput = modifiedInput.toLowerCase().replaceAll("[^a-zA-Z]", "");
        ArrayList<String> TagsFound = new ArrayList<>();

        // iterate over each letter in the word
        for (int start = 0; start < modifiedInput.length(); start++) {
            // from each letter, keep going to find bad words until either the end of
            // the sentence is reached, or the max word length is reached.
            for (int offset = 1; offset < (modifiedInput.length() + 1 - start) && offset < largestWordLength; offset++) {
                String wordToCheck = modifiedInput.substring(start, start + offset);
                if (Tags.containsKey(wordToCheck)) {
                    String[] ignoreCheck = Tags.get(wordToCheck);
                    boolean ignore = false;
                    for (int stringIndex = 0; stringIndex < ignoreCheck.length; stringIndex++) {
                        if (modifiedInput.contains(ignoreCheck[stringIndex])) {
                            ignore = true;
                            break;
                        }
                    }

                    if (!ignore) {
                    	TagsFound.add(wordToCheck);
                    }
                }
            }
        }

      int count = 0;
        for (String tag : TagsFound) {
        	count++;
        }

        return count;
    } // end getCensoredText

    private static void loadWords() {
        int readCounter = 0;
        try {
            // The following spreadsheet is from: https://gist.github.com/PimDeWitte/c04cc17bc5fa9d7e3aee6670d4105941
            // (If the spreadsheet ever ceases to exist, then this application will still function normally otherwise - it just won't censor any swear words.)


        	 FileReader makeup = new FileReader("D:\\Esprit\\4SAE1\\Spring\\TPs\\WomenEmpowerment\\src\\main\\resources\\DictionnaireDomain\\Health.csv");
    	     BufferedReader reader = new BufferedReader(makeup);


            String currentLine = "";
            while ((currentLine = reader.readLine()) != null) {
                readCounter++;
                String[] content = null;
                try {
                    if (1 == readCounter) {
                        continue;
                    }

                    content = currentLine.split(","); 
                    if (content.length == 0) {
                        continue;
                    }

                    final String word = content[0];

                    if (word.startsWith("***")) {
                        continue;
                    }

                    if (word.length() > largestWordLength) {
                        largestWordLength = word.length();
                    }

                    String[] ignore_in_combination_with_words = new String[] {};
                    if (content.length > 1) {
                        ignore_in_combination_with_words = content[1].split("_");
                    }

                    // Make sure there are no capital letters in the spreadsheet
                    Tags.put(word.replaceAll(" ", "").toLowerCase(), ignore_in_combination_with_words);
                } catch (Exception except) {
                }
            } // end while
        } catch (IOException except) {
        }
    } // end loadBadWords
    
}
