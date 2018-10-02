package Training.GasMon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;


public class SQSInfo {

	public static void create() {

		BasicAWSCredentials credentials = new BasicAWSCredentials(");

		AmazonSNS sns = AmazonSNSClientBuilder.standard().withRegion(Regions.EU_WEST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

		AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion(Regions.EU_WEST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

		String myTopicArn = "arn:aws:sns:eu-west-1:552908040772:EventProcessing-RFM-Sept-2018-snsTopicSensorDataPart1-PUR0KBORONQF";
		String myQueueUrl = sqs.createQueue(new CreateQueueRequest("bdavies")).getQueueUrl();

		Topics.subscribeQueue(sns, sqs, myTopicArn, myQueueUrl);
		HashMap<String, Sensor> hmap = new HashMap<>();
		
		Timer t = new Timer();
		try {
			while (true) {
				
				t.schedule(new ExportCSV(hmap), 1000*60);

				java.util.List<Message> messages = sqs.receiveMessage(myQueueUrl).getMessages();

				for (Message m : messages) {

					jsonParser(m.getBody(), hmap);
					// System.out.println(m.getBody().toString());

					sqs.deleteMessage(myQueueUrl, m.getReceiptHandle());

				}
			}
		} finally {
			sqs.deleteQueue(myQueueUrl);
		}

	}


	public static void jsonParser(String message, HashMap<String, Sensor> hmap) {

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

		updateHmap(message_info, hmap);

		System.out.println(message_info[0] + message_info[1] + message_info[2] + message_info[3]);

	}

	private static void updateHmap(String[] message_info, HashMap<String, Sensor> hmap) {
		String[] location_info = message_info[0].split(":");
		// String[] event_id = message_info[1].split(":").toString();
		String[] value = message_info[2].split(":");
		String[] timestamp = message_info[3].split(":");

		if (hmap.containsKey(location_info[1])) {
			hmap.get(location_info[1]).setValue(Double.valueOf(value[1]));
		} else {

			hmap.put(location_info.toString(), new Sensor(location_info[1], value[1], timestamp[1]));
		}
	}

}
