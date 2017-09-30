/*
 * Copyright 2017 Daniel Pedraza-Arcega
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.danzx.zekke.client.util;

import static java.util.Objects.requireNonNull;

/**
 * Encodes/Decodes strings and bytes in Base64.
 */
public class Base64 {

    private Base64() {
        throw new AssertionError();
    }

    public final static byte[] encode(byte[] byteData) {
        requireNonNull(byteData, "byteData cannot be null");

        /*
         * Declare working variables including an array of bytes that will
         * contain the encoded data to be returned to the caller. Note that the
         * encoded array is about 1/3 larger than the input. This is because
         * every group of 3 bytes is being encoded into 4 bytes.
         */
        int iSrcIdx; // index into source (byteData)
        int iDestIdx; // index into destination (byteDest)
        // byte[] byteData = (byte[])byteData_in.clone();
        // byte[] byteData = byteData_in;
        byte[] byteDest = new byte[((byteData.length + 2) / 3) * 4];

        /*
         * Walk through the input array, 24 bits at a time, converting them from
         * 3 groups of 8 to 4 groups of 6 with two unset bits between. as per
         * Base64 spec see
         * http://www.javaworld.com/javaworld/javatips/jw-javatip36-p2.html for
         * example explanation
         */
        for (iSrcIdx = 0, iDestIdx = 0; iSrcIdx < byteData.length - 2; iSrcIdx += 3) {
            byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] >>> 2) & 077);
            byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx + 1] >>> 4) & 017 | (byteData[iSrcIdx] << 4) & 077);
            byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx + 2] >>> 6) & 003 | (byteData[iSrcIdx + 1] << 2) & 077);
            byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx + 2] & 077);
        }

        /*
         * If the number of bytes we received in the input array was not an even
         * multiple of 3, convert the remaining 1 or 2 bytes.
         */
        if (iSrcIdx < byteData.length) {
            byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] >>> 2) & 077);
            if (iSrcIdx < byteData.length - 1) {
                byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx + 1] >>> 4) & 017 | (byteData[iSrcIdx] << 4) & 077);
                byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx + 1] << 2) & 077);
            } else byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] << 4) & 077);
        }

        /*
         * Use the encoded data as indexes into the Base64 alphabet. (The Base64
         * alphabet is completely documented in RFC 1521.)
         */
        for (iSrcIdx = 0; iSrcIdx < iDestIdx; iSrcIdx++) {
            if (byteDest[iSrcIdx] < 26) byteDest[iSrcIdx] = (byte) (byteDest[iSrcIdx] + 'A');
            else if (byteDest[iSrcIdx] < 52) byteDest[iSrcIdx] = (byte) (byteDest[iSrcIdx] + 'a' - 26);
            else if (byteDest[iSrcIdx] < 62) byteDest[iSrcIdx] = (byte) (byteDest[iSrcIdx] + '0' - 52);
            else if (byteDest[iSrcIdx] < 63) byteDest[iSrcIdx] = '+';
            else byteDest[iSrcIdx] = '/';
        }

        /* Pad any unused bytes in the destination string with '=' characters. */
        for (; iSrcIdx < byteDest.length; iSrcIdx++) byteDest[iSrcIdx] = '=';

        return byteDest;
    }

    public final static byte[] decode(byte[] byteData) {
        requireNonNull(byteData, "byteData cannot be null");

        /*
         * Declare working variables including an array of bytes that will
         * contain the decoded data to be returned to the caller. Note that the
         * decoded array is about 3/4 smaller than the input. This is because
         * every group of 4 bytes is being encoded into 3 bytes.
         */
        int iSrcIdx; // index into source (byteData)
        int reviSrcIdx; // index from end of the src array (byteData)
        int iDestIdx; // index into destination (byteDest)
        byte[] byteTemp = new byte[byteData.length];

        /*
         * remove any '=' chars from the end of the byteData they would have
         * been padding to make it up to groups of 4 bytes note that I don't
         * need to remove it just make sure that when progressing throug array
         * we don't go past reviSrcIdx ;-)
         */
        for (reviSrcIdx = byteData.length; reviSrcIdx -1 > 0 && byteData[reviSrcIdx -1] == '='; reviSrcIdx--) {
            ; // do nothing. I'm just interested in value of reviSrcIdx
        }

        /* sanity check */
        if (reviSrcIdx -1 == 0) return null; /* ie all padding */

        /*
         * Set byteDest, this is smaller than byteData due to 4 -> 3 byte munge.
         * Note that this is an integer division! This fact is used in the logic
         * l8r. to make sure we don't fall out of the array and create an
         * OutOfBoundsException and also in handling the remainder
         */
        byte byteDest[] = new byte[((reviSrcIdx * 3) / 4)];

        /*
         * Convert from Base64 alphabet to encoded data (The Base64 alphabet is
         * completely documented in RFC 1521.) The order of the testing is
         * important as I use the '<' operator which looks at the hex value of
         * these ASCII chars. So convert from the smallest up
         *
         * do all of this in a new array so as not to edit the original input
         */
        for (iSrcIdx = 0; iSrcIdx < reviSrcIdx; iSrcIdx++) {
            if (byteData[iSrcIdx] == '+') byteTemp[iSrcIdx] = 62;
            else if (byteData[iSrcIdx] == '/') byteTemp[iSrcIdx] = 63;
            else if (byteData[iSrcIdx] < '0' + 10) byteTemp[iSrcIdx] = (byte) (byteData[iSrcIdx] + 52 - '0');
            else if (byteData[iSrcIdx] < ('A' + 26)) byteTemp[iSrcIdx] = (byte) (byteData[iSrcIdx] - 'A');
            else if (byteData[iSrcIdx] < 'a' + 26) byteTemp[iSrcIdx] = (byte) (byteData[iSrcIdx] + 26 - 'a');
        }

        /*
         * 4bytes -> 3bytes munge Walk through the input array, 32 bits at a
         * time, converting them from 4 groups of 6 to 3 groups of 8 removing
         * the two unset most significant bits of each sorce byte as this was
         * filler, as per Base64 spec. stop before potential buffer overun on
         * byteDest, remember that byteDest is 3/4 (integer division) the size
         * of input and won't necessary divide exactly (ie iDestIdx must be <
         * (integer div byteDest.length / 3)*3 see
         * http://www.javaworld.com/javaworld/javatips/jw-javatip36-p2.html for
         * example
         */
        for (iSrcIdx = 0, iDestIdx = 0; iSrcIdx < reviSrcIdx && iDestIdx < ((byteDest.length / 3) * 3); iSrcIdx += 4) {
            byteDest[iDestIdx++] = (byte) ((byteTemp[iSrcIdx] << 2) & 0xFC | (byteTemp[iSrcIdx + 1] >>> 4) & 0x03);
            byteDest[iDestIdx++] = (byte) ((byteTemp[iSrcIdx + 1] << 4) & 0xF0 | (byteTemp[iSrcIdx + 2] >>> 2) & 0x0F);
            byteDest[iDestIdx++] = (byte) ((byteTemp[iSrcIdx + 2] << 6) & 0xC0 | byteTemp[iSrcIdx + 3] & 0x3F);
        }

        /*
         * tidy up any remainders if iDestIdx >= ((byteDest.length / 3)*3) but
         * iSrcIdx < reviSrcIdx then we have at most 2 extra destination bytes
         * to fill and posiblr 3 input bytes yet to process
         */
        if (iSrcIdx < reviSrcIdx) {
            if (iSrcIdx < reviSrcIdx - 2) {
                // "3 input bytes left"
                byteDest[iDestIdx++] = (byte) ((byteTemp[iSrcIdx] << 2) & 0xFC | (byteTemp[iSrcIdx + 1] >>> 4) & 0x03);
                byteDest[iDestIdx++] = (byte) ((byteTemp[iSrcIdx + 1] << 4) & 0xF0 | (byteTemp[iSrcIdx + 2] >>> 2) & 0x0F);
            } else if (iSrcIdx < reviSrcIdx - 1) {
                // "2 input bytes left"
                byteDest[iDestIdx++] = (byte) ((byteTemp[iSrcIdx] << 2) & 0xFC | (byteTemp[iSrcIdx + 1] >>> 4) & 0x03);
            }
            /*
             * wont have just one input byte left (unless input wasn't base64
             * encoded ) due to the for loop steps and array sizes, after "="
             * pad removed, but for compleatness
             */
            else throw new IllegalArgumentException("Warning: 1 input bytes left to process. This was not Base64 input");
        }
        return byteDest;
    }
}
