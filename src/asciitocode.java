import java.util.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class asciitocode{
    public static String lineAnalyzer(String CurrLine) {
        String fixedLine;

        List<String> charArr = new ArrayList<>(Arrays.asList(CurrLine.split("")));
        int size = charArr.size();

        for (int i=0; i<size; i++) {
            // checks for: \ " and ' if found, adds a \ in front of each.
            if (charArr.get(i).equals("\"") || charArr.get(i).equals("\\") || charArr.get(i).equals("\'")) {
                charArr.add(i, "\\");
                size++;
                i++;
            }
        }

        // adds quotations to line for printing purposes.
        charArr.addFirst("\"");
        size = charArr.size();
        charArr.add(size, "\"");

        // converts line array back into string.
        StringBuilder sb = new StringBuilder();
        for (String ch : charArr) {
            sb.append(ch);
        }
        fixedLine = sb.toString();

        return fixedLine;
    }
    public static int modeSelector() {
        Scanner scnr2 = new Scanner(System.in);
        int mode;
        while (true) {
            try {
                System.out.println("select mode | 1. single-line\n\t\t\t| 2. multi-line\n\t\t\t| 3. change language\n\t\t\t| 4. help\n");
                mode = scnr2.nextInt();
                if (mode > 0 && mode < 4) {
                    break;
                } else {
                    System.out.println("\ninvalid input, please try again\n");
                }
            } catch (Exception e) {
                System.out.println("\ninvalid input, please try again\n");
                scnr2.nextLine(); // clears new line
            }
        }
        return mode;

    }
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        String tempLine;
        String finalCode = "";
        boolean count = false;
        int mode;
        String selectedMode = "";
        String selectedLanguage = "Ascii -> Java Code";
        // outputs ascii art. created using this program!
        System.out.print(" ▄▄▄· .▄▄ ·  ▄▄· ▪  ▪      ▄▄▄▄▄           ▄▄·       ·▄▄▄▄  ▄▄▄ ."+"\n"+"▐█ ▀█ ▐█ ▀. ▐█ ▌▪██ ██     •██  ▪         ▐█ ▌▪▪     ██▪ ██ ▀▄.▀·"+"\n"+"▄█▀▀█ ▄▀▀▀█▄██ ▄▄▐█·▐█·     ▐█.▪ ▄█▀▄     ██ ▄▄ ▄█▀▄ ▐█· ▐█▌▐▀▀▪▄"+"\n"+"▐█ ▪▐▌▐█▄▪▐█▐███▌▐█▌▐█▌     ▐█▌·▐█▌.▐▌    ▐███▌▐█▌.▐▌██. ██ ▐█▄▄▌"+"\n"+" ▀  ▀  ▀▀▀▀ ·▀▀▀ ▀▀▀▀▀▀     ▀▀▀  ▀█▄▀▪    ·▀▀▀  ▀█▄▀▪▀▀▀▀▀•  ▀▀▀ "+"\n");
        System.out.println("currently converting | "+selectedLanguage+"\n");

        mode = modeSelector();

        while (mode == 4) {    // help mode
            System.out.println("\n1. single-line puts all your ascii in one LONG line of code (more concise)");
            System.out.println("2. multi-line puts all your ascii in multiple lines of code (recommended)");
            System.out.println();
            mode = modeSelector();
        }

        while (mode == 3) {     // language selector, java, python, c++, rust.
            System.out.println("\nplease select a language :)");
            System.out.println("1. Java 2. Python 3. Rust");
            System.out.println();
            // change language somewhere here.
            mode = modeSelector();

        }
        System.out.println("\ninstructions | 1. copy and paste ascii art \n\t\t\t | 2. type \"enter0\" under art to input\n");

        // reads ascii art inputted.
        while (true) {
            tempLine = scnr.nextLine();

            if (tempLine.isEmpty()) {
                // if line empty, skip.
                continue;
            }

            if (tempLine.equals("enter0")) {
                // inputs ascii art to program.
                break;
            }

            // formats current line to account for: \ " and '
            tempLine = lineAnalyzer(tempLine);

            if (mode == 1) {
                // single line.
                if (count) {
                    // add a + to string after first iteration.
                    tempLine = "+" + tempLine;
                }
                // "line" + "\n" + | DO NOT TOUCH !
                finalCode += tempLine + "+\"\\n\"";
                count = true;

            }else if (mode == 2) {
                // multi-line.
                finalCode += "System.out.println("+tempLine+");\n";

            }
        }

        if (mode == 1) {
            finalCode = "System.out.print("+finalCode+");";
            selectedMode = "single-line";
        }else{
            selectedMode = "multi-line";
        }

        // copies ascii art to clipboard!
        StringSelection stringSelection = new StringSelection(finalCode);
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(stringSelection, null);

        System.out.println("\nautomatically copied "+selectedMode+" java code to clipboard!");
        scnr.close();
    }
}
