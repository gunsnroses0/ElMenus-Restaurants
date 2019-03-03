package Commands;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import Model.Restaurant;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class UpdateRestaurant extends ConcreteCommand {

	public void execute() throws NoSuchAlgorithmException {
		HashMap<String, Object> props = parameters;
		this.consume("r4");
		Channel channel = (Channel) props.get("channel");
		JSONParser parser = new JSONParser();
		int id = 0;
		String name = "";
		String hotline = "";
		String delivery_time = "";
		int delivery_fees = 0;
		String delivery_hours = "";
		String description = "";
		try {
			JSONObject body = (JSONObject) parser.parse((String) props.get("body"));
//	        System.out.println("The BODY is: " + body.toString());
			JSONObject params = (JSONObject) parser.parse(body.get("body").toString());
//			System.out.println("The params are: " + body.toString());
			id = Integer.parseInt(params.get("id").toString());
			name = params.get("name").toString();
			hotline = params.get("hotline").toString();
			delivery_time = params.get("delivery_time").toString();
			delivery_fees = Integer.parseInt(params.get("delivery_fees").toString());
			delivery_hours = params.get("delivery_hours").toString();
			description = params.get("description").toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		AMQP.BasicProperties properties = (AMQP.BasicProperties) props.get("properties");
		AMQP.BasicProperties replyProps = (AMQP.BasicProperties) props.get("replyProps");
		Envelope envelope = (Envelope) props.get("envelope");
		String response = Restaurant.Update(id, name, hotline, delivery_time, delivery_fees, delivery_hours,
				description) + "";
		sendMessage("database", properties.getCorrelationId(), response);
	}

	@Override
	public void handleApi(HashMap<String, Object> service_parameters) {
		HashMap<String, Object> props = parameters;
		AMQP.BasicProperties properties = (AMQP.BasicProperties) props.get("properties");
		AMQP.BasicProperties replyProps = (AMQP.BasicProperties) props.get("replyProps");
		String serviceBody = service_parameters.get("body").toString();

		Envelope envelope = (Envelope) props.get("envelope");
		try {
			channel.basicPublish("", properties.getReplyTo(), replyProps, serviceBody.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}