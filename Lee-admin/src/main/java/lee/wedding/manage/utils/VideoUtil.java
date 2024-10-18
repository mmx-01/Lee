package lee.wedding.manage.utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: VideoUtil
 * @Description: 类描述
 * @author: sunjiuxiang
 * @date: 2024/4/26 18:44
 */
public class VideoUtil {

    public static final String IDX = "idx";                 //图片序号
    public static final String PHOTO_PATH = "photoPath";    //图片本地路径
    public static final String TIME_NODE = "timeNode";      //图片所在时间点


    //读取文件夹的内容并执行1️⃣创建文件夹2️⃣抽帧
    public static String readFolderFiles(String address) {
        //存放读取到的路径
        ArrayList<String> strings = new ArrayList<>();
        // 文件夹路径
        String folderPath = address;
        // 创建File对象
        File folder = new File(folderPath);
        // 检查文件夹是否存在
        if (folder.exists() && folder.isDirectory()) {
            int num;
            String strCreate = "";
            String strFrame = "";
            // 获取文件夹中的所有文件名
            File[] files = folder.listFiles();
            // 将子文件夹的名称添加到ArrayList中并过滤掉当前文件夹名
            for (File file : files) {
                if (!file.getName().equals(".DS_Store")) {
                    strings.add(file.getName());
                }
            }
            num = strings.size();
            // 输出文件名数组
            for (String fileName : strings) {
                String oldfileName = address + "/" + fileName;
                String str = fileName.substring(0, fileName.indexOf('.'));
                String newFileName = address + "/" + str;
                strCreate = createFolder(newFileName);
                strFrame = frameExtractor(oldfileName);
            }
            return String.format(strCreate + "\n已对%d个视频进行抽帧\n" + strFrame + "至对应文件夹", num);
        } else {
            return "指定的文件夹不存在或不是一个文件夹";
        }
    }

    //1️⃣根据视频名称创建对应文件夹
    private static String createFolder(String fileName) {
        String str;
        // 文件夹名
        String folderName = fileName;
        // 创建一个File对象，表示文件夹路径
        File folder = new File(folderName);
        // 使用mkdir()方法创建文件夹
        if (!folder.exists()) {
            if (folder.mkdir()) {
                str = "文件夹创建成功！";
            } else {
                str = "文件夹创建失败！";
            }
        } else {
            str = "文件夹已存在！";
        }
        return str;
    }

    //2️⃣将视频转为帧图片
    public static String frameExtractor(String address) {
        String str;
        // 视频文件路径
        String videoFilePath = address;
        // 创建FFmpegFrameGrabber对象
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath);
        try {
            // 打开视频文件
            grabber.start();
            // 逐帧抓取视频
            Frame frame;
            int frameNumber = 1;
            //保存路径
            String createaddress = address.substring(0, address.indexOf("."));
            while ((frame = grabber.grabImage()) != null) {
                BufferedImage bufferedImage = frameToBufferedImage(frame);
                // 生成保存帧图像的文件路径
                String outputFilePath = String.format("%s/frame_%05d.jpg", createaddress, frameNumber);
                // 保存帧图像到文件
                try {
                    ImageIO.write(bufferedImage, "jpg", new File(outputFilePath));
                } catch (IOException e) {
                    e.printStackTrace();
                    str = "保存失败";
                }
                frameNumber++;
            }
            // 关闭视频文件
            grabber.stop();
            str = "保存成功";
        } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
            e.printStackTrace();
            str = "保存失败";
        }
        return str;
    }

    //将Frame转换为BufferedImage
    private static BufferedImage frameToBufferedImage(Frame frame) {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        return converter.getBufferedImage(frame);
    }


    /**
     * 传入视频抽帧 得到对应图片信息数组
     *
     * @param address 视频全路径
     * @return
     */
    public static List<String> getPhotoByVideo(String address) {

        List<String> resultList = Collections.emptyList();
        String newFilePath = address.substring(0, address.indexOf('.'));
        createFolder(newFilePath);
        resultList = frameExtractorByTime(address);

        return resultList;

    }

    //将视频-抽帧得到图片地址
    public static List<String> frameExtractorByTime(String address) {

        List<String> resultList = new ArrayList<>();
        // 视频文件路径
        String videoFilePath = address;
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath);
        try {
            grabber.start();
            double frameRate = grabber.getFrameRate();//1秒-30
            int totalFrames = grabber.getLengthInFrames();
            int temRate = totalFrames / 2;
            Frame frame;
            int frameNumber = 1; //计数
            int currentFrame = 0;//帧计数
            int lastTotalFrames = totalFrames - 1;
            String createaddress = address.substring(0, address.indexOf("."));
            while ((frame = grabber.grabImage()) != null) {
                currentFrame++;
                if (currentFrame == temRate || currentFrame == 1 || currentFrame == lastTotalFrames) {
                    BufferedImage bufferedImage = frameToBufferedImage(frame);
                    String outputFilePath = String.format("%s/frame_%05d.jpg", createaddress, frameNumber);
                    try {
                        ImageIO.write(bufferedImage, "jpg", new File(outputFilePath));
                        //结果返回
                        resultList.add(outputFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    frameNumber++;
                }
            }
            grabber.stop();
        } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    // 将时间戳格式化为00:00:00的结构
    private static String formatTimestamp(double seconds) {
        int hours = (int) (seconds / 3600);
        int minutes = (int) ((seconds % 3600) / 60);
        int secs = (int) (seconds % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}
