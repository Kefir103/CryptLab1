import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CharactersLab {
    private static CharactersLab sCharactersLab;

    StringBuilder originalFullText = new StringBuilder();
    StringBuilder originalChapterText = new StringBuilder();

    String toLowerOriginalText = "";
    String toLowerChapterText = "";
    String toLowerEncryptedChapterText = "";

    StringBuilder encryptedText = new StringBuilder();
    StringBuilder decryptedText = new StringBuilder();

    int numOfAllTextChars;
    int numOfAllChapterChars;
    int numOfAllEncryptedChapterChars;
    int numOfAllTextBigrams;
    int numOfAllEncryptedBigrams;

    int[] numOfEveryTextChar = new int[32];
    int[] numOfEveryChapterChar = new int[32];
    int[] numOfEveryEncryptedChapterChar = new int[32];
    int[] numOfEveryFullBigram = new int[1024];
    int[] numOfEveryEncryptBigram = new int[1024];

    char[] originalAlphabet = new char[32];
    char[] newAlphabet = new char[32];
    char[] decryptedFreqAlphabet = new char[32];

    Map <Character, Integer> mapFullCharacterInteger = new TreeMap<>();
    Map <String, Integer> mapFullBigramInteger = new TreeMap<>();
    Map <Character, Float> mapFullCharacterFreq = new TreeMap<>();
    Map<String, Float> mapFullBigramFreq = new TreeMap<>();
    Map<Float, String> mapFullFreqBigram = new TreeMap<>(Comparator.reverseOrder());
    Map <Float, Character> mapTextFreqCharacter = new TreeMap<>(Comparator.reverseOrder());

    Map <Character, Integer> mapChapterCharacterInteger = new TreeMap<>();
    Map <Character, Float> mapChapterFreq = new TreeMap<>();
    Map <Float, Character> mapFreqChapter = new TreeMap<>(Comparator.reverseOrder());

    Map <Character, Integer> mapEncryptedCharacterInteger = new TreeMap<>();
    Map<String, Integer> mapEncryptedBigramInteger = new TreeMap<>();
    Map<String, Float> mapEncryptedBigramFreq = new TreeMap<>();
    Map<Float, String> mapEncryptedFreqBigram = new TreeMap<>(Comparator.reverseOrder());
    Map <Character, Float> mapEncryptedChapterFreq = new TreeMap<>();
    Map <Float, Character> mapEncryptedFreqChapter = new TreeMap<>(Comparator.reverseOrder());

    public static CharactersLab get(){
        if (sCharactersLab == null){
            sCharactersLab = new CharactersLab();
        }
        return sCharactersLab;
    }

    public int getNumOfAllTextChars() {
        return numOfAllTextChars;
    }

    public int getNumOfAllChapterChars(){
        return numOfAllChapterChars;
    }

    public int getNumOfAllEncryptedChapterChars() {
        return numOfAllEncryptedChapterChars;
    }

    public Map<Character, Integer> getMapFullCharacterInteger() {
        return mapFullCharacterInteger;
    }

    public Map<Character, Integer> getMapChapterCharacterInteger() {
        return mapChapterCharacterInteger;
    }

    public Map<Character, Integer> getMapEncryptedCharacterInteger() {
        return mapEncryptedCharacterInteger;
    }

    public void setMapBigramInteger (Map bigramInteger){
        for (char startChar = 'а'; startChar <= 'я'; startChar++){
            for (char endChar = 'а'; endChar <= 'я'; endChar++){
                String startEndChar = "" + startChar + endChar;
                bigramInteger.put(startEndChar, 0);
            }
        }
    }

    public void setMapBigramFloat (Map bigramFloat){
        for (char startChar = 'а'; startChar <= 'я'; startChar++){
            for (char endChar = 'а'; endChar <= 'я'; endChar++){
                String startEndChar = "" + startChar + endChar;
                bigramFloat.put(startEndChar, 0.0);
            }
        }
    }

    public Map<String, Integer> getMapFullBigramInteger() {
        return mapFullBigramInteger;
    }

    public Map<String, Integer> getMapEncryptedBigramInteger() {
        return mapEncryptedBigramInteger;
    }
}
