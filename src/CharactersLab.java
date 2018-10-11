import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CharactersLab {
    private static CharactersLab sCharactersLab;
    StringBuilder originalFullText = new StringBuilder();
    StringBuilder originalChapterText = new StringBuilder();
    String toLowerOriginalText = "";
    StringBuilder encryptedText = new StringBuilder();
    StringBuilder decryptedText = new StringBuilder();
    int numOfAllTextChars, numOfAllChapterChars;
    int[] numOfEveryTextChar = new int[32];
    int[] numOfEveryChapterChar = new int[32];
    char[] originalAlphabet = new char[32];
    char[] newAlphabet = new char[32];
    Map <Character, Integer> mapCharacterInteger = new TreeMap<>();
    Map <Character, Float> mapCharacterFreq = new TreeMap<>();
    Map <Character, Float> mapChapterFreq = new TreeMap<>();

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

    public Map<Character, Integer> getMapCharacterInteger() {
        return mapCharacterInteger;
    }
}
