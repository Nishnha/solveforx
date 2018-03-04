/**
 * CS 2110 Spring 2018 HW2
 * Part 3 - Coding with bitwise operators
 *
 * Global rules for this file:
 * - All of these functions must be completed in ONE line. That means they should be
 *   of the form "return [...];", with a single semicolon. No partial credit will be
 *   awarded for any Method that isn't completed in one line.
 *
 * - You may not use more than 2 conditionals per method, which for this file would
 *   just be ternary expressions.
 * - You may not declare any variables.
 * - You may not use casting.
 * - You may not use the unsigned right shift operator (>>>)
 * - You may not write any helper methods, or call any other method from this or
 *   another file to implement any method.
 * - In functions where addition or subtraction is allowed, you may only do so
 *   with the number 1.
 *
 * Method-specific rules for this file:
 * - You may not use multiplication, division or modulus in any method.
 * - You may not use addition, subtraction, or conditionals in setByte,
 *   getShort, pack, or xor.
 * - You noy not use bitshifting or the exclusive OR operator (^) in xor.
 *
 * Finally, your code must be robust and concise. If we asked you to print out the values 1 through 100
 * and you wrote 100 separate print statements, then sure, it works, but no one's gonna hire a coder who does
 * that. Likewise, you will NOT get credit for verbose answers for which there is a much more concise
 * solution. For instance, if you need to shift a value by n*4 times, you may not write x << n << n << n << n
 * or you will get no credit, because there is a much more concise way to do this in only two operations by
 * first shifting n. Keep this in mind, ESPECIALLY in the first 2 functions in this file.
 *
 * Remember that for this assignment, All bit masks must be written in hexadecimal.
 * This is the convention for masks and makes it much easier for the programmer to
 * understand the code. If you write a mask in any other base you will lose points.
 *
 * All of these functions accept ints as parameters because if you pass in a number
 * (which is of type int by default) into a Method accepting a byte, then the Java
 * compiler will complain even if the number would fit into that type.
 *
 * Now, keep in mind the return value is also an int. Please read the comments about how
 * many significant bits to return and make sure that the other bits are not set or else
 * you will not get any points for that test case.
 * i.e if I say to return 6 bits and you return 0xFFFFFFFF, you will lose points.
 *
 * Definitions of types:
 * nibble - 4 bits
 * byte   - 8 bits
 * short  - 16 bits
 * int    - 32 bits
 */
public class Operations
{
    /**
     * Get a 16-bit short from an int.
     *
     * Ints are made of 2 shorts, numbered like so:
     *   S1 | S0
     *
     * For a graphical representation of the bits:
     *   33222222222211111111110000000000
     *   10987654321098765432109876543210 <= starting with bit 0
     *   +--------------+---------------+
     *   |      1       |       0       |
     *
     * Examples:
     *     getShort(0x56781234, 0); // => 0x1234
     *     getShort(0xFF254545, 1); // => 0x5678
     *
     * Note: Remember, no multiplication allowed!
     *
     * @param num The int to get a short from.
     * @param which Determines which short gets returned - 0 for
     *              least-significant short.
     *
     * @return A short corresponding to the "which" parameter from num.
     */
    int getShort(int num, int which)
    {
        return 0xFFFF & (num >> (which << 4));
        //To obtain a short, which is 16 bits, you know you're going to have to
        //use 0xFFFF to represent 16 bits. And 0xFFFF with the int shifted right
        //by the which parameter shifted by 4. Which needs to be shifted by 4
        //because there are 4 sets of 4 bits in a short. The resulting number
        //will be a short.

    }

