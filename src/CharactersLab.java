import java.util.*;

public class CharactersLab {
    private static CharactersLab sCharactersLab;

    StringBuilder originalFullText = new StringBuilder();
    StringBuilder originalChapterText = new StringBuilder();

    StringBuilder encryptedText = new StringBuilder();
    StringBuilder decryptedText = new StringBuilder();
    
    StringBuilder logs = new StringBuilder();

    char[] originalAlphabet = new char[32];
    char[] newAlphabet = new char[32];
    char[] decryptedFreqAlphabet = new char[32];
    
    Map <Character, Float> mapFullCharacterFreq = new TreeMap<>();

    Map <Character, Float> mapChapterFreq = new TreeMap<>();

    Map <Character, Float> mapEncryptedChapterFreq = new TreeMap<>();

    public static CharactersLab get(){
        if (sCharactersLab == null){
            sCharactersLab = new CharactersLab();
        }
        return sCharactersLab;
    }

    public void setDataOfText(StringBuilder text, Map mapOfChars, boolean writeDateToFile, String logText){
        logs.append(logText + "\n");
        int numOfAllChars;
        int[] numOfEveryChar = new int[32];
        String toLowerText = text.toString().toLowerCase();
        numOfAllChars = setNumOfAllTextChars(toLowerText);
        setNumOfEveryChar(toLowerText, numOfEveryChar,numOfAllChars, mapOfChars);

        if (writeDateToFile){
            logs.append(getNumOfAllChars(numOfAllChars));
            logs.append(getMapOfEveryChar(mapOfChars, numOfEveryChar));
            logs.append("/////////////////////////////////////////////\n");
            Main.writeLogs(logs);
        }

    }

    private String getNumOfAllChars(int numOfAllChars){
        return ("Num of all chars: " + numOfAllChars + "\n");
    }

    private StringBuilder getMapOfEveryChar(Map mapOfEveryChar, int[] numOfEveryChar){
        StringBuilder logStr = new StringBuilder();
        int counter = 0;
        Set<Map.Entry<Character, Integer>> set = mapOfEveryChar.entrySet();
        for (Map.Entry<Character, Integer> map: set){
            logStr.append(map.getKey() + ": " + map.getValue()
                            + " (" + numOfEveryChar[counter] + ")"
                            + "\n");
            counter++;
        }
        return logStr;
    }

    private int setNumOfAllTextChars(String text){
        int num = 0;
        for (int i = 0; i < text.length(); i++){
            for (char c = 'а'; c <= 'я'; c++){
                if (c == text.charAt(i)){
                    num++;
                    break;
                }
            }
        }
        return num;
    }

    private void setNumOfEveryChar(String text, int[] numOfEveryChar,int numOfAllChar, Map mapOfChars){
        int j = 0;
        for (char c = 'а'; c <= 'я'; c++){
            for (int i = 0; i < text.length(); i++){
                if (c == text.charAt(i)){
                    numOfEveryChar[j]++;
                }
            }
            mapOfChars.put(c, rounding(numOfAllChar, numOfEveryChar[j]));
            j++;
        }
    }
    
    private float rounding(int numOfAllChars, int numOfNeededChar){
        Float charFreq = new Float((float) numOfNeededChar / numOfAllChars * 100);
        charFreq = Math.round(charFreq * 100) / 100.0f;
        return charFreq;
    }


}
