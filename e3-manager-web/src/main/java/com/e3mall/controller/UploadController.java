package com.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.e3mall.utils.FastDFSClient;

@Controller
public class UploadController {
	@Value(value="${IMG_SERVER}")
	private String IMG_SERVER;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map<String, Object> uploadImg(MultipartFile uploadFile){
		//返回的结果集
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//上传工具类
			FastDFSClient client =  new FastDFSClient("classpath:conf/client.conf");
			//取扩展名
			String filename = uploadFile.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf(".")+1);
			//上传图片文件
			String filePath = client.uploadFile(uploadFile.getBytes(), extName);
			
			result.put("error", 0);
			result.put("url", IMG_SERVER + filePath);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", 1);
			result.put("message", "error!!!");
		}
		return result;
	}
}