    /**
     * Set a 4-bit nibble in an int.
     *
     * Ints are made of 8 nibbles, numbered like so:
     *   N7 | N6 | N5 | N4 | N3 | N2 | N1 | N0
     *
     * For a graphical representation of the bits:
     *   33222222222211111111110000000000
     *   10987654321098765432109876543210 <= starting with bit 0
     *   ----+---+---+---+---+---+---+---
     *   | 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0
     *
     * Examples:
     *     setNibble(0xAAA5BBC6, 0x2, 0); // => 0xAAA5BBC2
     *     setNibble(0x56B218F9, 0x8, 3); // => 0x56B288F9
     *
     * Note: Remember, no multiplication allowed!
     *
     * @param num The int that will be modified.
     * @param a_nibble The nibble to insert into the integer.
     * @param which Selects which nibble to modify - 0 for least-significant
     * nibble.
     *
     * @return The modified int.
     */
    int setNibble(int num, int a_nibble, int which)
    {
        return (num & ~ (0x0000000F << (which << 2))) | (a_nibble << (which << 2));
        //Since an int has 32 bits, and 8 nibbles of 4 bits each, the which parameter must be multiplied by 4.
        //Then left shift 00000000000000000000000000001111 by which * 4, so that the 1111 aligns with the nibble
        //that is going to be altered. NOT the result so that it will turn into 111100000000... Then AND this result with
        //the given int so that all the 0s and 1s turn into 0s. Then OR this  number by the result of left shifting the
        // given nibble by which * 4. This will modify the correct nibble.

    }


    /**
     * Pack 2 shorts into an int.
     *
     * The shorts should be placed consecutively in the 32-bit int in the order
     * that they appear in the parameters
     *
     * Example:
     *     pack(0x1234, 0x5678); // => 0x12345678
     *     pack(0xDEAD, 0xBEEF); // => 0xDEADBEEF
     *
     * @param s1 Most significant short (will always be a 16-bit number).
     * @param s0 Least significant short (will always be a 16-bit number).
     *
     * @return a 32-bit value formatted like so: s1s0
     */
    int pack(int s1, int s0)
    {
        return (s1 << 16) | s0;
    }

    /**
     * Extract a range of bits from a number.
     *
     * Examples:
     *     bitRange(0x00001234, 0, 4);  // => 0x00000004
     *     bitRange(0x00001234, 4, 8);  // => 0x00000023
     *     bitRange(0x12345678, 0, 28); // => 0x02345678
     *     bitRange(0x55555555, 5, 7);  // => 0x0000002A
     *
     * Note: We will only pass in values 1 to 32 for n.
     *
     * @param num An n-bit 2's complement number.
     * @param s The starting bit to grab
     * @param n The number of bits to return.
     * @return The n-bit number num[s:s+n-1].
     */
    int bitRange(int num, int s, int n)
    {
        return (~(0xFFFFFFFF << n) & (num >> s));
        //first left shift 1 by n, the number of bits, in order to make sure the
        // number return has the right number of bits. Then subtract 1 so that
        // the number wil be 0 followed by 1s. Then AND that number with the
        // original number right shifted by s, so that you grasp the right bits.
        // Since you AND, all 1's will only be outputted if both inputs are 1s.
    }


    /**
     * NOTE: For this method, you may only use &, |, and ~.
     *
     * Perform an exclusive-or on two 32-bit ints.
     *
     * Examples:
     *     xor(0xFF00FF00, 0x00FF00FF); // => 0xFFFFFFFF
     *     xor(0x12345678, 0x87654321); // => 0x95511559
     *
     * @param num1 An int
     * @param num2 Another int
     *
     * @return num1 ^ num2
     */
    int xor(int num1, int num2)
    {
        return (num1 | num2) & (~num1 | ~num2); //first use OR between the two
        //numbers. Then NOT each number and OR the results. This removes extra
        //set bits. Then ADD the previous result to the latter, and the result is
        //the XOR operation.
    }


    /**
     * Return true if the given number is a power of 2.
     *
     * Examples:
     *     powerOf2(1024); // => true
     *     powerOf2(23);   // => false
     *
     * Note: Make sure you handle ALL the cases!
     * Note2: Remember: Robust and concise! Do not just return an OR of all the powers of 2.
     *
     * @param num a 32-bit int. Since this is an int, it is SIGNED!
     * @return true if num is a power of 2.
     */ 
    boolean powerOf2(int num)
    {
        return (num > 0) && ((num & (num -1)) == 0);   //if the number is a power of
            // two already, one less will result in a binary number that is all 1s.
            //So when you & the number with number - 1, you get all 0s, which evaluates
            //to true. Otherwise, you will get a 1 in the binary number, which evaluates
            //to false.
    }
}
