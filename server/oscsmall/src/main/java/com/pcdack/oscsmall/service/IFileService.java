package com.pcdack.oscsmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by pcdack on 17-9-11.
 *
 */
public interface IFileService {

   String upload(MultipartFile file, String path);
   String uploadUserImage(MultipartFile file, String path);
}
