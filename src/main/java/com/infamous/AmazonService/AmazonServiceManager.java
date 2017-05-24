package com.infamous.AmazonService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.infamous.Model.InformationFile;

public class AmazonServiceManager {
	private static final String KEYID = "AKIAJQA5CNC5PUVUB44A";
	private static final String ACCESSID = "HKFckr0B7T78bG0k283Sxs1k8Nk5DnsVJ58/5RW6";
	private static final String REGION = "us-east-2";
	private static final String BUCKET = "nhom9sangthu7";
	public static final String DOWNLOAD_LINK = "https://s3." + REGION + ".amazonaws.com/" + BUCKET + "/";

	protected static AmazonServiceManager instance;
	private AmazonS3 service;

	private AmazonServiceManager() {
		service = buildService();
	}

	public static AmazonServiceManager getService() {
		if (instance == null) {
			return new AmazonServiceManager();
		} else {
			return instance;
		}
	}

	private AmazonS3 buildService() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(KEYID, ACCESSID);
		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(REGION).build();
	}

	public String uploadFile(String fileName, InputStream inputStream) {
		String flag = "";
		try {
			java.io.File file2Upload = new java.io.File(fileName);

			OutputStream outStream = new FileOutputStream(file2Upload);

			byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			outStream.close();

			service.putObject(new PutObjectRequest(BUCKET, fileName, file2Upload)
					.withCannedAcl(CannedAccessControlList.PublicRead));

			file2Upload.delete();
			flag = fileName;
		} catch (Exception e) {
			flag = "";
		}
		return flag;
	}

	private String getDownloadLink(String fileName) {
		return DOWNLOAD_LINK + fileName;
	}

	public List<InformationFile> getAllFile() {
		List<InformationFile> list = new ArrayList<>();
		final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(BUCKET).withMaxKeys(2);
		ListObjectsV2Result result;
		do {
			result = service.listObjectsV2(req);

			for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {

				InformationFile item = new InformationFile();
				item.setId(objectSummary.getKey());
				item.setTitle(objectSummary.getKey());
				item.setLinkDownload(getDownloadLink(objectSummary.getKey()));

				list.add(item);

			}

			req.setContinuationToken(result.getNextContinuationToken());
		} while (result.isTruncated() == true);
		return list;
	}

}
