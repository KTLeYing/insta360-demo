package com.mzl.insta360demo.util;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import java.io.*;

/**
 * @Description: 切段mp3文件工具类
 * @Author: mzl
 */
public class SplitMp3Util1 {

    /**
     *  每段时长5分钟
     */
    private static final int SEGMENT_DURATION = 5 * 60;

    public static void main(String[] args) {
        // 输入文件路径
        String inputFile = "/Users/mazhenle/Desktop/Work/Insta360/PSP/音频切段/test.mp3";
        // 输出目录
        String outputDir = "/Users/mazhenle/Desktop/Work/Insta360/PSP/音频切段/";

        // 创建输出目录
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            splitMP3(inputFile, outputDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 切割MP3文件
     */
    public static void splitMP3(String inputFile, String outputDir) throws Exception {
        FileInputStream inputStream = new FileInputStream(inputFile);
        Bitstream bitstream = new Bitstream(inputStream);

        Header header;
        int frameCount = 0;
        // 每段所需帧数
        int framesPerSegment = calculateFramesPerSegment(bitstream);
        int segmentIndex = 0;

        FileOutputStream outputStream = null;

        try {
            while ((header = bitstream.readFrame()) != null) {
                if (frameCount % framesPerSegment == 0) {
                    if (outputStream != null) {
                        outputStream.close();
                    }

                    String outputFileName = outputDir + segmentIndex + ".mp3";
                    outputStream = new FileOutputStream(outputFileName);
                    segmentIndex++;
                }

                InputStream frameData = bitstream.getRawID3v2();
                byte[] frameDataByte = inputStreamToByteArray(frameData);
                outputStream.write(frameDataByte);
                frameCount++;

                bitstream.closeFrame();
            }
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            bitstream.close();
            inputStream.close();
        }
    }

    /**
     * 根据MP3文件的帧率和每段时长计算每段所需的帧数
     */
    private static int calculateFramesPerSegment(Bitstream bitstream) throws Exception {
        Header header = bitstream.readFrame();
        if (header == null) {
            throw new Exception("无法读取MP3文件的帧信息！");
        }

        // 每帧时长（秒）
        float frameDuration = header.ms_per_frame() / 1000.0f;
        // 每段所需帧数
        return (int) (SEGMENT_DURATION / frameDuration);
    }

    /**
     * 将InputStream转换为byte[]
     */
    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 缓冲区，大小可以根据需要调整
        byte[] buffer = new byte[1024];
        int bytesRead;

        // 从InputStream中读取数据，并写入到ByteArrayOutputStream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        return byteArrayOutputStream.toByteArray();
    }

}