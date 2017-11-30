package com.pcdack.oscsmall.service.Impl;

import com.google.common.collect.Lists;
import com.pcdack.oscsmall.service.IFileService;
import com.pcdack.oscsmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by pcdack on 17-9-11.
 *
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String upload(MultipartFile file, String path) {
        String fileName=file.getOriginalFilename();
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadName= UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("上传文件名{}，文件扩展名{}，新文件名{}",fileName,fileExtensionName,uploadName);
        File fileDir=new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile=new File(path,uploadName);
        if (!transfer(file,targetFile)) {
            return null;
        }
        return targetFile.getName();
    }

    @Override
    public String uploadUserImage(MultipartFile file, String path) {
        String fileName=file.getOriginalFilename();
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadName= "user_avatar"+UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("上传文件名{}，文件扩展名{}，新文件名{}",fileName,fileExtensionName,uploadName);
        File fileDir=new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile=new File(path,uploadName);
        if (!transfer(file,targetFile)) {
            return null;
        }
        return targetFile.getName();
    }

    private boolean transfer(MultipartFile file, File targetFile) {
        try {
            file.transferTo(targetFile);
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
            return true;
        } catch (IOException e) {
            logger.error("文件上传异常",e);
            return false;
        }
    }
}
