import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        CharactersLab charactersLab = CharactersLab.get();

        main.readText("res/Voina_i_mir.txt", charactersLab.originalFullText);
        main.readText("res/Tom 1 Chapter 9.txt", charactersLab.originalChapterText);

        charactersLab.setDataOfText(charactersLab.originalFullText,
                charactersLab.mapFullCharacterFreq,
                charactersLab.listFullFreques,
                true,
                "Data of original text");

        charactersLab.setDataOfText(charactersLab.originalChapterText,
                charactersLab.mapChapterFreq,
                charactersLab.listChapterFreques,
                true,
                "Data of chapter text");

        charactersLab.setOriginalAlphabet();

        charactersLab.encryptCaesar(charactersLab.originalChapterText, charactersLab.originalAlphabet);

        charactersLab.setDataOfText(charactersLab.encryptedText,
                charactersLab.mapEncryptedChapterFreq,
                charactersLab.listEncryptedFreques,
                true,
                "Data of encrypted chapter text");

        charactersLab.setDecryptedFreqAlphabet(charactersLab.listFullFreques, charactersLab.listEncryptedFreques);

        charactersLab.getAlphabets(charactersLab.originalAlphabet, charactersLab.newAlphabet, charactersLab.decryptedFreqAlphabet);

        charactersLab.decryptTextByFreques(charactersLab.encryptedText, charactersLab.newAlphabet, charactersLab.decryptedFreqAlphabet);

        main.checkText(charactersLab.originalChapterText, charactersLab.decryptedText);

        charactersLab.getBigrams(charactersLab.mapFullBigram,charactersLab.listFullBigrams, charactersLab.originalFullText);
        charactersLab.getBigrams(charactersLab.mapEncryptedBigram,charactersLab.listEncryptedBigrams, charactersLab.encryptedText);

        charactersLab.setBigramsAlphabet(charactersLab.listFullBigrams, charactersLab.listEncryptedBigrams, charactersLab.decryptedBigramAlphabet);

        charactersLab.decryptTextByBigrams(charactersLab.encryptedText, charactersLab.newAlphabet, charactersLab.decryptedBigramAlphabet);
        main.checkText(charactersLab.originalChapterText, charactersLab.decryptedByBigramsText);

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
        percent = ((float)numOfGoodChars / (float) textLength) * 100;
        System.out.println("\n///////////////////////////////////////////////////");
        System.out.println ("Точность криптоанализа " + percent + "%");
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

    static void writeToFile(String filePath, StringBuilder text){
        try(FileWriter foutEncrypt = new FileWriter(filePath)){
            foutEncrypt.write(text.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
