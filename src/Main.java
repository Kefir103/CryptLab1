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

        main.readText("res/Voina_i_mir.txt", charactersLab.originalFullText);
        main.readText("res/Tom 1 Chapter 9.txt", charactersLab.originalChapterText);

        charactersLab.setDataOfText(charactersLab.originalFullText,
                charactersLab.mapFullCharacterFreq,
                true,
                "Data of original text");

        charactersLab.setDataOfText(charactersLab.originalChapterText,
                charactersLab.mapChapterFreq,
                true,
                "Data of chapter text");

        /*

        // Получаем алфавит, шифруем его шифром Цезаря и зашифровываем текст
        main.setOriginalAlphabet();
        main.setNewAlphabet(charactersLab.originalAlphabet, 4);

        main.encryptCaesar(charactersLab.originalChapterText, charactersLab.originalAlphabet, charactersLab.newAlphabet);
        System.out.println("///////////////////////////////////////////////////");

        // Работа с символами зашифрованного текста
        main.setFreqMap(charactersLab.mapEncryptedChapterFreq, charactersLab.numOfEveryEncryptedChapterChar, charactersLab.numOfAllEncryptedChapterChars);
        main.setValueMap(charactersLab.mapEncryptedChapterFreq, charactersLab.mapEncryptedFreqChapter);

        System.out.println("Num of all encrypted chapter chars: " + charactersLab.getNumOfAllEncryptedChapterChars());
        main.getNumOfEveryChar(charactersLab.getMapEncryptedCharacterInteger(), true);
        main.getNumOfEveryChar(charactersLab.mapEncryptedChapterFreq, true);
        main.getNumOfEveryChar(charactersLab.mapEncryptedFreqChapter, true);

        System.out.println("///////////////////////////////////////////////////");
        main.getNewAlphabet(charactersLab.newAlphabet, charactersLab.originalAlphabet);
        main.setDecryptedFreqAlphabet(charactersLab.mapTextFreqCharacter, charactersLab.mapEncryptedFreqChapter, charactersLab.decryptedFreqAlphabet, charactersLab.originalAlphabet);
        main.decryptTextByFreques(charactersLab.encryptedText, charactersLab.newAlphabet, charactersLab.decryptedFreqAlphabet);

        System.out.println("\n///////////////////////////////////////////////////");

        try(FileWriter foutEncrypt = new FileWriter("res/encryptedText.txt")){
            foutEncrypt.write(charactersLab.encryptedText.toString());
        } catch (IOException e){
            e.printStackTrace();
        }

        try(FileWriter foutDecrypt = new FileWriter("res/decryptedByFrequesText.txt")){
            foutDecrypt.write(charactersLab.decryptedText.toString());
        } catch (IOException e){
            e.printStackTrace();
        }

        main.checkText(charactersLab.originalChapterText, charactersLab.decryptedText);



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
        System.out.println();
    }

    private void encryptCaesar(StringBuilder originalChapterText, char[] originalAlphabet ,char[] newAlphabet){
        CharactersLab.get().encryptedText.append(originalChapterText.toString());
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

    private void setValueMap(Map keysMap, Map valuesMap){
        Set<Map.Entry<Character, Float>> charFloatSet = keysMap.entrySet();

        for (Map.Entry<Character, Float> loopMap: charFloatSet){
            valuesMap.put(loopMap.getValue(), loopMap.getKey());
        }
    }

    private void setDecryptedFreqAlphabet(Map mapFreqFullText, Map mapFreqEncryptedText, char[] decryptedFreqAlphabet, char[] originalAlphabet){
        ArrayList<Float> alFreqFullText = new ArrayList<>(mapFreqFullText.keySet());
        ArrayList<Float> alFreqEncryptedText = new ArrayList<>(mapFreqEncryptedText.keySet());
        ArrayList<Float> firstMap = new ArrayList<>(CharactersLab.get().mapEncryptedChapterFreq.values());
        Map<Float, Character> secondMap = CharactersLab.get().mapEncryptedFreqChapter;
        ArrayList<Character> alFullTest = new ArrayList<>(mapFreqEncryptedText.values());
        ArrayList<Character> alEncTest = new ArrayList<>(CharactersLab.get().mapEncryptedChapterFreq.keySet());
        float tmpMin = Math.abs(alFreqEncryptedText.get(0) - alFreqFullText.get(0));
        int indexOfMin = 0;
        for (int i = 0; i < decryptedFreqAlphabet.length;i++) {
            for (int j = 0; j < decryptedFreqAlphabet.length; j++) {
                if (alFreqEncryptedText.get(i) - alFreqFullText.get(j) <= tmpMin) {
                    tmpMin = Math.abs(alFreqEncryptedText.get(i) - alFreqFullText.get(j));
                    indexOfMin = j;
                } else {
                    break;
                }
            }
            decryptedFreqAlphabet[indexOfMin] = originalAlphabet[indexOfMin];
            indexOfMin = 0;
        }
        for (int i = 0; i < decryptedFreqAlphabet.length; i++) {
            if (decryptedFreqAlphabet[i] == '\u0000') {
                decryptedFreqAlphabet[i] = CharactersLab.get().newAlphabet[i];
            }
        }
        for (int i = 0; i < decryptedFreqAlphabet.length; i++){
            System.out.print(decryptedFreqAlphabet[i] + " ");
        }
    }

    private void decryptTextByFreques(StringBuilder encryptedText, char[] encryptedAlphabet, char[] decryptedAlphabet){
        CharactersLab.get().decryptedText.append(encryptedText.toString());
        Character character;
        for (int i = 0; i < encryptedText.length(); i++){
            for (int j = 0; j < decryptedAlphabet.length; j++){
                if (Character.isUpperCase(CharactersLab.get().decryptedText.charAt(i))){
                    character = new Character(CharactersLab.get().decryptedText.charAt(i));
                    if (Character.toLowerCase(character) == encryptedAlphabet[j]){
                        character = decryptedAlphabet[j];
                        character = Character.toUpperCase(character);
                        CharactersLab.get().decryptedText.setCharAt(i, character);
                        break;
                    }
                }
                if (CharactersLab.get().decryptedText.charAt(i) == encryptedAlphabet[j]){
                    CharactersLab.get().decryptedText.setCharAt(i, decryptedAlphabet[j]);
                    break;
                }
            }
        }
    }

    private void checkText(StringBuilder originalChapterText, StringBuilder decryptedText){
        String checkOriginalText = originalChapterText.toString().toLowerCase();
        String checkDecryptedText = decryptedText.toString().toLowerCase();
        int numOfGoodChars = 0;
        int textLength = 0;
        float percent;
        for (int i = 0; i < checkOriginalText.length(); i++){
            for (char c = 'а'; c <= 'я'; c++) {
                if (checkOriginalText.charAt(i) == c) {
                    textLength++;
                    if (checkOriginalText.charAt(i) == checkDecryptedText.charAt(i)) {
                        numOfGoodChars++;
                    }
                }
            }
        }
        System.out.println(textLength);
        System.out.println(numOfGoodChars);
        percent = ((float)numOfGoodChars / (float) textLength) * 100;
        System.out.println("///////////////////////////////////////////////////");
        System.out.println ("Точность криптоанализа " + percent + "%");
    }
    */

    }

    void readText (String filePath, StringBuilder readedText){
        int readedChar;
        try (FileReader fin = new FileReader(filePath)) {
            do {
                readedChar = fin.read();
                if (readedChar != -1) {
                    readedText.append((char) readedChar);
                }
            } while (readedChar != -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeLogs (StringBuilder logs){
        try (FileWriter fout = new FileWriter("res/logs.txt")){
            fout.write(logs.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
