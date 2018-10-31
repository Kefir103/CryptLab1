import java.util.*;

public class CharactersLab {
    private static CharactersLab sCharactersLab;

    StringBuilder originalFullText = new StringBuilder();
    StringBuilder originalChapterText = new StringBuilder();

    StringBuilder encryptedText = new StringBuilder();
    StringBuilder decryptedText = new StringBuilder();

    int numOfAllTextChars;
    int numOfAllChapterChars;
    int numOfAllEncryptedChapterChars;

    int[] numOfEveryTextChar = new int[32];
    int[] numOfEveryChapterChar = new int[32];
    int[] numOfEveryEncryptedChapterChar = new int[32];

    char[] originalAlphabet = new char[32];
    char[] newAlphabet = new char[32];
    char[] decryptedFreqAlphabet = new char[32];

    Map <Character, Integer> mapFullCharacterInteger = new TreeMap<>();
    Map <Character, Float> mapFullCharacterFreq = new TreeMap<>();
    Map <Float, Character> mapTextFreqCharacter = new TreeMap<>(Comparator.reverseOrder());

    Map <Character, Integer> mapChapterCharacterInteger = new TreeMap<>();
    Map <Character, Float> mapChapterFreq = new TreeMap<>();
    Map <Float, Character> mapFreqChapter = new TreeMap<>(Comparator.reverseOrder());

    Map <Character, Integer> mapEncryptedCharacterInteger = new TreeMap<>();
    Map <Character, Float> mapEncryptedChapterFreq = new TreeMap<>();
    Map <Float, Character> mapEncryptedFreqChapter = new TreeMap<>(Comparator.reverseOrder());

    public static CharactersLab get(){
        if (sCharactersLab == null){
            sCharactersLab = new CharactersLab();
        }
        return sCharactersLab;
    }

    public void setDataOfText(StringBuilder text, int numOfAllChars, int[] numOfEveryChar, Map mapOfChars, boolean printDate){
        String toLowerText = text.toString().toLowerCase();
        numOfAllChars = setNumOfAllTextChars(toLowerText, numOfAllChars);
        setNumOfEveryChar(toLowerText, numOfEveryChar, mapOfChars);

        if (printDate){
            getNumOfAllChars(numOfAllChars);
            getMapOfEveryChar(mapOfChars);
        }
    }

    private void getNumOfAllChars(int numOfAllChars){
        System.out.println("Num of all chars: " + numOfAllChars);
    }

    private void getMapOfEveryChar(Map mapOfEveryChar){
        Set<Map.Entry<Character, Integer>> set = mapOfEveryChar.entrySet();
        for (Map.Entry<Character, Integer> map: set){
            System.out.print(map.getKey() + ": " + map.getValue() + "\n");
        }
    }

    private int setNumOfAllTextChars(String text, int numOfAllChars){
        for (int i = 0; i < text.length(); i++){
            for (char c = 'а'; c <= 'я'; c++){
                if (c == text.charAt(i)){
                    numOfAllChars++;
                    break;
                }
            }
        }
        return numOfAllChars;
    }

    private void setNumOfEveryChar(String text, int[] numOfEveryChar, Map mapOfChars){
        int j = 0;
        for (char c = 'а'; c <= 'я'; c++){
            for (int i = 0; i < text.length(); i++){
                if (c == text.charAt(i)){
                    numOfEveryChar[j]++;
                }
            }
            mapOfChars.put(c, numOfEveryChar[j]);
            j++;
        }
    }
}
