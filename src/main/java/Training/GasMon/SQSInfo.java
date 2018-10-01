package Training.GasMon;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;

import com.amazonaws.services.sns.AmazonSNSClientBuilder;

import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;

import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;

public class SQSInfo {

	public static void create() {

		BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAIT7GQTMHV6B3D72A",
				"Zsnt51Pwjokfl71QHxyedUu67gOs7gEyMs9wHX2A");

		AmazonSNS sns = AmazonSNSClientBuilder.standard().withRegion(Regions.EU_WEST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

		AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion(Regions.EU_WEST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

		String myTopicArn = "arn:aws:sns:eu-west-1:552908040772:EventProcessing-RFM-Sept-2018-snsTopicSensorDataPart1-PUR0KBORONQF";
		String myQueueUrl = sqs.createQueue(new CreateQueueRequest("bdavies1")).getQueueUrl();

		Topics.subscribeQueue(sns, sqs, myTopicArn, myQueueUrl);

		try {
			while (true) {

				java.util.List<Message> messages = sqs.receiveMessage(myQueueUrl).getMessages();

				for (Message m : messages) {

					jsonParser(m.getBody());
					// System.out.println(m.getBody().toString());

					sqs.deleteMessage(myQueueUrl, m.getReceiptHandle());

				}
			}
		} finally {
			sqs.deleteQueue(myQueueUrl);
		}

	}

	public static void jsonParser(String message) {

		JSONParser parser = new JSONParser();

		JSONObject o = null;

		try {
			o = (JSONObject) parser.parse(message);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		String new_message = (String) o.get("Message").toString();

		String[] message_info = new_message.substring(1, new_message.length() - 1).split(",");

		String []location_info =message_info[0].split(":");
		String []event_id =message_info[1].split(":");
		String []value =message_info[2].split(":");
		String []timestamp =message_info[3].split(":");
		

		System.out.println(message_info[0] + message_info[1] + message_info[2] + message_info[3]);

	}

}
