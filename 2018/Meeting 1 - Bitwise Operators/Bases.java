/**
 * CS 2110 Spring 2018 HW2
 * Part 2 - Coding with bases
 *
 * Global rules for this file:
 * - You may not use more than 2 conditionals per method. Conditionals are
 *   if-statements, if-else statements, or ternary expressions. The else block
 *   associated with an if-statement does not count toward this sum.
 * - You may not use more than 2 looping constructs per method. Looping
 *   constructs include for loops, while loops and do-while loops.
 * - You may not use nested loops.
 * - You may not declare any file-level variables.
 * - You may not declare any objects, other than String in select methods.
 * - You may not use switch statements.
 * - You may not use the unsigned right shift operator (>>>)
 * - You may not write any helper methods, or call any other method from this or
 *   another file to implement any method.
 * - The only Java API methods you are allowed to invoke are:
 *     String.length()
 *     String.charAt()
 * - You may not invoke the above methods from string literals.
 *     Example: "12345".length()
 * - When concatenating numbers with Strings, you may only do so if the number
 *   is a single digit.
 *
 * Method-specific rules for this file:
 * - You may not use multiplication, division or modulus in any method, EXCEPT
 *   decimalStringToInt.
 * - You may declare exactly one String variable each in intToBinaryString and
 *   and intToHexString.
 */
public class Bases
{
    /**
     * Convert a string containing ASCII characters (in binary) to an int.
     * You do not need to handle negative numbers. The Strings we will pass in will be
     * valid binary numbers, and able to fit in a 32-bit signed integer.
     *
     * Example: binaryStringToInt("111"); // => 7
     */
    public static int binaryStringToInt(String binary) {
        int result = 0;  //initialize an int variable
        for (int i = 0; i < binary.length(); i++) {  //iterate through the binary number
            if (binary.charAt(i) == 49) {  //if the binary number is 1 at the value
                result += 1 << (binary.length() - i - 1); //add its value to the overall result
            }
        }
        return result;  //return the result (the integer)
    }

    /**
     * Convert a string containing ASCII characters (in decimal) to an int.
     * You do not need to handle negative numbers. The Strings we will pass in will be
     * valid decimal numbers, and able to fit in a 32-bit signed integer.
     *
     * Example: decimalStringToInt("123"); // => 123
     */
    public static int decimalStringToInt(String decimal) {
        int result = 0; //initialize an int variable
        for (int i = 0; i < decimal.length(); i++) { //iterate through the decimal number
            result = result * 10 + decimal.charAt(i) - '0'; //add its value to the overall result
        }
        return result;  //return the result (the integer)
    }

    /**
     * Convert a string containing ASCII characters (in hex) to an int.
     * The input string will only contain numbers and uppercase letters A-F.
     * You do not need to handle negative numbers. The Strings we will pass in will be
     * valid hexadecimal numbers, and able to fit in a 32-bit signed integer.
     *
     * Example: hexStringToInt("A6"); // => 166
     */
    public static int hexStringToInt(String hex) {
        int result = 0;  //initialize an int variable
        for (int i = 0; i < hex.length(); i++) { //iterate through the hex number
            if (hex.charAt(i) >= 65) { //if the hex is A,B,C,D,E, or F
                result = (result + (hex.charAt(i) - 55)); //add its value -55 to the int
            } else {
                result = (result + hex.charAt(i) - 48);  //otherwise, add the character's value to int
            }
            if (i != hex.length() - 1) {   //if you're not at the most significant bit,
                result = result << 4;      // left shift the result by 4 to leave room for the next bits.
            }
        }
        return result;                     //return the int
    }

    /**
     * Convert a int into a String containing ASCII characters (in binary).
     * You do not need to handle negative numbers.
     * The String returned should contain the minimum number of characters necessary to
     * represent the number that was passed in.
     *
     * Example: intToBinaryString(7); // => "111"
     */
    public static String intToBinaryString(int binary) {
        String result = "";  //initialize a string variable
        int mask = 1;        //make a mask representing 1
        int i = 0;           //initialize i to start at 0
        if (binary == 0) {   //if the binary number == 0, the int is also 0, so add 0 to the string.
            return "0";
        }
        while (binary > 0) {
            if ((binary & mask) == 1) {  //if ANDing bits and 1 results in 1, add 1 to the binary string.
                result = "1" + result;
            } else {                     //if ANDing bits and 1 results in 0, add 0 to the binary string.
                result = "0" + result;
            }

            binary = binary >> 1;       //right shift bits by 1 now that the least significant bit has been added to the binary string.
            i++;                        //increment i
        }
        return result;                  //return the binary string
}



    /**
     * Convert a int into a String containing ASCII characters (in hexadecimal).
     * The output string should only contain numbers and uppercase letters A-F.
     * You do not need to handle negative numbers.
     * The String returned should contain the minimum number of characters necessary to
     * represent the number that was passed in.
     *
     * Example: intToHexString(166); // => "A6"
     */
    public static String intToHexString(int hex) {
        String result = ""; //initialize a string variable
        int rightmostHexDigit = hex & 0xF;  //initialize a variable to represent the rightmost Hex digit
        if (hex == 0) {                     //if the hex is just 0, add 0 to the hexadecimal string.
            return "0";
        }
        while (hex > 0) {                   //iterate through the int's bits while the int > 0
            if (rightmostHexDigit <= 9) {   //if the int is less than 10, you can just add the character's value to the decimal digit and add that to result.
                char decimalDigit = (char) ('0' + rightmostHexDigit);
                result = decimalDigit + result;
            } else {
                char hexDigit = (char) ('A' + (rightmostHexDigit - 10));  //if the int is between 10 and 15, it's represented by A-F in hexadecimal.
                result = hexDigit + result;                     //add the character's value - 10 to 'A', which is 65. Then add it to the result.
            }
            hex = hex >> 4;              //right shift the provided int by 4 now that you've taken care of the rightmost 4.
            rightmostHexDigit = hex & 0xF;  //reassign right most hex digit to the provided int ANDed with 1111
        }
        return result;                    //return the hex string
    }
}
