/*
 * Decompiled with CFR 0.150.
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class DitherFilter
extends PointFilter {
    protected static final int[] ditherMagic2x2Matrix;
    protected static final int[] ditherMagic4x4Matrix;
    public static final int[] ditherOrdered4x4Matrix;
    public static final int[] ditherLines4x4Matrix;
    public static final int[] dither90Halftone6x6Matrix;
    public static final int[] ditherOrdered6x6Matrix;
    public static final int[] ditherOrdered8x8Matrix;
    public static final int[] ditherCluster3Matrix;
    public static final int[] ditherCluster4Matrix;
    public static final int[] ditherCluster8Matrix;
    private int[] matrix = ditherMagic4x4Matrix;
    private int rows = 2;
    private int cols = 2;
    private int levels = 6;
    private int[] mod;
    private int[] div;
    private int[] map;
    private boolean colorDither = true;
    private boolean initialized = false;

    static {
        int[] arrn = new int[4];
        arrn[1] = 2;
        arrn[2] = 3;
        arrn[3] = 1;
        ditherMagic2x2Matrix = arrn;
        int[] arrn2 = new int[16];
        arrn2[1] = 14;
        arrn2[2] = 3;
        arrn2[3] = 13;
        arrn2[4] = 11;
        arrn2[5] = 5;
        arrn2[6] = 8;
        arrn2[7] = 6;
        arrn2[8] = 12;
        arrn2[9] = 2;
        arrn2[10] = 15;
        arrn2[11] = 1;
        arrn2[12] = 7;
        arrn2[13] = 9;
        arrn2[14] = 4;
        arrn2[15] = 10;
        ditherMagic4x4Matrix = arrn2;
        int[] arrn3 = new int[16];
        arrn3[1] = 8;
        arrn3[2] = 2;
        arrn3[3] = 10;
        arrn3[4] = 12;
        arrn3[5] = 4;
        arrn3[6] = 14;
        arrn3[7] = 6;
        arrn3[8] = 3;
        arrn3[9] = 11;
        arrn3[10] = 1;
        arrn3[11] = 9;
        arrn3[12] = 15;
        arrn3[13] = 7;
        arrn3[14] = 13;
        arrn3[15] = 5;
        ditherOrdered4x4Matrix = arrn3;
        int[] arrn4 = new int[16];
        arrn4[1] = 1;
        arrn4[2] = 2;
        arrn4[3] = 3;
        arrn4[4] = 4;
        arrn4[5] = 5;
        arrn4[6] = 6;
        arrn4[7] = 7;
        arrn4[8] = 8;
        arrn4[9] = 9;
        arrn4[10] = 10;
        arrn4[11] = 11;
        arrn4[12] = 12;
        arrn4[13] = 13;
        arrn4[14] = 14;
        arrn4[15] = 15;
        ditherLines4x4Matrix = arrn4;
        int[] arrn5 = new int[36];
        arrn5[0] = 29;
        arrn5[1] = 18;
        arrn5[2] = 12;
        arrn5[3] = 19;
        arrn5[4] = 30;
        arrn5[5] = 34;
        arrn5[6] = 17;
        arrn5[7] = 7;
        arrn5[8] = 4;
        arrn5[9] = 8;
        arrn5[10] = 20;
        arrn5[11] = 28;
        arrn5[12] = 11;
        arrn5[13] = 3;
        arrn5[15] = 1;
        arrn5[16] = 9;
        arrn5[17] = 27;
        arrn5[18] = 16;
        arrn5[19] = 6;
        arrn5[20] = 2;
        arrn5[21] = 5;
        arrn5[22] = 13;
        arrn5[23] = 26;
        arrn5[24] = 25;
        arrn5[25] = 15;
        arrn5[26] = 10;
        arrn5[27] = 14;
        arrn5[28] = 21;
        arrn5[29] = 31;
        arrn5[30] = 33;
        arrn5[31] = 25;
        arrn5[32] = 24;
        arrn5[33] = 23;
        arrn5[34] = 33;
        arrn5[35] = 36;
        dither90Halftone6x6Matrix = arrn5;
        int[] arrn6 = new int[64];
        arrn6[0] = 1;
        arrn6[1] = 59;
        arrn6[2] = 15;
        arrn6[3] = 55;
        arrn6[4] = 2;
        arrn6[5] = 56;
        arrn6[6] = 12;
        arrn6[7] = 52;
        arrn6[8] = 33;
        arrn6[9] = 17;
        arrn6[10] = 47;
        arrn6[11] = 31;
        arrn6[12] = 34;
        arrn6[13] = 18;
        arrn6[14] = 44;
        arrn6[15] = 28;
        arrn6[16] = 9;
        arrn6[17] = 49;
        arrn6[18] = 5;
        arrn6[19] = 63;
        arrn6[20] = 10;
        arrn6[21] = 50;
        arrn6[22] = 6;
        arrn6[23] = 60;
        arrn6[24] = 41;
        arrn6[25] = 25;
        arrn6[26] = 37;
        arrn6[27] = 21;
        arrn6[28] = 42;
        arrn6[29] = 26;
        arrn6[30] = 38;
        arrn6[31] = 22;
        arrn6[32] = 3;
        arrn6[33] = 57;
        arrn6[34] = 13;
        arrn6[35] = 53;
        arrn6[37] = 58;
        arrn6[38] = 14;
        arrn6[39] = 54;
        arrn6[40] = 35;
        arrn6[41] = 19;
        arrn6[42] = 45;
        arrn6[43] = 29;
        arrn6[44] = 32;
        arrn6[45] = 16;
        arrn6[46] = 46;
        arrn6[47] = 30;
        arrn6[48] = 11;
        arrn6[49] = 51;
        arrn6[50] = 7;
        arrn6[51] = 61;
        arrn6[52] = 8;
        arrn6[53] = 48;
        arrn6[54] = 4;
        arrn6[55] = 62;
        arrn6[56] = 43;
        arrn6[57] = 27;
        arrn6[58] = 39;
        arrn6[59] = 23;
        arrn6[60] = 40;
        arrn6[61] = 24;
        arrn6[62] = 36;
        arrn6[63] = 20;
        ditherOrdered6x6Matrix = arrn6;
        int[] arrn7 = new int[256];
        arrn7[0] = 1;
        arrn7[1] = 235;
        arrn7[2] = 59;
        arrn7[3] = 219;
        arrn7[4] = 15;
        arrn7[5] = 231;
        arrn7[6] = 55;
        arrn7[7] = 215;
        arrn7[8] = 2;
        arrn7[9] = 232;
        arrn7[10] = 56;
        arrn7[11] = 216;
        arrn7[12] = 12;
        arrn7[13] = 228;
        arrn7[14] = 52;
        arrn7[15] = 212;
        arrn7[16] = 129;
        arrn7[17] = 65;
        arrn7[18] = 187;
        arrn7[19] = 123;
        arrn7[20] = 143;
        arrn7[21] = 79;
        arrn7[22] = 183;
        arrn7[23] = 119;
        arrn7[24] = 130;
        arrn7[25] = 66;
        arrn7[26] = 184;
        arrn7[27] = 120;
        arrn7[28] = 140;
        arrn7[29] = 76;
        arrn7[30] = 180;
        arrn7[31] = 116;
        arrn7[32] = 33;
        arrn7[33] = 193;
        arrn7[34] = 17;
        arrn7[35] = 251;
        arrn7[36] = 47;
        arrn7[37] = 207;
        arrn7[38] = 31;
        arrn7[39] = 247;
        arrn7[40] = 34;
        arrn7[41] = 194;
        arrn7[42] = 18;
        arrn7[43] = 248;
        arrn7[44] = 44;
        arrn7[45] = 204;
        arrn7[46] = 28;
        arrn7[47] = 244;
        arrn7[48] = 161;
        arrn7[49] = 97;
        arrn7[50] = 145;
        arrn7[51] = 81;
        arrn7[52] = 175;
        arrn7[53] = 111;
        arrn7[54] = 159;
        arrn7[55] = 95;
        arrn7[56] = 162;
        arrn7[57] = 98;
        arrn7[58] = 146;
        arrn7[59] = 82;
        arrn7[60] = 172;
        arrn7[61] = 108;
        arrn7[62] = 156;
        arrn7[63] = 92;
        arrn7[64] = 9;
        arrn7[65] = 225;
        arrn7[66] = 49;
        arrn7[67] = 209;
        arrn7[68] = 5;
        arrn7[69] = 239;
        arrn7[70] = 63;
        arrn7[71] = 223;
        arrn7[72] = 10;
        arrn7[73] = 226;
        arrn7[74] = 50;
        arrn7[75] = 210;
        arrn7[76] = 6;
        arrn7[77] = 236;
        arrn7[78] = 60;
        arrn7[79] = 220;
        arrn7[80] = 137;
        arrn7[81] = 73;
        arrn7[82] = 177;
        arrn7[83] = 113;
        arrn7[84] = 133;
        arrn7[85] = 69;
        arrn7[86] = 191;
        arrn7[87] = 127;
        arrn7[88] = 138;
        arrn7[89] = 74;
        arrn7[90] = 178;
        arrn7[91] = 114;
        arrn7[92] = 134;
        arrn7[93] = 70;
        arrn7[94] = 188;
        arrn7[95] = 124;
        arrn7[96] = 41;
        arrn7[97] = 201;
        arrn7[98] = 25;
        arrn7[99] = 241;
        arrn7[100] = 37;
        arrn7[101] = 197;
        arrn7[102] = 21;
        arrn7[103] = 255;
        arrn7[104] = 42;
        arrn7[105] = 202;
        arrn7[106] = 26;
        arrn7[107] = 242;
        arrn7[108] = 38;
        arrn7[109] = 198;
        arrn7[110] = 22;
        arrn7[111] = 252;
        arrn7[112] = 169;
        arrn7[113] = 105;
        arrn7[114] = 153;
        arrn7[115] = 89;
        arrn7[116] = 165;
        arrn7[117] = 101;
        arrn7[118] = 149;
        arrn7[119] = 85;
        arrn7[120] = 170;
        arrn7[121] = 106;
        arrn7[122] = 154;
        arrn7[123] = 90;
        arrn7[124] = 166;
        arrn7[125] = 102;
        arrn7[126] = 150;
        arrn7[127] = 86;
        arrn7[128] = 3;
        arrn7[129] = 233;
        arrn7[130] = 57;
        arrn7[131] = 217;
        arrn7[132] = 13;
        arrn7[133] = 229;
        arrn7[134] = 53;
        arrn7[135] = 213;
        arrn7[137] = 234;
        arrn7[138] = 58;
        arrn7[139] = 218;
        arrn7[140] = 14;
        arrn7[141] = 230;
        arrn7[142] = 54;
        arrn7[143] = 214;
        arrn7[144] = 131;
        arrn7[145] = 67;
        arrn7[146] = 185;
        arrn7[147] = 121;
        arrn7[148] = 141;
        arrn7[149] = 77;
        arrn7[150] = 181;
        arrn7[151] = 117;
        arrn7[152] = 128;
        arrn7[153] = 64;
        arrn7[154] = 186;
        arrn7[155] = 122;
        arrn7[156] = 142;
        arrn7[157] = 78;
        arrn7[158] = 182;
        arrn7[159] = 118;
        arrn7[160] = 35;
        arrn7[161] = 195;
        arrn7[162] = 19;
        arrn7[163] = 249;
        arrn7[164] = 45;
        arrn7[165] = 205;
        arrn7[166] = 29;
        arrn7[167] = 245;
        arrn7[168] = 32;
        arrn7[169] = 192;
        arrn7[170] = 16;
        arrn7[171] = 250;
        arrn7[172] = 46;
        arrn7[173] = 206;
        arrn7[174] = 30;
        arrn7[175] = 246;
        arrn7[176] = 163;
        arrn7[177] = 99;
        arrn7[178] = 147;
        arrn7[179] = 83;
        arrn7[180] = 173;
        arrn7[181] = 109;
        arrn7[182] = 157;
        arrn7[183] = 93;
        arrn7[184] = 160;
        arrn7[185] = 96;
        arrn7[186] = 144;
        arrn7[187] = 80;
        arrn7[188] = 174;
        arrn7[189] = 110;
        arrn7[190] = 158;
        arrn7[191] = 94;
        arrn7[192] = 11;
        arrn7[193] = 227;
        arrn7[194] = 51;
        arrn7[195] = 211;
        arrn7[196] = 7;
        arrn7[197] = 237;
        arrn7[198] = 61;
        arrn7[199] = 221;
        arrn7[200] = 8;
        arrn7[201] = 224;
        arrn7[202] = 48;
        arrn7[203] = 208;
        arrn7[204] = 4;
        arrn7[205] = 238;
        arrn7[206] = 62;
        arrn7[207] = 222;
        arrn7[208] = 139;
        arrn7[209] = 75;
        arrn7[210] = 179;
        arrn7[211] = 115;
        arrn7[212] = 135;
        arrn7[213] = 71;
        arrn7[214] = 189;
        arrn7[215] = 125;
        arrn7[216] = 136;
        arrn7[217] = 72;
        arrn7[218] = 176;
        arrn7[219] = 112;
        arrn7[220] = 132;
        arrn7[221] = 68;
        arrn7[222] = 190;
        arrn7[223] = 126;
        arrn7[224] = 43;
        arrn7[225] = 203;
        arrn7[226] = 27;
        arrn7[227] = 243;
        arrn7[228] = 39;
        arrn7[229] = 199;
        arrn7[230] = 23;
        arrn7[231] = 253;
        arrn7[232] = 40;
        arrn7[233] = 200;
        arrn7[234] = 24;
        arrn7[235] = 240;
        arrn7[236] = 36;
        arrn7[237] = 196;
        arrn7[238] = 20;
        arrn7[239] = 254;
        arrn7[240] = 171;
        arrn7[241] = 107;
        arrn7[242] = 155;
        arrn7[243] = 91;
        arrn7[244] = 167;
        arrn7[245] = 103;
        arrn7[246] = 151;
        arrn7[247] = 87;
        arrn7[248] = 168;
        arrn7[249] = 104;
        arrn7[250] = 152;
        arrn7[251] = 88;
        arrn7[252] = 164;
        arrn7[253] = 100;
        arrn7[254] = 148;
        arrn7[255] = 84;
        ditherOrdered8x8Matrix = arrn7;
        int[] arrn8 = new int[36];
        arrn8[0] = 9;
        arrn8[1] = 11;
        arrn8[2] = 10;
        arrn8[3] = 8;
        arrn8[4] = 6;
        arrn8[5] = 7;
        arrn8[6] = 12;
        arrn8[7] = 17;
        arrn8[8] = 16;
        arrn8[9] = 5;
        arrn8[11] = 1;
        arrn8[12] = 13;
        arrn8[13] = 14;
        arrn8[14] = 15;
        arrn8[15] = 4;
        arrn8[16] = 3;
        arrn8[17] = 2;
        arrn8[18] = 8;
        arrn8[19] = 6;
        arrn8[20] = 7;
        arrn8[21] = 9;
        arrn8[22] = 11;
        arrn8[23] = 10;
        arrn8[24] = 5;
        arrn8[26] = 1;
        arrn8[27] = 12;
        arrn8[28] = 17;
        arrn8[29] = 16;
        arrn8[30] = 4;
        arrn8[31] = 3;
        arrn8[32] = 2;
        arrn8[33] = 13;
        arrn8[34] = 14;
        arrn8[35] = 15;
        ditherCluster3Matrix = arrn8;
        int[] arrn9 = new int[64];
        arrn9[0] = 18;
        arrn9[1] = 20;
        arrn9[2] = 19;
        arrn9[3] = 16;
        arrn9[4] = 13;
        arrn9[5] = 11;
        arrn9[6] = 12;
        arrn9[7] = 15;
        arrn9[8] = 27;
        arrn9[9] = 28;
        arrn9[10] = 29;
        arrn9[11] = 22;
        arrn9[12] = 4;
        arrn9[13] = 3;
        arrn9[14] = 2;
        arrn9[15] = 9;
        arrn9[16] = 26;
        arrn9[17] = 31;
        arrn9[18] = 30;
        arrn9[19] = 21;
        arrn9[20] = 5;
        arrn9[22] = 1;
        arrn9[23] = 10;
        arrn9[24] = 23;
        arrn9[25] = 25;
        arrn9[26] = 24;
        arrn9[27] = 17;
        arrn9[28] = 8;
        arrn9[29] = 6;
        arrn9[30] = 7;
        arrn9[31] = 14;
        arrn9[32] = 13;
        arrn9[33] = 11;
        arrn9[34] = 12;
        arrn9[35] = 15;
        arrn9[36] = 18;
        arrn9[37] = 20;
        arrn9[38] = 19;
        arrn9[39] = 16;
        arrn9[40] = 4;
        arrn9[41] = 3;
        arrn9[42] = 2;
        arrn9[43] = 9;
        arrn9[44] = 27;
        arrn9[45] = 28;
        arrn9[46] = 29;
        arrn9[47] = 22;
        arrn9[48] = 5;
        arrn9[50] = 1;
        arrn9[51] = 10;
        arrn9[52] = 26;
        arrn9[53] = 31;
        arrn9[54] = 30;
        arrn9[55] = 21;
        arrn9[56] = 8;
        arrn9[57] = 6;
        arrn9[58] = 7;
        arrn9[59] = 14;
        arrn9[60] = 23;
        arrn9[61] = 25;
        arrn9[62] = 24;
        arrn9[63] = 17;
        ditherCluster4Matrix = arrn9;
        int[] arrn10 = new int[256];
        arrn10[0] = 64;
        arrn10[1] = 69;
        arrn10[2] = 77;
        arrn10[3] = 87;
        arrn10[4] = 86;
        arrn10[5] = 76;
        arrn10[6] = 68;
        arrn10[7] = 67;
        arrn10[8] = 63;
        arrn10[9] = 58;
        arrn10[10] = 50;
        arrn10[11] = 40;
        arrn10[12] = 41;
        arrn10[13] = 51;
        arrn10[14] = 59;
        arrn10[15] = 60;
        arrn10[16] = 70;
        arrn10[17] = 94;
        arrn10[18] = 100;
        arrn10[19] = 109;
        arrn10[20] = 108;
        arrn10[21] = 99;
        arrn10[22] = 93;
        arrn10[23] = 75;
        arrn10[24] = 57;
        arrn10[25] = 33;
        arrn10[26] = 27;
        arrn10[27] = 18;
        arrn10[28] = 19;
        arrn10[29] = 28;
        arrn10[30] = 34;
        arrn10[31] = 52;
        arrn10[32] = 78;
        arrn10[33] = 101;
        arrn10[34] = 114;
        arrn10[35] = 116;
        arrn10[36] = 115;
        arrn10[37] = 112;
        arrn10[38] = 98;
        arrn10[39] = 83;
        arrn10[40] = 49;
        arrn10[41] = 26;
        arrn10[42] = 13;
        arrn10[43] = 11;
        arrn10[44] = 12;
        arrn10[45] = 15;
        arrn10[46] = 29;
        arrn10[47] = 44;
        arrn10[48] = 88;
        arrn10[49] = 110;
        arrn10[50] = 123;
        arrn10[51] = 124;
        arrn10[52] = 125;
        arrn10[53] = 118;
        arrn10[54] = 107;
        arrn10[55] = 85;
        arrn10[56] = 39;
        arrn10[57] = 17;
        arrn10[58] = 4;
        arrn10[59] = 3;
        arrn10[60] = 2;
        arrn10[61] = 9;
        arrn10[62] = 20;
        arrn10[63] = 42;
        arrn10[64] = 89;
        arrn10[65] = 111;
        arrn10[66] = 122;
        arrn10[67] = 127;
        arrn10[68] = 126;
        arrn10[69] = 117;
        arrn10[70] = 106;
        arrn10[71] = 84;
        arrn10[72] = 38;
        arrn10[73] = 16;
        arrn10[74] = 5;
        arrn10[76] = 1;
        arrn10[77] = 10;
        arrn10[78] = 21;
        arrn10[79] = 43;
        arrn10[80] = 79;
        arrn10[81] = 102;
        arrn10[82] = 119;
        arrn10[83] = 121;
        arrn10[84] = 120;
        arrn10[85] = 113;
        arrn10[86] = 97;
        arrn10[87] = 82;
        arrn10[88] = 48;
        arrn10[89] = 25;
        arrn10[90] = 8;
        arrn10[91] = 6;
        arrn10[92] = 7;
        arrn10[93] = 14;
        arrn10[94] = 30;
        arrn10[95] = 45;
        arrn10[96] = 71;
        arrn10[97] = 95;
        arrn10[98] = 103;
        arrn10[99] = 104;
        arrn10[100] = 105;
        arrn10[101] = 96;
        arrn10[102] = 92;
        arrn10[103] = 74;
        arrn10[104] = 56;
        arrn10[105] = 32;
        arrn10[106] = 24;
        arrn10[107] = 23;
        arrn10[108] = 22;
        arrn10[109] = 31;
        arrn10[110] = 35;
        arrn10[111] = 53;
        arrn10[112] = 65;
        arrn10[113] = 72;
        arrn10[114] = 80;
        arrn10[115] = 90;
        arrn10[116] = 91;
        arrn10[117] = 81;
        arrn10[118] = 73;
        arrn10[119] = 66;
        arrn10[120] = 62;
        arrn10[121] = 55;
        arrn10[122] = 47;
        arrn10[123] = 37;
        arrn10[124] = 36;
        arrn10[125] = 46;
        arrn10[126] = 54;
        arrn10[127] = 61;
        arrn10[128] = 63;
        arrn10[129] = 58;
        arrn10[130] = 50;
        arrn10[131] = 40;
        arrn10[132] = 41;
        arrn10[133] = 51;
        arrn10[134] = 59;
        arrn10[135] = 60;
        arrn10[136] = 64;
        arrn10[137] = 69;
        arrn10[138] = 77;
        arrn10[139] = 87;
        arrn10[140] = 86;
        arrn10[141] = 76;
        arrn10[142] = 68;
        arrn10[143] = 67;
        arrn10[144] = 57;
        arrn10[145] = 33;
        arrn10[146] = 27;
        arrn10[147] = 18;
        arrn10[148] = 19;
        arrn10[149] = 28;
        arrn10[150] = 34;
        arrn10[151] = 52;
        arrn10[152] = 70;
        arrn10[153] = 94;
        arrn10[154] = 100;
        arrn10[155] = 109;
        arrn10[156] = 108;
        arrn10[157] = 99;
        arrn10[158] = 93;
        arrn10[159] = 75;
        arrn10[160] = 49;
        arrn10[161] = 26;
        arrn10[162] = 13;
        arrn10[163] = 11;
        arrn10[164] = 12;
        arrn10[165] = 15;
        arrn10[166] = 29;
        arrn10[167] = 44;
        arrn10[168] = 78;
        arrn10[169] = 101;
        arrn10[170] = 114;
        arrn10[171] = 116;
        arrn10[172] = 115;
        arrn10[173] = 112;
        arrn10[174] = 98;
        arrn10[175] = 83;
        arrn10[176] = 39;
        arrn10[177] = 17;
        arrn10[178] = 4;
        arrn10[179] = 3;
        arrn10[180] = 2;
        arrn10[181] = 9;
        arrn10[182] = 20;
        arrn10[183] = 42;
        arrn10[184] = 88;
        arrn10[185] = 110;
        arrn10[186] = 123;
        arrn10[187] = 124;
        arrn10[188] = 125;
        arrn10[189] = 118;
        arrn10[190] = 107;
        arrn10[191] = 85;
        arrn10[192] = 38;
        arrn10[193] = 16;
        arrn10[194] = 5;
        arrn10[196] = 1;
        arrn10[197] = 10;
        arrn10[198] = 21;
        arrn10[199] = 43;
        arrn10[200] = 89;
        arrn10[201] = 111;
        arrn10[202] = 122;
        arrn10[203] = 127;
        arrn10[204] = 126;
        arrn10[205] = 117;
        arrn10[206] = 106;
        arrn10[207] = 84;
        arrn10[208] = 48;
        arrn10[209] = 25;
        arrn10[210] = 8;
        arrn10[211] = 6;
        arrn10[212] = 7;
        arrn10[213] = 14;
        arrn10[214] = 30;
        arrn10[215] = 45;
        arrn10[216] = 79;
        arrn10[217] = 102;
        arrn10[218] = 119;
        arrn10[219] = 121;
        arrn10[220] = 120;
        arrn10[221] = 113;
        arrn10[222] = 97;
        arrn10[223] = 82;
        arrn10[224] = 56;
        arrn10[225] = 32;
        arrn10[226] = 24;
        arrn10[227] = 23;
        arrn10[228] = 22;
        arrn10[229] = 31;
        arrn10[230] = 35;
        arrn10[231] = 53;
        arrn10[232] = 71;
        arrn10[233] = 95;
        arrn10[234] = 103;
        arrn10[235] = 104;
        arrn10[236] = 105;
        arrn10[237] = 96;
        arrn10[238] = 92;
        arrn10[239] = 74;
        arrn10[240] = 62;
        arrn10[241] = 55;
        arrn10[242] = 47;
        arrn10[243] = 37;
        arrn10[244] = 36;
        arrn10[245] = 46;
        arrn10[246] = 54;
        arrn10[247] = 61;
        arrn10[248] = 65;
        arrn10[249] = 72;
        arrn10[250] = 80;
        arrn10[251] = 90;
        arrn10[252] = 91;
        arrn10[253] = 81;
        arrn10[254] = 73;
        arrn10[255] = 66;
        ditherCluster8Matrix = arrn10;
    }

    public void setMatrix(int[] matrix) {
        this.matrix = matrix;
    }

    public int[] getMatrix() {
        return this.matrix;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public int getLevels() {
        return this.levels;
    }

    public void setColorDither(boolean colorDither) {
        this.colorDither = colorDither;
    }

    public boolean getColorDither() {
        return this.colorDither;
    }

    protected void initialize() {
        this.rows = this.cols = (int)Math.sqrt(this.matrix.length);
        this.map = new int[this.levels];
        for (int i = 0; i < this.levels; ++i) {
            int v;
            this.map[i] = v = 255 * i / (this.levels - 1);
        }
        this.div = new int[256];
        this.mod = new int[256];
        int rc = this.rows * this.cols + 1;
        for (int i = 0; i < 256; ++i) {
            this.div[i] = (this.levels - 1) * i / 256;
            this.mod[i] = i * rc / 256;
        }
    }

    @Override
    public int filterRGB(int x, int y, int rgb) {
        if (!this.initialized) {
            this.initialized = true;
            this.initialize();
        }
        int a = rgb & 0xFF000000;
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;
        int col = x % this.cols;
        int row = y % this.rows;
        int v = this.matrix[row * this.cols + col];
        if (this.colorDither) {
            r = this.map[this.mod[r] > v ? this.div[r] + 1 : this.div[r]];
            g = this.map[this.mod[g] > v ? this.div[g] + 1 : this.div[g]];
            b = this.map[this.mod[b] > v ? this.div[b] + 1 : this.div[b]];
        } else {
            int value = (r + g + b) / 3;
            g = b = this.map[this.mod[value] > v ? this.div[value] + 1 : this.div[value]];
            r = b;
        }
        return a | r << 16 | g << 8 | b;
    }

    public String toString() {
        return "Colors/Dither...";
    }
}
