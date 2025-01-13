package com.mzl.insta360demo.util;

import java.io.*;

/**
 * @Description: 切段MP3文件工具类
 * @Author: mzl
 */
public class SplitMp3Util2 {

    public static void main(String[] args) {
        File audioFile = new File("/Users/mazhenle/Desktop/Work/Insta360/PSP/音频切段/test.mp3");
        // 每段大小：5分钟（以字节为单位）
        long segmentSize = 5 * 60 * 1024 * 1024;

        try (RandomAccessFile raf = new RandomAccessFile(audioFile, "r")) {
            long totalSize = raf.length();
            int segmentCount = (int) Math.ceil((double) totalSize / segmentSize);

            // 1MB 缓冲区
            byte[] buffer = new byte[1024 * 1024];

            for (int i = 0; i < segmentCount; i++) {
                long startByte = i * segmentSize;
                long endByte = Math.min((i + 1) * segmentSize, totalSize);

                raf.seek(startByte);
                try (FileOutputStream fos = new FileOutputStream("/Users/mazhenle/Desktop/Work/Insta360/PSP/音频切段/" + i + ".mp3")) {
                    long bytesToRead = endByte - startByte;
                    while (bytesToRead > 0) {
                        int bytesRead = raf.read(buffer, 0, (int) Math.min(buffer.length, bytesToRead));
                        if (bytesRead == -1) {
                            break;
                        }
                        fos.write(buffer, 0, bytesRead);
                        bytesToRead -= bytesRead;
                    }
                }
            }

            System.out.println("音频文件切割完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
