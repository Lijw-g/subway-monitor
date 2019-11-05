package com.subway.monitor.utils;


public class CRC16Util {

    public static int get_crc16(int[] bufData, int buflen) {
        int ret = 0;
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;


        if (buflen == 0) {
            return ret;
        }
        for (i = 0; i < buflen; i++) {
            CRC ^= ((int) bufData[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }

        System.out.println(Integer.toHexString(CRC));
        return CRC;
    }

    public static String creatCrc16_s(String data) {
        int[] datas = DataUtil.creatDateInt(data);
        int crcResult = get_crc16(datas, datas.length);
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(crcResult & 0x000000ff));
        sb.append(Integer.toHexString(crcResult >> 8));
        return sb.toString();
    }

}
