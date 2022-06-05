package com.example.itenaryplanner.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Clock;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

//@Component
public class AmazonClient {

	private String endpointUrl;

    private String bucketName;

    private String accessKey;

    private String secretKey;

    private AmazonS3 s3client;

    public AmazonClient(@Value("${ENDPOINT_URI}") String endpointUrl,
            @Value("${BUCKET_NAME}") String bucketName,
            @Value("${ACCESS_KEY}") String accessKey,
            @Value("${SECRET_KEY}") String secretKey) {
    	this.endpointUrl = endpointUrl;
    	this.bucketName = bucketName;
    	this.accessKey = accessKey;
    	this.secretKey = secretKey;
    }

//    @PostConstruct
    private void initializeAmazon() {
    	AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    	this.s3client = AmazonS3ClientBuilder.standard()
    			.withForceGlobalBucketAccessEnabled(true)
    			.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }

    /**
     * Upload image to S3
     * 
     * @param multipartFile
     * @param isProfile
     * @return uploadUrl
     * @throws IOException
     */
    public String uploadImage(MultipartFile multipartFile, boolean isProfile) throws IOException {
    	File file = convertMultiPartToFile(multipartFile);

    	final String fileName = multipartFile.getOriginalFilename() + "_" + Clock.systemDefaultZone().millis();
    	final String key = isProfile ? "profiles" : "itenary";
    	String uploadUrl = getS3UploadPath(fileName, key);
    	
    	uploadImageToS3(file, String.join("/", key, fileName));
    	file.delete();
    	
    	return uploadUrl;
    }

	private void uploadImageToS3(File file, String key) {
		s3client.putObject(new PutObjectRequest(bucketName, key, file)
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}

	private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
		File file = new File(multipartFile.getOriginalFilename());
		OutputStream os = new FileOutputStream(file);
		os.write(multipartFile.getBytes());
		os.close();

		return file;
	}
	
	private String getS3UploadPath(final String name, String key) {
		return String.join("/", endpointUrl, bucketName, key, name);
	}
}
