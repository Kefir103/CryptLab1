import java.util.*;

public class CharactersLab {
    private static CharactersLab sCharactersLab;

    StringBuilder originalFullText = new StringBuilder();
    StringBuilder originalChapterText = new StringBuilder();

    StringBuilder encryptedText = new StringBuilder();
    StringBuilder decryptedText = new StringBuilder();
    StringBuilder decryptedByBigramsText = new StringBuilder();
    
    StringBuilder logs = new StringBuilder();

    char[] originalAlphabet = new char[32];
    char[] newAlphabet = new char[32];
    char[] decryptedFreqAlphabet = new char[32];
    char[] decryptedBigramAlphabet = new char[32];

    int[][] decryptedIntBigrams = new int[32][32];
    char[][] decryptedTableChars = new char[32][32];
    
    Map <Character, Float> mapFullCharacterFreq = new TreeMap<>();
    ArrayList<Float> listFullFreques = new ArrayList<>();

    Map <Character, Float> mapChapterFreq = new TreeMap<>();
    ArrayList<Float> listChapterFreques = new ArrayList<>();

    Map <Character, Float> mapEncryptedChapterFreq = new TreeMap<>();
    ArrayList<Float> listEncryptedFreques = new ArrayList<>();

    Map <String, Integer> mapFullBigram = new TreeMap<>();
    ArrayList<String> listFullBigrams = new ArrayList<>();

    Map <String, Integer> mapEncryptedBigram = new TreeMap<>();
    ArrayList<String> listEncryptedBigrams = new ArrayList<>();

    public static CharactersLab get(){
        if (sCharactersLab == null){
            sCharactersLab = new CharactersLab();
        }
        return sCharactersLab;
    }

    public void setDataOfText(StringBuilder text, Map mapOfChars, ArrayList<Float> listSortingFreques, boolean writeDateToFile, String logText){
        logs.append(logText + "\n");
        int numOfAllChars;
        int[] numOfEveryChar = new int[32];
        String toLowerText = text.toString().toLowerCase();
        numOfAllChars = setNumOfAllTextChars(toLowerText);
        setNumOfEveryChar(toLowerText, numOfEveryChar,numOfAllChars, mapOfChars);
        sortFloatMap(mapOfChars, listSortingFreques);

        if (writeDateToFile){
            logs.append(getNumOfAllChars(numOfAllChars));
            logs.append(getMapOfEveryChar(mapOfChars, numOfEveryChar));
            logs.append(getSortedFreques(listSortingFreques));
            logs.append("/////////////////////////////////////////////\n");
            Main.writeLogs(logs);
        }
    }

    public void encryptCaesar(StringBuilder originalChapterText, char[] originalAlphabet){

        setNewAlphabet(originalAlphabet, 4);

        encryptedText.append(originalChapterText.toString());
        Character character;
        for (int i = 0; i < originalChapterText.length(); i++){
            for (int j = 0; j < newAlphabet.length; j++){
                if (Character.isUpperCase(encryptedText.charAt(i))){
                    character = new Character(encryptedText.charAt(i));
                    if (Character.toLowerCase(character) == originalAlphabet[j]){
                        character = newAlphabet[j];
                        character = Character.toUpperCase(character);
                        encryptedText.setCharAt(i, character);
                        break;
                    }
                }
                if (encryptedText.charAt(i) == originalAlphabet[j]){
                    encryptedText.setCharAt(i, newAlphabet[j]);
                    break;
                }
            }
        }

        Main.writeToFile("res/encryptedText.txt", encryptedText);
    }

    public void decryptTextByFreques(StringBuilder encryptedText, char[] encryptedAlphabet, char[] decryptedAlphabet){
        decryptedText.append(encryptedText.toString());
        Character character;
        for (int i = 0; i < encryptedText.length(); i++){
            for (int j = 0; j < decryptedAlphabet.length; j++){
                if (Character.isUpperCase(decryptedText.charAt(i))){
                    character = new Character(decryptedText.charAt(i));
                    if (Character.toLowerCase(character) == encryptedAlphabet[j]){
                        character = decryptedAlphabet[j];
                        character = Character.toUpperCase(character);
                        decryptedText.setCharAt(i, character);
                        break;
                    }
                }
                if (decryptedText.charAt(i) == encryptedAlphabet[j]){
                    decryptedText.setCharAt(i, decryptedAlphabet[j]);
                    break;
                }
            }
        }
        Main.writeToFile("res/decryptedByFrequesText.txt", decryptedText);
    }

