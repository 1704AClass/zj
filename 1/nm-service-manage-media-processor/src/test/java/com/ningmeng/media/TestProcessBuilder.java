package com.ningmeng.media;

import com.ningmeng.framework.utils.HlsVideoUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周周 on 2020/3/5.
 */
public class TestProcessBuilder {

    @Test
    public void testProcessBuilder(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        // processBuilder.command("ping","127.0.0.1");
        processBuilder.command("ipconfig");
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            // /启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            //读取进程输入流中的内容
            while ((len= inputStreamReader.read(c))!=-1) {
                String s = new String(c,0,len);
                outputString.append(s);
                System.out.print(s);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFFmpeg(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        //定义命令内容
        List<String> command = new ArrayList<>();
        command.add("F:\\stu\\软件\\idea\\ffmpeg\\ffmpeg-20180227-fa0c9d6-win64-static\\bin\\ffmpeg.exe");
        command.add("-i");
        command.add("D:\\teach\\ffmpeg\\haicaowu.avi");
        command.add("-y");
        //覆盖输出文件
        command.add("-c:v");
        command.add("libx264");
        command.add("-s");
        command.add("1280x720");
        command.add("-pix_fmt");
        command.add("yuv420p");
        command.add("-b:a");
        command.add("63k");
        command.add("-b:v");
        command.add("753k");
        command.add("-r");
        command.add("18");
        command.add("D:\\teach\\ffmpeg\\haicaowu1111.mp4");
        processBuilder.command(command);
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            //读取进程输入流中的内容
            while ((len= inputStreamReader.read(c))!=-1) {
                String s = new String(c,0,len);
                outputString.append(s); System.out.print(s);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   @Test
    public void testFfmpeg1(){
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil("F:\\stu\\软件\\idea\\ffmpeg\\ffmpeg-20180227-fa0c9d6-win64-static\\bin\\ffmpeg.exe",
               "D:\\teach\\ffmpeg\\haicaowu1111.mp4","haicaowu.m3u8","D:\\teach\\ffmpeg\\hls");
         String success = hlsVideoUtil.generateM3u8();
    }
}
