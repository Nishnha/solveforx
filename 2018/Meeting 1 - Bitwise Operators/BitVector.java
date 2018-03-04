/**
 * CS 2110 Spring 2018 HW2
 * Part 1 - Coding a bit vector
 *
 * Global rules for this file:
 * - You may not use multiplication, division or modulus in any method.
 * - You may not use more than 2 conditionals per method, and you may only use
 *   them in select methods. Conditionals are if-statements, if-else statements,
 *   or ternary expressions. The else block associated with an if-statement does
 *   not count toward this sum.
 * - You may not use looping constructs in most methods. Looping constructs
 *   include for loops, while loops and do-while loops. See below for exceptions
 * - In those methods that allow looping, you may not use more than 2 looping
 *   constructs, and they may not be nested.
 * - You may not declare any file-level variables.
 * - You may not declare any objects, other than String in select methods.
 * - You may not use switch statements.
 * - You may not use casting.
 * - You may not use the unsigned right shift operator (>>>)
 * - You may not write any helper methods, or call any other method from this
 *   file, another file, or the Java API to implement any method.
 * - You may only perform addition or subtraction with the number 1.
 *
 * Method-specific rules for this file:
 * - You may declare exactly one String variable, in toString only.
 * - Iteration may not be used in set, clear, toggle, isSet or isClear.
 * - Conditionals may not be used in set, clear, or toggle.
 */
public class BitVector
{
    /**
     * 32-bit data initialized to all zeros. Here is what you will be using to represent
     * the Bit Vector. Do not change its scope from private.
     */
    private int bits;

    /** You may not add any more fields to this class other than the given one. */

    /**
     * Sets the bit (sets to 1) pointed to by index.
     * @param index index of which bit to set.
     *        0 for the least significant bit (right most bit).
     *        31 for the most significant bit.
     */
    public void set(int index)
    {
        bits = bits | (1 << index);
        //To set a bit, OR the bit with 1 left shifted by the index provided.
        //This will make a binary number in the form 10000, where a 1 is followed
        //by zeros. When you OR a number with 1, 1 is the result, so that bit is set.
    }
    //

    /**
     * Clears the bit (sets to 0) pointed to by index.
     * @param index index of which bit to set.
     * 	      0 for the least significant bit (right most bit).
     * 	      31 for the most significant bit.
     */
    public void clear(int index)
    {
        bits = bits & ~(1 << index);
        //To clear a bit, AND the bit with the NOT of 1 left shifted by the index provided.
        //This will make a binary number in the form 01111, where a 0 is followed
        //by 1s. When you AND a number with 0, 0 is the result, so that bit is cleared.
    }

    /**
     * Toggles the bit (sets to the opposite of its current value) pointed to by index.
     * @param index index of which bit to set.
     * 	      0 for the least significant bit (right most bit).
     * 	      31 for the most significant bit.
     */
    public void toggle(int index)
    {
        bits = bits ^ (1 << index);
        //To toggle a bit, XOR the bit with 1 left shifted by the index provided.
        //This will make a binary number in the form 10000, where a 1 is followed
        //by 0s. When you XOR a number with 0, 1 is the result, and vice versa,
        //so that bit is toggled.
    }

    /**
     * Returns true if the bit pointed to by index is currently set.
     * @param index index of which bit to check.
     * 	      0 for the least significant bit (right-most bit).
     * 	      31 for the most significant bit.
     * @return true if the bit is set, false if the bit is clear.
     *         If the index is out of range (index >= 32), then return false.
     */
    public boolean isSet(int index)
    {
        return ((bits & (1 << index)) != 0) && (index < 32);
        //For a bit to be set, it must be 1. To check if a bit is set, AND the
        //bits with 1 left shifted by index. Check if this value is 1. If so, that
        //specific bit is set. Also check if index is less than 32. If not,
        //the bit at the out of bounds index is obviously 0, and so clear, not set.
    }

    /**
     * Returns true if the bit pointed to by index is currently clear.
     * @param index index of which bit to check.
     * 	      0 for the least significant bit (right-most bit).
     * 	      31 for the most significant bit.
     * @return true if the bit is clear, false if the bit is set.
     *         If the index is out of range (index >= 32), then return true.
     */
    public boolean isClear(int index)
    {
        return (index >= 32) || ((bits & (1 << index)) == 0);
        //For a bit to be clear, it must be 0. To check if a bit is clear, AND the
        //bits with 1 left shifted by index. Check if this value is 0. If so, that
        //specific bit is clear. Also check if index is greater than or equal to 32.
        // If it is, the bit at the out of bounds index is obviously 0, and so clear.
    }
    /**
     * Returns a string representation of this object.
     * Return a string with the binary representation of the bit vector.
     * You may use String concatenation (+) here.
     * You must return a 32-bit string representation.
     * i.e if the bits field was 2, then return "00000000000000000000000000000010"
     */
    public String toPaddedBinaryString()
    {
        String result = "";                 //Make an empty string variable.
        int mask = 1;                       //Make a mask variable to represent 1.
        int i = 0;                          //Set an i counter starting at 0.
        while (i < 32) {                    //Loop through the while loop as long as i < 32.
            if ((bits & mask) == 1) {       //if ANDing bits and 1 results in 1, add 1 to the binary string.
                result = "1" + result;
            } else {                        //if ANDing bits and 1 results in 0, add 0 to the binary string.
                result = "0" + result;
        }
        bits = bits >> 1;                   //right shift bits by 1 now that the least significant bit has been added to the binary string.
        i++;                                //increment i until it reaches 32
    }
        return result;                      //return the binary string
}

    /**
     * Returns the number of bits currently set (=1) in this bit vector.
     * You may obviously use the ++ operator to increment your counter.
     */
    public int onesCount()
    {
        int count = 0;                      //Make a counter and initialize it as 0.
        for (int i = 0; i < 32; i++){       //Iterate through the bits in bits.
            if ((bits & (1 << i)) != 0) {   //If bits AND 1 shifted by i results in 1, increment the counter.
                count++;
            }
        }
        return count;                       //return count
    }


    /**
     * Returns the number of bits currently clear (=0) in this bit vector.
     * You may obviously use the ++ operator to increment your counter.
     */
    public int zerosCount()
    {
        int count = 0;                            //Make a counter and initialize it as 0.
        for (int i = 0; i < 32; i++){             //Iterate through the bits in bits.
            if ((bits & (1 << i)) == 0) {         //If bits AND 1 shifted by i results in 0, increment the counter.
                count++;
            }
        }
        return count;                            //return count
    }

    /**
     * Returns the "size" of this BitVector. The size of this bit vector is defined
     * to be the minimum number of bits that will represent all of the ones.
     * For example, the size of the bit vector 00010000 will be 5.
     */
    public int size()
    {
        int size = 1;                          //Initialize a size variable as 1, as 0 has size 1.
        for (int i = 0; i < 32; i++){          //Iterate through the bits in bits.
            if ((bits & (1 << i)) != 0) {      //If bits AND 1 shifted by i results in 1, increment size by i + 1.
                size = i + 1;                  //You add i + 1 instead of i because i is the index, so size is actually 1 more.
            }
        }
        return size;                           //return size
    }
}
