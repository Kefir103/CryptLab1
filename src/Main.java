import com.sun.istack.internal.NotNull;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        CharactersLab charactersLab = CharactersLab.get();
        int readedChar;
        try (FileReader finFullText = new FileReader("res/Voina_i_mir.txt");
             FileReader finChapterText = new FileReader("res/Tom 1 Chapter 9.txt")){
            do {
                readedChar = finFullText.read();
                if (readedChar != -1) {
                    charactersLab.originalFullText.append((char) readedChar);
                }
            } while (readedChar != -1);
            do {
                readedChar = finChapterText.read();
                if (readedChar != -1){
                    charactersLab.originalChapterText.append((char) readedChar);
                }
            } while (readedChar != -1);

        } catch (IOException e){
            e.printStackTrace();
        }
        charactersLab.toLowerOriginalText = charactersLab.originalFullText.toString().toLowerCase();
        main.setNumOfChars(charactersLab.toLowerOriginalText);
        System.out.println("Num of all chars: " + charactersLab.getNumOfAllTextChars());

        main.setNumOfEveryChar(charactersLab.toLowerOriginalText, charactersLab.numOfEveryTextChar, charactersLab.mapCharacterInteger);
        main.setFreqMap(charactersLab.mapCharacterFreq,charactersLab.numOfEveryTextChar, charactersLab.numOfAllTextChars);
        main.getNumOfEveryChar(charactersLab.getMapCharacterInteger(), true);
        main.getNumOfEveryChar(charactersLab.mapCharacterFreq, true);

        main.setOriginalAlphabet();
        main.setNewAlphabet(charactersLab.originalAlphabet, 4);
        main.getNewAlphabet(charactersLab.newAlphabet, charactersLab.originalAlphabet);
        main.encryptCaesar(charactersLab.originalChapterText, charactersLab.originalAlphabet, charactersLab.newAlphabet);
        try(FileWriter fout = new FileWriter("res/encryptedText.txt")){
            fout.write(charactersLab.encryptedText.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void setNumOfChars(@NotNull String text){
        for (int i = 0; i < text.length(); i++){
            for (char c = 'а'; c <= 'я'; c++){
                if (c == text.charAt(i)){
                    CharactersLab.get().numOfAllTextChars++;
                    break;
                }
            }
        }
    }

    private void setNumOfEveryChar(@NotNull String text, int[] numOfEveryChar, Map mapCharInt){
        int j = 0;
        for (char c = 'а'; c <= 'я'; c++){
            for (int i = 0; i < text.length(); i++){
                if (c == text.charAt(i)){
                    numOfEveryChar[j]++;
                }
            }
            mapCharInt.put(c, numOfEveryChar[j]);
            j++;
        }
    }

    private void getNumOfEveryChar(Map map, boolean startPrintLoop) {
        if (startPrintLoop) {
            Set<Map.Entry<Character, Integer>> set = map.entrySet();
            for (Map.Entry<Character, Integer> me : set) {
                System.out.print(me.getKey() + ": " + me.getValue() + "\n");
            }
            System.out.println();
        } else {
            for (Character c = 'а'; c <= 'я'; c++) {
                map.get(c);
            }
        }
    }

    private void setFreqMap(Map mapFreq, int[] numOfChar, int numAllChars){
        char c = 'а';
        for (int i = 0; i < numOfChar.length; i++){
            Float charFreq = new Float((float) numOfChar[i] / numAllChars * 100);
            charFreq = Math.round(charFreq * 100) / 100.0f;
            System.out.println(charFreq);
            mapFreq.put(c, charFreq);
            c++;
        }

    }

    private void setOriginalAlphabet(){
        int i = 0;
        for (char c = 'а'; c <= 'я'; c++, i++){
            CharactersLab.get().originalAlphabet[i] = c;
        }
    }

    private void setNewAlphabet(char[] originalAlphabet, int shift){
        int tmpNum = shift;
        try{
            for (int i = 0; i < originalAlphabet.length; i++, shift++){
                CharactersLab.get().newAlphabet[i] = originalAlphabet[shift];
            }
        } catch (IndexOutOfBoundsException e){
            shift = tmpNum;
            for (int i = 0; i < tmpNum; i++, shift--){
                CharactersLab.get().newAlphabet[originalAlphabet.length - shift] = originalAlphabet[i];
            }
        }
    }

    private void getNewAlphabet(char[] newAlphabet, char[] originalAlphabet){
        for (int i = 0; i < newAlphabet.length; i++){
            System.out.print(originalAlphabet[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < newAlphabet.length; i++){
            System.out.print(newAlphabet[i] + " ");
        }
    }

    private void encryptCaesar(StringBuilder originalChapterText, char[] originalAlphabet ,char[] newAlphabet){
        CharactersLab.get().encryptedText = originalChapterText;
        Character character;
        for (int i = 0; i < originalChapterText.length(); i++){
            for (int j = 0; j < newAlphabet.length; j++){
                if (Character.isUpperCase(CharactersLab.get().encryptedText.charAt(i))){
                    character = new Character(CharactersLab.get().encryptedText.charAt(i));
                    if (Character.toLowerCase(character) == originalAlphabet[j]){
                        character = newAlphabet[j];
                        character = Character.toUpperCase(character);
                        CharactersLab.get().encryptedText.setCharAt(i, character);
                        break;
                    }
                }
                if (CharactersLab.get().encryptedText.charAt(i) == originalAlphabet[j]){
                    CharactersLab.get().encryptedText.setCharAt(i, newAlphabet[j]);
                    break;
                }
            }
        }
    }

}
