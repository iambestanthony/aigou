package com.mmall.service;

import com.mmall.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by JayJ on 2018/6/15.
 **/
public interface IFileService {

    String upload(MultipartFile file , String path);

}
