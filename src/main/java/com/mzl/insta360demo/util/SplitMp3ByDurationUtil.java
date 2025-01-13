package com.mzl.insta360demo.util;

import java.io.*;

/**
 * @Description: 切段MP3文件工具类【按每段时长】
 * @Author: mzl
 */
public class SplitMp3ByDurationUtil {

    /**
     * 每段时长, 单位：毫秒【5分钟】
     */
    private static final int SEGMENT_DURATION = 11 * 60 * 1000;

    /**
     * 音频总时长, 单位：毫秒【3时24分29秒】
     */
    private static final long TOTAL_DURATION = (3 * 60 * 60 + 24 * 60 + 29) * 1000;

    public static void main(String[] args) {
        // 输入文件路径
        String inputFilePath = "/Users/mazhenle/Desktop/Work/Insta360/PSP/音频切段/test.mp3";
        // 输出目录
        String outputDirPath = "/Users/mazhenle/Desktop/Work/Insta360/PSP/音频切段/";

        try {
            splitAudioFile(inputFilePath, outputDirPath);
            System.out.println("音频切割完成！");
        } catch (IOException e) {
            System.err.println("音频切割失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 切割音频文件
     *
     * @param inputFilePath 输入音频文件路径
     * @param outputDirPath 输出目录路径
     * @throws IOException 如果发生文件读写错误
     */
    public static void splitAudioFile(String inputFilePath, String outputDirPath) throws IOException {
        // 确保输出目录存在
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("无法创建输出目录：" + outputDirPath);
            }
        }

        File inputFile = new File(inputFilePath);
        try (FileInputStream fis = new FileInputStream(inputFile)) {
            // 文件总大小（字节）
            long totalFileSize = inputFile.length();
            // 每段文件大小（字节）
            long segmentSize = calculateSegmentSize(totalFileSize);

            // 缓冲区（2MB）
            byte[] buffer = new byte[2 * 1024 * 1024];
            int bytesRead;
            long bytesWritten = 0;
            int segmentIndex = 0;

            FileOutputStream fos = createSegmentFile(outputDirPath, segmentIndex++);

            while ((bytesRead = fis.read(buffer)) != -1) {
                // 当当前段写入的字节数加上即将写入的字节数超过段大小时，进行切割
                while (bytesWritten + bytesRead > segmentSize) {
                    int bytesToWrite = (int) (segmentSize - bytesWritten);
                    fos.write(buffer, 0, bytesToWrite);
                    // 关闭当前段文件
                    fos.close();

                    // 准备写入下一段
                    fos = createSegmentFile(outputDirPath, segmentIndex++);
                    bytesRead -= bytesToWrite;
                    // 将剩余部分移到缓冲区起始
                    System.arraycopy(buffer, bytesToWrite, buffer, 0, bytesRead);
                    // 新段开始计数
                    bytesWritten = 0;
                }

                // 如果不需要切割，写入剩余数据
                fos.write(buffer, 0, bytesRead);
                bytesWritten += bytesRead;
            }
            // 关闭最后一段文件
            fos.close();
        }
    }

    /**
     * 计算每段的文件大小
     *
     * @param totalFileSize 文件总大小（字节）
     * @return 每段的文件大小（字节）
     */
    private static long calculateSegmentSize(long totalFileSize) {
        // 每段文件大小 = 文件总大小 / 总时长 * 每段时长
        return Math.round((double) totalFileSize / TOTAL_DURATION * SEGMENT_DURATION);
    }

    /**
     * 创建分段音频文件
     *
     * @param outputDirPath 输出目录路径
     * @param segmentIndex  段索引
     * @return 文件输出流
     * @throws IOException 如果无法创建文件
     */
    private static FileOutputStream createSegmentFile(String outputDirPath, int segmentIndex) throws IOException {
        String outputFilePath = outputDirPath + File.separator + segmentIndex + ".mp3";
        File outputFile = new File(outputFilePath);
        if (!outputFile.createNewFile()) {
            throw new IOException("无法创建输出文件：" + outputFilePath);
        }
        return new FileOutputStream(outputFile);
    }
}
