package Training.GasMon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class AWSLocations {
	
	static String bucket_name = "eventprocessing-rfm-sept-2018-locationss3bucket-186b0uzd6cf01";
	static String key_name = "AKIAIT7GQTMHV6B3D72A";

	public void getLocations(){
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIT7GQTMHV6B3D72A", "Zsnt51Pwjokfl71QHxyedUu67gOs7gEyMs9wHX2A");
    	AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1)
    	                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
    	                        .build();
    	

    	try {
    	    S3Object o = s3Client.getObject(bucket_name, "locations.json");
    	    S3ObjectInputStream s3is = o.getObjectContent();
    	    FileOutputStream fos = new FileOutputStream(new File("locations.json"));
    	    byte[] read_buf = new byte[1024];
    	    int read_len = 0;
    	    while ((read_len = s3is.read(read_buf)) > 0) {
    	        fos.write(read_buf, 0, read_len);
    	    }
    	    s3is.close();
    	    fos.close();
    	} catch (AmazonServiceException e) {
    	    System.err.println(e.getErrorMessage());
    	    System.exit(1);
    	} catch (FileNotFoundException e) {
    	    System.err.println(e.getMessage());
    	    System.exit(1);
    	} catch (IOException e) {
    	    System.err.println(e.getMessage());
    	    System.exit(1);
    	}
	}
	
}
