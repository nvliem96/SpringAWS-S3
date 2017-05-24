package com.infamous.AmazonService;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.infamous.Model.InformationFile;
@Service
public class AmazonService {

	AmazonServiceManager serviceManager;

	public AmazonService() {
		serviceManager = AmazonServiceManager.getService();
	}

	public String uploadFile(String fileName, InputStream inputStream) {
		return serviceManager.uploadFile(fileName, inputStream);
	
	}

	public List<InformationFile> getAllFile() {
		return serviceManager.getAllFile();
	}
}
