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

        // Работа по символам оригинального текста всего произведения
        charactersLab.toLowerOriginalText = charactersLab.originalFullText.toString().toLowerCase();
        main.setNumOfAllTextChars(charactersLab.toLowerOriginalText);
        System.out.println("Num of all chars: " + charactersLab.getNumOfAllTextChars());

        main.setNumOfEveryChar(charactersLab.toLowerOriginalText, charactersLab.numOfEveryTextChar, charactersLab.mapFullCharacterInteger);
        main.setFreqMap(charactersLab.mapFullCharacterFreq, charactersLab.numOfEveryTextChar, charactersLab.numOfAllTextChars);
        main.setValueMap(charactersLab.mapFullCharacterFreq, charactersLab.mapTextFreqCharacter);

        // Получаем получившиеся данные по оригинальному тексту всего произведения
        main.getNumOfEveryChar(charactersLab.getMapFullCharacterInteger(), true);
        main.getNumOfEveryChar(charactersLab.mapFullCharacterFreq, true);
        main.getNumOfEveryChar(charactersLab.mapTextFreqCharacter, true);

        System.out.println("///////////////////////////////////////////////////");

        // Работа с символами главы произведения
        charactersLab.toLowerChapterText = charactersLab.originalChapterText.toString().toLowerCase();
        main.setNumOfAllChapterChars(charactersLab.toLowerChapterText);
        main.setNumOfEveryChar(charactersLab.toLowerChapterText, charactersLab.numOfEveryChapterChar, charactersLab.mapChapterCharacterInteger);
        main.setFreqMap(charactersLab.mapChapterFreq, charactersLab.numOfEveryChapterChar, charactersLab.numOfAllChapterChars);
        main.setValueMap(charactersLab.mapChapterFreq, charactersLab.mapFreqChapter);

        // Получаем данные по главе произведения
        System.out.println("Num of all chapter chars: " + charactersLab.getNumOfAllChapterChars());
        main.getNumOfEveryChar(charactersLab.getMapChapterCharacterInteger(), true);
        main.getNumOfEveryChar(charactersLab.mapChapterFreq, true);
        main.getNumOfEveryChar(charactersLab.mapFreqChapter, true);

        System.out.println("///////////////////////////////////////////////////");

        // Получаем алфавит, шифруем его шифром Цезаря и зашифровываем текст
        main.setOriginalAlphabet();
        main.setNewAlphabet(charactersLab.originalAlphabet, 4);

        main.encryptCaesar(charactersLab.originalChapterText, charactersLab.originalAlphabet, charactersLab.newAlphabet);
        System.out.println("///////////////////////////////////////////////////");

        // Работа с символами зашифрованного текста
        charactersLab.toLowerEncryptedChapterText = charactersLab.encryptedText.toString().toLowerCase();
        main.setNumOfAllEncryptedChapterChars(charactersLab.toLowerEncryptedChapterText);
        main.setNumOfEveryChar(charactersLab.toLowerEncryptedChapterText, charactersLab.numOfEveryEncryptedChapterChar, charactersLab.mapEncryptedCharacterInteger);
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
        /*
        charactersLab.setMapBigramInteger(charactersLab.mapFullBigramInteger);
        charactersLab.setMapBigramInteger(charactersLab.mapEncryptedBigramInteger);
        main.setNumOfEveryBigram(charactersLab.mapFullBigramInteger, charactersLab.numOfEveryFullBigram);
        main.setNumOfEveryBigram(charactersLab.mapEncryptedBigramInteger, charactersLab.numOfEveryEncryptBigram);
        charactersLab.setMapBigramFloat(charactersLab.mapFullBigramFreq);
        charactersLab.setMapBigramFloat(charactersLab.mapEncryptedBigramFreq);
        main.setBigramsInteger(charactersLab.toLowerOriginalText, charactersLab.getMapFullBigramInteger(), charactersLab.numOfAllTextBigrams);
        main.setBigramsInteger(charactersLab.toLowerEncryptedChapterText, charactersLab.getMapEncryptedBigramInteger(), charactersLab.numOfAllEncryptedBigrams);
        main.setBigramFreq(charactersLab.mapFullBigramInteger, charactersLab.numOfAllTextBigrams, charactersLab.numOfEveryFullBigram);
        main.setBigramFreq(charactersLab.mapEncryptedBigramInteger, charactersLab.numOfAllEncryptedBigrams, charactersLab.numOfEveryEncryptBigram);
        */

   }


    private void setNumOfAllTextChars(@NotNull String text){
        for (int i = 0; i < text.length(); i++){
            for (char c = 'а'; c <= 'я'; c++){
                if (c == text.charAt(i)){
                    CharactersLab.get().numOfAllTextChars++;
                    break;
                }
            }
        }
    }

    private void setNumOfAllChapterChars(@NotNull String text){
        for (int i = 0; i < text.length(); i++){
            for (char c = 'а'; c <= 'я'; c++){
                if (c == text.charAt(i)){
                    CharactersLab.get().numOfAllChapterChars++;
                    break;
                }
            }
        }
    }

    private void setNumOfAllEncryptedChapterChars(@NotNull String text){
        for (int i = 0; i < text.length(); i++){
            for (char c = 'а'; c <= 'я'; c++){
                if (c == text.charAt(i)){
                    CharactersLab.get().numOfAllEncryptedChapterChars++;
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
        System.out.println();
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

    private void setValueMap(Map keysMap, Map valuesMap){
        Set<Map.Entry<Character, Float>> charFloatSet = keysMap.entrySet();

        for (Map.Entry<Character, Float> loopMap: charFloatSet){
            valuesMap.put(loopMap.getValue(), loopMap.getKey());
        }
    }

    private void setDecryptedFreqAlphabet(Map mapFreqFullText, Map mapFreqEncryptedText, char[] decryptedFreqAlphabet, char[] originalAlphabet){
        ArrayList<Float> alFreqFullText = new ArrayList<>(mapFreqFullText.keySet());
        ArrayList<Float> alFreqEncryptedText = new ArrayList<>(mapFreqEncryptedText.keySet());
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

    private void setBigramsInteger(@NotNull String text, Map mapBigramInteger, int fullBigramsInteger){
        ArrayList<String> alBigramKeys = new ArrayList<>(mapBigramInteger.keySet());
        Integer bigramIntegerCounter = 0;
        fullBigramsInteger = 0;
        int j = 2;
        for (int i = 0; i < text.length(); i++, j++) {
            try {
                for (String string : alBigramKeys) {
                    if (text.substring(i, j).equals(string)) {
                        fullBigramsInteger++;
                        bigramIntegerCounter = (Integer) mapBigramInteger.get(string);
                        bigramIntegerCounter++;
                        mapBigramInteger.replace(string, bigramIntegerCounter);
                        break;
                    }
                }
                bigramIntegerCounter = 0;
            } catch (StringIndexOutOfBoundsException e){
                break;
            }
        }
        System.out.println("Всего биграмм: " + fullBigramsInteger);
        for (String string: alBigramKeys){
            System.out.print(string + ": " + mapBigramInteger.get(string) + "\n");
        }
        System.out.println();
    }

    private void setNumOfEveryBigram (Map mapBigramInteger, int[] numOfEveryBigram){
        ArrayList<Integer> alBigram = new ArrayList<>(mapBigramInteger.values());
        int counter = 0;
        for (int num: alBigram){
            numOfEveryBigram[counter] = num;
            counter++;
        }
    }

    private void setBigramFreq (Map mapBigramFreq, int numOfBigram, int[] numOfEveryBigram){
        String key;
        int counter = 0;
        System.out.println();
        for (char startChar = 'а'; startChar <= 'я'; startChar++){
            for (char endChar = 'а'; endChar <= 'я'; endChar++){
                key = "" + startChar + endChar;
                mapBigramFreq.replace(key, (float) numOfEveryBigram[counter] / numOfBigram);
                System.out.print(key + ": " + mapBigramFreq.get(key) + "\n");
            }
        }


    }
}
