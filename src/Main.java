import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
//        calc(scanner.nextLine());
       System.out.println(calc(scanner.nextLine()));
    }

    public static String calc(String input) throws Exception {
        String[] parts = input.split(" ");
        String[] operations = new String[]{"+", "-", "/", "*"};

        HashMap<String, String> map = new HashMap<>();
        map.put("I", "1");
        map.put("II", "2");
        map.put("III", "3");
        map.put("IV", "4");
        map.put("V", "5");
        map.put("VI", "6");
        map.put("VII", "7");
        map.put("VIII", "8");
        map.put("IX", "9");
        map.put("X", "10");

        if (parts.length != 3) {
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        String firstNumber = parts[0];
        String operation = parts[1];
        String secondNumber = parts[2];
        boolean isRoman = false;

        if (map.containsKey(firstNumber) && map.containsKey(secondNumber)) {
            isRoman = true;
            firstNumber = map.get(firstNumber);
            secondNumber = map.get(secondNumber);
        }

        if (!Arrays.asList(operations).contains(operation)) {
            throw new Exception("Допустимы только + - / *");
        }

        if (checkIsBigger(firstNumber) || checkIsBigger(secondNumber) || checkIsBigger(intToArabic(firstNumber)) || checkIsBigger(intToArabic(secondNumber))) {
            throw new Exception("допустимы значения не больше 10");
        }

        if (!map.containsValue(firstNumber) && !map.containsKey(firstNumber)  || !map.containsValue(secondNumber) && !map.containsKey(secondNumber)) {
            throw new Exception("строка не является математической операцией");
        }


        if (map.containsKey(firstNumber) && !map.containsKey(secondNumber) || !map.containsKey(firstNumber) && map.containsKey(secondNumber)) {
            throw new Exception("используются одновременно разные системы счисления");
        }

        if (isRoman) {
            if (action(firstNumber, operation, secondNumber) < 1) {
                throw new Exception("в римской системе нет отрицательных чисел");
            }
            return intToRoman(action(firstNumber, operation, secondNumber));
        }
        return Integer.toString(action(firstNumber, operation, secondNumber));
    }

    public static boolean checkIsBigger(String string) {
        try {
            int numFromString = Integer.parseInt(string);
            return numFromString > 10;
        } catch (Exception e) {
            return false;
        }
    }

    public static String intToRoman(int num)
    {
        String[] keys = new String[] { "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        int[] vals = new int[] { 100, 90, 50, 40, 10, 9, 5, 4, 1 };

        StringBuilder ret = new StringBuilder();
        int ind = 0;

        while(ind < keys.length)
        {
            while(num >= vals[ind])
            {
                var d = num / vals[ind];
                num = num % vals[ind];
                ret.append(keys[ind].repeat(d));
            }
            ind++;
        }

        return ret.toString();
    }

    public static String intToArabic(String roman) {

        int[] keys={0, 1, 4, 5, 9, 10, 40, 50, 90, 100};
        String[] vals={"", "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};


        int result = 0;
        for (int i = keys.length - 1; i >= 0; i-- ) {
            while (roman.indexOf(vals[i]) == 0 && !vals[i].isEmpty()) {
                result += keys[i];
                roman = roman.substring(vals[i].length());
            }
        }
        return Integer.toString(result);
    }

    public static int action(String firstNumber, String operation, String secondNumber) {
        return switch (operation) {
            case "+" -> Integer.parseInt(firstNumber) + Integer.parseInt(secondNumber);
            case "-" -> Integer.parseInt(firstNumber) - Integer.parseInt(secondNumber);
            case "*" -> Integer.parseInt(firstNumber) * Integer.parseInt(secondNumber);
            case "/" -> Integer.parseInt(firstNumber) / Integer.parseInt(secondNumber);
            default -> 0;
        };
    }
}