package com.pcdack.oscsmall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by pcdack on 17-9-11.
 *
 */
public class FTPUtil {
    private static Logger logger= LoggerFactory.getLogger(FTPUtil.class);
    private static String FTP_IP=PropertiesUtil.getProperty("ftp.server.ip");
    private static String FTP_USERNAME=PropertiesUtil.getProperty("ftp.user");
    private static String FTP_PASSWD=PropertiesUtil.getProperty("ftp.pass");
    private static int FTP_PORT=21;

    public FTPUtil(String ip, int port, String username, String userpasswd) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.userpasswd = userpasswd;
    }
    public static boolean uploadFile(List<File> files) throws IOException {
        FTPUtil ftpUtil=new FTPUtil(FTP_IP,FTP_PORT,FTP_USERNAME,FTP_PASSWD);
        logger.info("开始建立ftp链接");
        boolean result=ftpUtil.uploadFile("img",files);
        logger.info("开始连接ftp服务器,结束上传,上传结果:{}");
        return result;
    }
    private boolean uploadFile(String remotePath,List<File> files) throws IOException {
        boolean isUpload=true;
        FileInputStream fis=null;
        if (connectFTP(this.ip,this.port,this.username,this.userpasswd)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("utf-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File file : files) {
                    fis=new FileInputStream(file);
                    ftpClient.storeFile(file.getName(),fis);
                }
            } catch (IOException e) {
                logger.error("上传文件异常",e);
                isUpload=false;
                e.printStackTrace();
            }finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return isUpload;
    }
    private boolean connectFTP(String ip,int port,String username,String userpasswd){
        boolean isSuccess=false;
        ftpClient=new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess=ftpClient.login(username,userpasswd);
        } catch (IOException e) {
            logger.error("链接FTP服务器失败",e);
        }
        return isSuccess;
    }


    private String ip;
    private int port;
    private String username;
    private String userpasswd;
    private FTPClient ftpClient;

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpasswd() {
        return userpasswd;
    }

    public void setUserpasswd(String userpasswd) {
        this.userpasswd = userpasswd;
    }
//    enum Atype{
//        A(0),
//        B(1),
//        C(2);
//        int i;
//        Atype(int i) {
//            this.i=i;
//        }
//
//        public int getI() {
//            return i;
//        }
//    }
//
//    public static void main(String[] args) {
//        System.out.println(Atype.A.getI());
//    }
}
