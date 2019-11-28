import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner(new File("input.txt"));
        List<String> testData = new ArrayList<>();
        String is = in.nextLine();
        for (int i = 0; i < is.length(); i++) {
            int random_number1 = (int) (Math.random() * 4);
            char rep = 'f';
            switch (random_number1) {
                case 0:
                    rep = 'f';
                    break;
                case 1:
                    rep = '/';
                    break;
                case 2:
                    rep = '<';
                    break;
                case 3:
                    rep = '>';
                    break;
            }
            testData.add(is.substring(0, i) + rep + is.substring(i + 1));
        }
        //testData.add(in.nextLine());

        in.close();

        List<String> testResults = new ArrayList<>();

        for (String file : testData) {
            String result = file;
//            if (file.equals("")) {
//                continue;
//            }

            Pattern pattern = Pattern.compile("</?[a-z]+>");
            Matcher matcher = pattern.matcher(file);

            Deque<String> stack = new ArrayDeque<>();

            int start = -1;
            int end = -1;
            int wrongStart = -1;
            int wrongEnd = -1;
            int oldEnd = 0;
            int superStart = -1;
            int superEnd = -1;
            while (matcher.find()) {
                start = matcher.start();
                end = matcher.end();

                /*
                НАЧАЛО ОГРОМНОЙ ЛОГИКИ ПО НЕВЕРНЫМ ЛЕКСЕМАМ
                 */
                if (oldEnd != start) {
                    superStart = oldEnd;
                    superEnd = start;
                    String wrongName = file.substring(superStart, superEnd);

                    // if (wrongName.charAt(0) == '<' && wrongName.charAt(wrongName.length() - 1) == '>' && (wrongName.charAt(1) == '<' || wrongName.charAt(1) == '>' || wrongName.charAt(1) == '/') && (wrongName.charAt(wrongName.length() - 2) != '<' && wrongName.charAt(wrongName.length() - 2) != '>')) {
//                        boolean slash = false;
//                        wrongName = wrongName.substring(1, wrongName.length() - 1);
//                        int slashIn = wrongName.substring(1).indexOf('/');
//                        String searchPattern = "/?[a-z,>,<,/]" + wrongName.substring(1);
//                        if (slashIn != -1) {
//                            searchPattern = "/?[a-z,>,<,/]" + wrongName.substring(1, slashIn+1) + "[a-z,/]" + wrongName.substring(slashIn+2);
//                        }
//                        Pattern pattern1 = Pattern.compile(searchPattern);
//                        Matcher matcher1 = pattern1.matcher(file);
//                        int matches = 0;
//                        String newReplace = "";
//                        while (matcher1.find()) {
//                            matches++;
//                            if (!wrongName.equals(file.substring(matcher1.start(), matcher1.end()))) {
//                                newReplace = file.substring(matcher1.start(), matcher1.end());
//                                if (newReplace.charAt(0) == '/') {
//                                    slash = true;
//                                    //wrongName = wrongName.substring(1);
//                                } else {
//                                    slash = false;
//                                }
//                            }
//                        }
//                        if (matches == 2) {
//                            if (slash) {
//                                result = file.substring(0, superStart) + '<' + newReplace.substring(1) + file.substring(superEnd - 1);
//                            } else {
//                                result = file.substring(0, superStart) + "</" + newReplace.substring(1) + file.substring(superEnd - 1);
//                            }
//                            break;
//                        }
                    if (wrongName.charAt(0) == '<' && wrongName.charAt(wrongName.length() - 1) == '>') {
                        wrongName = wrongName.substring(1, wrongName.length() - 1);
                        if (wrongName.length() == 1 || (wrongName.substring(1).contains("/")) && (wrongName.charAt(0) != '/') || wrongName.contains("<") || wrongName.contains(">")) {
                            Deque<String> stacker = new ArrayDeque<>();
                            Matcher matcher2 = pattern.matcher(file);
                            while (matcher2.find()) {
                                start = matcher2.start();
                                end = matcher2.end();
                                String tagName = file.substring(start + 1, end - 1);

                                if (file.charAt(start + 1) != '/') {
                                    stacker.addLast(tagName);
                                } else {
                                    if (!stacker.isEmpty() && stacker.getLast().equals(tagName.substring(1))) {
                                        stacker.removeLast();
                                    } else {
                                        String res = file.substring(start + 1, end - 1);
                                        result = file.substring(0, superStart + 1) + res.substring(1) + file.substring(superEnd - 1);
                                        break;
                                    }
                                }
                            }
                            if (!file.equals(result)) {
                                break;
                            }
                        } else {
                            Deque<String> stacker = new ArrayDeque<>();
                            Matcher matcher2 = pattern.matcher(file);
                            while (matcher2.find()) {
                                start = matcher2.start();
                                end = matcher2.end();
                                String tagName = file.substring(start + 1, end - 1);

                                if (file.charAt(start + 1) != '/') {
                                    stacker.addLast(tagName);
                                } else {
                                    if (!stacker.isEmpty() && stacker.getLast().equals(tagName.substring(1))) {
                                        stacker.removeLast();
                                    } else if (!stacker.isEmpty()) {
                                        String res = stacker.getLast();
                                        result = file.substring(0, superStart + 1) + "/" + res + file.substring(superEnd - 1);
                                        break;
                                    }

                                }
                            }
                            if (!file.equals(result)) {
                                break;
                            }
                        }
                    }

                    if (wrongName.charAt(0) == '<' && wrongName.charAt(wrongName.length() - 1) == '>' && (wrongName.charAt(wrongName.length() - 2) == '<' || wrongName.charAt(wrongName.length() - 2) == '>')) {
                        boolean slash = false;
                        wrongName = wrongName.substring(1, wrongName.length() - 1);
                        if (wrongName.charAt(0) == '/') {
                            wrongName = wrongName.substring(1);
                        }
                        String searchPattern = "/?" + wrongName.substring(0, wrongName.length() - 1) + "[a-z,>,<]";
                        Pattern pattern1 = Pattern.compile(searchPattern);
                        Matcher matcher1 = pattern1.matcher(file);
                        int matches = 0;
                        String newReplace = "";
                        while (matcher1.find()) {
                            matches++;
                            if (!wrongName.equals(file.substring(matcher1.start() + 1, matcher1.end()))) {
                                newReplace = file.substring(matcher1.start(), matcher1.end());
                                if (newReplace.charAt(0) == '/') {
                                    slash = true;
                                    //wrongName = wrongName.substring(1);
                                } else {
                                    slash = false;
                                }
                            }
                        }
                        if (matches == 2) {
                            if (slash) {
                                result = file.substring(0, superStart) + '<' + newReplace.substring(1) + file.substring(superEnd - 1);
                            } else {
                                result = file.substring(0, superStart) + "</" + newReplace + file.substring(superEnd - 1);
                            }
                            break;
                        }
                    }


                    if (wrongName.charAt(0) != '<') {
                        int index = superStart;
                        int rightIndex = 0;
                        boolean flag = false;
                        while (index >= 0) {
                            if (file.charAt(index) == '<') {
                                flag = true;
                                break;
                            }
                            index--;
                        }
                        if (flag) {
                            boolean slash = false;
                            String fullTag = file.substring(index + 1, superEnd - 1);
                            if (fullTag.charAt(0) == '/') {
                                slash = true;
                                fullTag = fullTag.substring(1);
                            }
                            rightIndex = fullTag.indexOf('>');
                            //System.out.println(file);

                            String searchPattern = fullTag.substring(0, rightIndex) + "[a-z,>]" + fullTag.substring(rightIndex + 1);
                            Pattern pattern1 = Pattern.compile(searchPattern);
                            Matcher matcher1 = pattern1.matcher(file);
                            int matches = 0;
                            String newReplace = "";
                            while (matcher1.find()) {
                                matches++;
                                if (!fullTag.equals(file.substring(matcher1.start(), matcher1.end()))) {
                                    newReplace = file.substring(matcher1.start(), matcher1.end());
                                }
                            }
                            if (matches == 2) {
                                if (!slash) {
                                    result = file.substring(0, index) + '<' + newReplace + file.substring(superEnd - 1);
                                } else {
                                    result = file.substring(0, index) + "</" + newReplace + file.substring(superEnd - 1);
                                }

                                break;
                            }
                        }

                        result = file.substring(0, superStart) + '<' + wrongName.substring(1) + file.substring(superEnd);
                        break;
                    } else if (wrongName.charAt(wrongName.length() - 1) != '>') {
                        int index = superEnd;
                        int rightIndex = 0;
                        boolean flag = false;
                        while (index <= file.length()) {
                            if (file.charAt(index) == '>') {
                                flag = true;
                                break;
                            }
                            index++;
                        }
                        if (flag) {
                            boolean slash = false;
                            String fullTag = file.substring(superStart + 1, index - 1);
                            if (fullTag.charAt(0) == '/') {
                                slash = true;
                                fullTag = fullTag.substring(1);
                            }
                            rightIndex = fullTag.indexOf('<');
                            String searchPattern = fullTag.substring(0, rightIndex) + "[a-z,<]" + fullTag.substring(rightIndex + 1);
                            Pattern pattern1 = Pattern.compile(searchPattern);
                            Matcher matcher1 = pattern1.matcher(file);
                            int matches = 0;
                            String newReplace = "";
                            while (matcher1.find()) {
                                matches++;
                                if (!fullTag.equals(file.substring(matcher1.start(), matcher1.end()))) {
                                    newReplace = file.substring(matcher1.start(), matcher1.end());
                                }
                            }
                            if (matches == 2) {
                                if (!slash) {
                                    result = file.substring(0, superStart) + '<' + newReplace + file.substring(index - 1);
                                } else {
                                    result = file.substring(0, superStart) + "</" + newReplace + file.substring(index - 1);
                                }

                                break;
                            }
                        }

                        result = file.substring(0, superEnd - 1) + '>' + file.substring(superEnd);
                        break;
                    }
                    break;
                } else {
                    oldEnd = end;
                }
                /*
                КОНЕЦ ОГРОМНОЙ ЛОГИКИ
                 */

                String tagName = file.substring(start + 1, end - 1);

                if (file.charAt(start + 1) != '/') { // добавляем открывающий тег в стек
                    if (stack.isEmpty()) { // ...конечно, если стек пустой
                        stack.addLast(tagName);
                    } else if (tagName.substring(1).equals(stack.getLast())) { // а иначе говно какое-то
                        result = file.substring(0, start + 1) + "/" + tagName.substring(1) + file.substring(end - 1);
                        break;
                    } else {
                        stack.addLast(tagName);
                    }
                } else {
                    /*
                    вычисляем, на скорлько символов расходятся теги (если на один, то один и тот же тег с ошибкой)
                     */
                    int diff = 0;
                    if (!stack.isEmpty()) {
                        if ((tagName.length() == (stack.getLast().length() + 1))) {
                            for (int i = 0; i < stack.getLast().length(); i++) {
                                if (tagName.charAt(i + 1) != stack.getLast().charAt(i)) {
                                    diff++;
                                }
                            }
                        } else {
                            diff = Integer.MAX_VALUE;
                        }
                    }

                    if (stack.isEmpty() || diff > 1) { // если стек пустой, значит это элемент с ложным '/' (на месте которого должна стоять буква), либо такой элемент уже найден и мы пытаемся его закрыть
                        if (wrongStart == -1) { //если ложный элемент
                            wrongStart = start;
                            wrongEnd = end;
                        } else { // если пытаемся закрыть его
                            result = file.substring(0, wrongStart + 1) + file.substring(start + 2, end - 1) + file.substring(wrongEnd - 1);
                            break;
                        }
                    } else if (tagName.substring(1).equals(stack.getLast())) { // если элемент равен последнему в стеке, удаляем его (тег "закрылся")
                        stack.removeLast();
                    } else { // а если не равен, значит в нём ошибка, и нужно заменить его на тот, что в стеке
                        result = file.substring(0, start + 2) + stack.getLast() + file.substring(end - 1);
                        break;
                    }
                }
            }

            /*
            проверка для последнего тега, если он ошибочный (матчер до него не дойдёт)
            */
            if (end != file.length() && result.equals(file)) {
                superStart = oldEnd;
                superEnd = file.length();
                String wrongName = file.substring(superStart, superEnd);

                if (wrongName.charAt(0) == '<' && wrongName.charAt(wrongName.length() - 1) == '>' && (wrongName.charAt(1) == '<' || wrongName.charAt(1) == '>' || wrongName.charAt(1) == '/') && (wrongName.charAt(wrongName.length() - 2) != '<' && wrongName.charAt(wrongName.length() - 2) != '>')) {
                    boolean slash = false;
                    wrongName = wrongName.substring(1, wrongName.length() - 1);
                    String searchPattern = "/?[a-z,>,<,/]" + wrongName.substring(1);
                    Pattern pattern1 = Pattern.compile(searchPattern);
                    Matcher matcher1 = pattern1.matcher(file);
                    int matches = 0;
                    String newReplace = "";
                    while (matcher1.find()) {
                        matches++;
                        if (!wrongName.equals(file.substring(matcher1.start(), matcher1.end()))) {
                            newReplace = file.substring(matcher1.start(), matcher1.end());
                            if (newReplace.charAt(0) == '/') {
                                slash = true;
                                //wrongName = wrongName.substring(1);
                            } else {
                                slash = false;
                            }
                        }
                    }
                    if (matches == 2) {
                        if (slash) {
                            result = file.substring(0, superStart) + '<' + newReplace.substring(1) + file.substring(superEnd - 1);
                        } else {
                            result = file.substring(0, superStart) + "</" + newReplace.substring(1) + file.substring(superEnd - 1);
                        }
                    }
                } else if (wrongName.charAt(0) == '<' && wrongName.charAt(wrongName.length() - 1) == '>' && (wrongName.charAt(wrongName.length() - 2) == '<' || wrongName.charAt(wrongName.length() - 2) == '>')) {
                    boolean slash = false;
                    wrongName = wrongName.substring(1, wrongName.length() - 1);
                    if (wrongName.charAt(0) == '/') {
                        wrongName = wrongName.substring(1);
                    }
                    String searchPattern = "/?" + wrongName.substring(0, wrongName.length() - 1) + "[a-z,>,<]";
                    Pattern pattern1 = Pattern.compile(searchPattern);
                    Matcher matcher1 = pattern1.matcher(file);
                    int matches = 0;
                    String newReplace = "";
                    while (matcher1.find()) {
                        matches++;
                        if (!wrongName.equals(file.substring(matcher1.start() + 1, matcher1.end()))) {
                            newReplace = file.substring(matcher1.start(), matcher1.end());
                            if (newReplace.charAt(0) == '/') {
                                slash = true;
                                //wrongName = wrongName.substring(1);
                            } else {
                                slash = false;
                            }
                        }
                    }
                    if (matches == 2) {
                        if (slash) {
                            result = file.substring(0, superStart) + '<' + newReplace.substring(1) + file.substring(superEnd - 1);
                        } else {
                            result = file.substring(0, superStart) + "</" + newReplace + file.substring(superEnd - 1);
                        }
                        break;
                    }
                } else if (wrongName.charAt(0) != '<') {
                    //       result = file.substring(0, superStart) + '<' + wrongName.substring(1) + file.substring(superEnd);
                    int index = superStart;
                    int rightIndex = 0;
                    boolean flag = false;
                    while (index >= 0) {
                        if (file.charAt(index) == '<') {
                            flag = true;
                            break;
                        }
                        index--;
                    }
                    if (flag) {
                        boolean slash = false;
                        String fullTag = file.substring(index + 1, superEnd - 1);
                        if (fullTag.charAt(0) == '/') {
                            slash = true;
                            fullTag = fullTag.substring(1);
                        }
                        rightIndex = fullTag.indexOf('>');
                        String searchPattern = fullTag.substring(0, rightIndex) + "[a-z,>]" + fullTag.substring(rightIndex + 1);
                        Pattern pattern1 = Pattern.compile(searchPattern);
                        Matcher matcher1 = pattern1.matcher(file);
                        int matches = 0;
                        String newReplace = "";
                        while (matcher1.find()) {
                            matches++;
                            if (!fullTag.equals(file.substring(matcher1.start(), matcher1.end()))) {
                                newReplace = file.substring(matcher1.start(), matcher1.end());
                            }
                        }
                        if (matches == 2) {
                            if (!slash) {
                                result = file.substring(0, index) + '<' + newReplace + file.substring(superEnd - 1);
                            } else {
                                result = file.substring(0, index) + "</" + newReplace + file.substring(superEnd - 1);
                            }
                        } else {
                            result = file.substring(0, superStart) + '<' + wrongName.substring(1) + file.substring(superEnd);
                        }
                    }
                } else if (wrongName.charAt(wrongName.length() - 1) != '>') {
                    result = file.substring(0, superEnd - 1) + '>';
                }
            }

            if (!result.equals(is)) {
                System.out.println("> " + file);
                System.out.println(result);
            }
            //out.println(result);
            testResults.add(result);
        }


//        for (int i = 0; i < testResults.size() - 1; i++) {
//            if (!testResults.get(i).equals(testResults.get(i + 1))) {
//                out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
//                out.println("index: " + i);
//            }
//        }

        out.close();
    }
}