    public void decryptTextByBigrams(StringBuilder encryptedText, char[] encryptedAlphabet, char[] decryptedBigramAlphabet){
        decryptedByBigramsText.append(encryptedText.toString());
        Character character;
        for (int i = 0; i < encryptedText.length(); i++){
            for (int j = 0; j < decryptedBigramAlphabet.length; j++){
                if (Character.isUpperCase(decryptedByBigramsText.charAt(i))){
                    character = new Character(decryptedByBigramsText.charAt(i));
                    if (Character.toLowerCase(character) == encryptedAlphabet[j]){
                        character = decryptedBigramAlphabet[j];
                        character = Character.toUpperCase(character);
                        decryptedByBigramsText.setCharAt(i, character);
                        break;
                    }
                }
                if (decryptedByBigramsText.charAt(i) == encryptedAlphabet[j]){
                    decryptedByBigramsText.setCharAt(i, decryptedBigramAlphabet[j]);
                    break;
                }
            }
        }
        Main.writeToFile("res/decryptedTextByBigrams.txt", decryptedByBigramsText);
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

    public void setOriginalAlphabet(){
        int i = 0;
        for (char c = 'а'; c <= 'я'; c++, i++){
            originalAlphabet[i] = c;
        }
    }

    private void setNewAlphabet(char[] originalAlphabet, int shift){
        int tmpNum = shift;
        try{
            for (int i = 0; i < originalAlphabet.length; i++, shift++){
                newAlphabet[i] = originalAlphabet[shift];
            }
        } catch (IndexOutOfBoundsException e){
            shift = tmpNum;
            for (int i = 0; i < tmpNum; i++, shift--){
                newAlphabet[originalAlphabet.length - shift] = originalAlphabet[i];
            }
        }
    }

    private void sortFloatMap(Map mapCharFreq, ArrayList listValues){
        ArrayList<Float> tmpList = new ArrayList<>(mapCharFreq.values());
        tmpList.sort(Comparator.reverseOrder());
        listValues.addAll(tmpList);
    }

    private void setDecryptedTableChars(){
        int i = 0;
        int j = 0;
        for (char c = 'а'; c <= 'я'; c++, j++){
            decryptedTableChars[i][j] = c;
        }
        j = 0;
        for (char c = 'а'; c <= 'я'; c++, i++){
            decryptedTableChars[i][j] = c;
        }

    }





    private StringBuilder getSortedFreques(ArrayList<Float> listFreques){
        StringBuilder log = new StringBuilder();
        log.append("Sorted freques: \n");
        for (int i = 0; i < listFreques.size(); i++){
            log.append((float) listFreques.get(i) + "\n");
        }
        return log;
    }

    public void setDecryptedFreqAlphabet(ArrayList<Float> listFullFreques, ArrayList<Float> listEncryptedFreques){
        float tmpMin = Math.abs(listEncryptedFreques.get(0) - listFullFreques.get(0));
        int indexOfMin = 0;
        for (int i = 0; i < decryptedFreqAlphabet.length;i++) {
            for (int j = 0; j < decryptedFreqAlphabet.length; j++) {
                if (listEncryptedFreques.get(i) - listFullFreques.get(j) <= tmpMin) {
                    tmpMin = Math.abs(listEncryptedFreques.get(i) - listFullFreques.get(j));
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
                decryptedFreqAlphabet[i] = newAlphabet[i];
            }
        }
    }

    public void getAlphabets (char[] originalAlphabet, char[] newAlphabet, char[] decryptedFreqAlphabet){
        for (int i = 0; i < originalAlphabet.length; i++){
            System.out.print(originalAlphabet[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < newAlphabet.length; i++){
            System.out.print(newAlphabet[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < decryptedFreqAlphabet.length; i++){
            System.out.print(decryptedFreqAlphabet[i] + " ");
        }
        System.out.println();
    }

    // Работа с биграммами
    private void setMapBigram(Map<String, Integer> map){
        for(char c = 'а'; c <= 'я'; c++){
            for (char s = 'а'; s <= 'я'; s++){
                String str = "" + c + s;
                map.put(str, 0);
            }
        }
    }

    public void getBigrams(Map<String, Integer> mapBigram,ArrayList listBigrams, StringBuilder text){
        setMapBigram(mapBigram);
        String textToLowerCase = text.toString().toLowerCase();
        int counter;
        int j = 2;
        for (int i = 0; j < textToLowerCase.length(); i++, j++){
            try{
                String str = textToLowerCase.substring(i, j);
                counter = mapBigram.get(str) + 1;
                mapBigram.put(str, counter);
            } catch (NullPointerException e){
                i++;
                j++;
            }
        }
        sortBigramMap(mapBigram, listBigrams);
        printSortedMap(mapBigram, listBigrams);

    }

    private void printBigrams(Map<String, Integer> mapBigram){
        Set<Map.Entry<String, Integer>> set = mapBigram.entrySet();
        for (Map.Entry<String, Integer> map: set){
            System.out.print(map.getKey() + ": " + map.getValue() + "\n");
        }
        System.out.println("/////////////////////////////////////////////\n");
    }

    public void setBigramsAlphabet(ArrayList listFullBigrams, ArrayList listEncryptedBigrams, char[] decryptedBigramAlphabet){

        setDecryptedTableChars();

        setBigramsTable(listFullBigrams, listEncryptedBigrams);

        getDecryptedCharsTable(decryptedTableChars, decryptedIntBigrams);

        int maxIndex;
        int maxValue = decryptedIntBigrams[0][0];
        for (int i = 0; i < decryptedBigramAlphabet.length; i++){
            for (int j = 0; j < decryptedBigramAlphabet.length; j++){
                if ((decryptedIntBigrams[i][j] >= maxValue) && (decryptedIntBigrams[i][j] > 3)){
                    maxValue = decryptedIntBigrams[i][j];
                    maxIndex = j;
                    decryptedBigramAlphabet[i] = newAlphabet[maxIndex];
                }
            }
            maxValue = decryptedIntBigrams[i][0];
        }
        for (int i = 0; i < decryptedBigramAlphabet.length; i++){
            if (decryptedBigramAlphabet[i] == '\u0000'){
                decryptedBigramAlphabet[i] = decryptedFreqAlphabet[i];
            }
        }
        System.out.println("///////////////////////////////////////");
        for (int i = 0; i < decryptedBigramAlphabet.length; i++){
            System.out.print(decryptedBigramAlphabet[i] + " ");
        }
    }

    private void setBigramsTable(ArrayList listFullBigrams, ArrayList listEncryptedBigrams) {
        for (int i = 0; i < listFullBigrams.size() / 4; i++){
            char firstCharFullBig = listFullBigrams.get(i).toString().charAt(0);
            char secondCharFullBig = listFullBigrams.get(i).toString().charAt(1);
            char firstCharEncBig = listEncryptedBigrams.get(i).toString().charAt(0);
            char secondCharEncBig = listEncryptedBigrams.get(i).toString().charAt(1);
            for (int counterN = 0; counterN < 32; counterN++){
                if (firstCharEncBig == decryptedTableChars[counterN][0]){
                    for (int counterM = 0; counterM < 32; counterM++){
                        if (firstCharFullBig == decryptedTableChars[0][counterM]){
                            decryptedIntBigrams[counterN][counterM]++;
                            break;
                        }
                    }
                    break;
                }
            }
            for (int counterN = 0; counterN < 32; counterN++){
                if (secondCharEncBig == decryptedTableChars[counterN][0]){
                    for (int counterM = 0; counterM < 32; counterM++){
                        if (secondCharFullBig == decryptedTableChars[0][counterM]){
                            decryptedIntBigrams[counterN][counterM]++;
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    private void sortBigramMap(Map mapCharFreq, ArrayList listValues){
        mapCharFreq.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach((string) -> listValues.add(string));
    }

    private void printSortedMap (Map mapChar, ArrayList<Integer> listValues){
        Set<Map.Entry<String, Integer>> mapSet = mapChar.entrySet();
        for (Map.Entry<String, Integer> map: mapSet){
            System.out.println(map.getKey() + ": " + map.getValue());
        }
        System.out.println("////////////////////////////////////////////");
    }

    private void getDecryptedCharsTable(char[][] decryptedTableChars, int[][] decryptedIntBigrams){
        int i = 0;
        int j;
        System.out.print(" ");
        for (j = 0; j < 32; j++){
            System.out.print(" " + decryptedTableChars[i][j]);
        }
        System.out.println();
        j = 0;
        for (i = 0; i < 32; i++){
            System.out.print(decryptedTableChars[i][j] + " ");
            for (j = 0; j < 32; j++){
                System.out.print(decryptedIntBigrams[i][j] + " ");
            }
            j = 0;
            System.out.println();
        }
    }

}
