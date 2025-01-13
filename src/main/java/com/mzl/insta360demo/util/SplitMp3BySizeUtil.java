package com.mzl.insta360demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description: 切段MP3文件工具类【按每段文件大小】
 * @Author: mzl
 */
public class SplitMp3BySizeUtil {

    /**
     * 每段文件大小, 单位：字节【5MB】
     */
    private static final long SEGMENT_SIZE = 5 * 1024 * 1024;

    public static void main(String[] args) {
        // 输入文件路径
        String inputFilePath = "/Users/mazhenle/Desktop/Work/Insta360/PSP/音频切段测试/origin.mp3";
        // 输出目录
        String outputDirPath = "/Users/mazhenle/Desktop/Work/Insta360/PSP/音频切段测试/";

        try {
            splitAudioFile(inputFilePath, outputDirPath);
            System.out.println("音频切割完成！");
        } catch (IOException e) {
            System.err.println("音频切割失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 按文件大小切割音频文件
     *
     * @param inputFilePath 输入音频文件路径
     * @param outputDirPath 输出目录路径
     * @throws IOException 如果发生文件读写错误
     */
    public static void splitAudioFile(String inputFilePath, String outputDirPath) throws IOException {
        // 确保输出目录存在
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new IOException("无法创建输出目录：" + outputDirPath);
        }

        File inputFile = new File(inputFilePath);
        try (FileInputStream fis = new FileInputStream(inputFile)) {
            // 缓冲区（2MB）
            byte[] buffer = new byte[2 * 1024 * 1024];
            int bytesRead;
            long bytesWritten = 0;
            int segmentIndex = 0;

            // 创建第一个分段文件
            FileOutputStream fos = createSegmentFile(outputDirPath, segmentIndex++);

            while ((bytesRead = fis.read(buffer)) != -1) {
                // 当前段写入的字节数加上即将写入的字节数超过段大小时，切割文件
                while (bytesWritten + bytesRead > SEGMENT_SIZE) {
                    int bytesToWrite = (int) (SEGMENT_SIZE - bytesWritten);
                    fos.write(buffer, 0, bytesToWrite);
                    fos.close();

                    // 创建新的分段文件
                    fos = createSegmentFile(outputDirPath, segmentIndex++);
                    bytesRead -= bytesToWrite;
                    System.arraycopy(buffer, bytesToWrite, buffer, 0, bytesRead);
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
