package scripts.addonProductisation;

public class SplitString {

    public static String splitString(String input, String regex, int position) throws IllegalArgumentException {
        String[] arrOfStr = input.split(regex);
        if (position < 1 || position > arrOfStr.length) {
            throw new IllegalArgumentException("Position should be within the range of the split array length.");
        }
        return arrOfStr[position - 1];
    }

    public static void main(String[] args) {
        String input = "one,two,three,four";
        String regex = ",";
        int position = 2;
        try {
            String result = splitString(input, regex, position);
            System.out.println("Successfully split string and stored the output into a runtime variable: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not split string with given condition. " + regex);

        }
    }
}