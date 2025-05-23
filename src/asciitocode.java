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
            if (charArr.get(i).equals("\"") || charArr.get(i).equals("\\") || charArr.get(i).equals("'")) {
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

    public static void printMenu() {
      System.out.println("select mode |");
      System.out.println("            | 1. single-line");
      System.out.println("            | 2. multi-line");
      System.out.println("            | 3. change language");
      System.out.println("            | 4. help");
    }

    // allows user to select mode
    public static int modeSelector() {
        Scanner scnr = new Scanner(System.in);
        int mode;
        String modeStr;
        while (true) {
            try {
                printMenu();
                modeStr = scnr.nextLine();
                mode = Integer.parseInt(modeStr);
                if (mode > 0 && mode < 4) {
                    break;
                } else {
                    System.out.println("\ninvalid input, please try again\n");
                }
            } catch (Exception e) {
                System.out.println("\ninvalid input, please try again\n");
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
        String selectedMode;
        String selectedLanguage = "ASCII art -> Java Code";
        int langaugeMode = 1; // 1=java 2=python
        // outputs ascii art. created using this program!
        System.out.print("""
                 ▄▄▄· .▄▄ ·  ▄▄· ▪  ▪      ▄▄▄▄▄           ▄▄·       ·▄▄▄▄  ▄▄▄ .
                ▐█ ▀█ ▐█ ▀. ▐█ ▌▪██ ██     •██  ▪         ▐█ ▌▪▪     ██▪ ██ ▀▄.▀·
                ▄█▀▀█ ▄▀▀▀█▄██ ▄▄▐█·▐█·     ▐█.▪ ▄█▀▄     ██ ▄▄ ▄█▀▄ ▐█· ▐█▌▐▀▀▪▄
                ▐█ ▪▐▌▐█▄▪▐█▐███▌▐█▌▐█▌     ▐█▌·▐█▌.▐▌    ▐███▌▐█▌.▐▌██. ██ ▐█▄▄▌
                 ▀  ▀  ▀▀▀▀ ·▀▀▀ ▀▀▀▀▀▀     ▀▀▀  ▀█▄▀▪    ·▀▀▀  ▀█▄▀▪▀▀▀▀▀•  ▀▀▀\s
                """);
        System.out.println("currently converting | "+selectedLanguage+"\n");

        mode = modeSelector();

        // help mode
        while (mode == 4) {
            System.out.println("\n1. single-line puts all your ascii in one LONG line of code (more concise)");
            System.out.println("2. multi-line puts all your ascii in multiple lines of code (recommended)");
            System.out.println();
            mode = modeSelector();
        }

        // language selector, java, python, c++, rust.
        while (mode == 3) {
            System.out.println("\nplease select a language :)");
            System.out.println("1. Java 2. Python");
            System.out.println();
            langaugeMode = scnr.nextInt();
            // change language somewhere here.
            if (langaugeMode == 1) {
                selectedLanguage = "ASCII art -> Java Code";
                System.out.println("currently converting | "+selectedLanguage+"\n");
            }else if (langaugeMode == 2) {
                selectedLanguage = "ASCII art -> Python Code";
                System.out.println("\ncurrently converting | "+selectedLanguage+"\n");
            }
            mode = modeSelector();
        }
        
        System.out.println();
        System.out.println("instructions |");
        System.out.println("             | 1. copy and paste ascii art");
        System.out.println("             | 2. type \"enter0\" under art to input\n");

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
                if (langaugeMode == 1) {
                    // java
                    finalCode += "System.out.println("+tempLine+");\n";
                }else if (langaugeMode == 2) {
                    // python
                    finalCode += "print("+tempLine+")\n";
                }
            }
        }

        // single line
        if (mode == 1) {
            if (langaugeMode == 1) {
                // java
                finalCode = "System.out.print("+finalCode+");";
            }else if (langaugeMode == 2) {
                // python
                finalCode = "print("+finalCode+")";
            }
            selectedMode = "single-line";
        }else{
            selectedMode = "multi-line";
        }
        
        System.out.println();
        System.out.println(finalCode);

        // copies ascii art to clipboard!
        StringSelection stringSelection = new StringSelection(finalCode);
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(stringSelection, null);

        System.out.println("\nautomatically copied "+selectedMode+" java code to clipboard!");
        scnr.close();
    }
}
