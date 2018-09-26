package com.zw.zwpp.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zw.zwpp.base.BaseResponse;

@RestController
public class FileController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("${filePath}")
	private String filePath;
	@RequestMapping("/uploadFile")
	public BaseResponse  uploadFile(@RequestParam("fileName") MultipartFile myFile) {
		BaseResponse resp = new BaseResponse();
		if(myFile.isEmpty()) {
			resp.setCode(500);
			resp.setMsg("上传文件为空！");
		}
		String fileName = myFile.getOriginalFilename();
		String path = filePath + fileName;
		logger.info("上传至{}",path);
		File fileDir = new File(filePath);
		if(!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File file =new File(path);
		try {
			myFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(500);
			resp.setMsg("文件上传异常");;
			return resp;
		}
		return resp;
		
	}
}
