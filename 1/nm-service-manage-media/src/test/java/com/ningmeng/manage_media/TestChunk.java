package com.ningmeng.manage_media;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * Created by 周周 on 2020/3/2.
 */
public class TestChunk {

    //测试文件分块方法
    @Test
    public void testChunk() throws IOException{
        //原文件地址
        File sourseFile = new File("D:\\teach\\ffmpeg\\haicaowu.mp4");
        //分开文件地址
        String chunkPath = "D:\\teach\\ffmpeg\\chunk\\";
        File chunkFile = new File(chunkPath);
        if(!chunkFile.exists()){
            //不存在创建目录
            chunkFile.mkdir();
        }
        //设置分块文件的大小 以kb为大小
        long chunkSize = 1024*1024*1;
        //分块的数量
        long chunkNum= (long) Math.ceil(sourseFile.length()*1.0/chunkSize);
        if(chunkNum<=0){
            chunkNum=1;
        }
        //读取sourseFile文件，循环写入快文件
        RandomAccessFile raf_read=new RandomAccessFile(sourseFile,"r");
        //定义缓冲区
        byte[] b=new byte[1024];
        for(int i=0;i<chunkNum;i++){
            //raf_read读出来 之后 写入文件
            //指定一个文件名
            File file = new File(chunkPath+i);
            boolean newFile=file.createNewFile();
            if(newFile){
                RandomAccessFile raf_write= (RandomAccessFile) new RandomAccessFile(file,"rw");
                 int len = -1;
                 while ((len = raf_read.read(b))!=-1){
                     raf_write.write(b,0,len);
                     if(file.length()>chunkSize){
                         break;
                     }
                 }
                 raf_write.close();
            }
        }
        raf_read.close();
    }
    //测试文件合并方法
    @Test
    public void testMerge() throws IOException {
        //块文件目录
        File chunkFolder = new File("D:\\teach\\ffmpeg\\chunk\\");
        //合并文件
        File mergeFile = new File("D:\\teach\\ffmpeg\\haicaowu1.mp4");
        if(mergeFile.exists()){
            mergeFile.delete();
        }
        //创建新的合并文件
        mergeFile.createNewFile();
        //用于写文件
        RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
        //指针指向文件顶端
        raf_write.seek(0);
        //缓冲区
        byte[] b = new byte[1024];
        //分块列表
        File[] fileArray = chunkFolder.listFiles();
        // 转成集合，便于排序
        List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));
        // 从小到大排序
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                    return -1;
                }
                    return 1;
            }
        });
        //合并文件
        for(File chunkFile:fileList){
            RandomAccessFile raf_read = new RandomAccessFile(chunkFile,"rw");
            int len = -1;
            while((len=raf_read.read(b))!=-1){
                raf_write.write(b,0,len);
            }
            raf_read.close();
        }
        raf_write.close(); }
}
