package Commands;

import Commands.Command;

import Model.Restaurant;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import java.io.IOException;
//import java.math.BigDecimal;
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;

public class UploadImage extends ConcreteCommand {
	public void execute() throws NoSuchAlgorithmException {
		this.consume("r2");
		HashMap<String, Object> props = parameters;
		Channel channel = (Channel) props.get("channel");
		JSONParser parser = new JSONParser();
		int id = 0;
		String image = "";
		String type = "";
		try {
			// Body
			JSONObject body = (JSONObject) parser.parse((String) props.get("body"));
			System.out.println("Body " + body);

			// FORM
			JSONObject form = (JSONObject) body.get("form");
			System.out.println(form.toJSONString());
			System.out.println(((String) body.get("uri")).split("/")[((String) body.get("uri")).split("/").length - 1]);

			String strId = ((String) body.get("uri")).split("/")[((String) body.get("uri")).split("/").length - 1];
			id =  Integer.parseInt(strId);
			image = form.get("media").toString();
			System.out.println(form.get("media"));
			type = form.get("type").toString();
			System.out.println(type);
		} catch (ParseException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
				| SignatureException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		AMQP.BasicProperties properties = (AMQP.BasicProperties) props.get("properties");
		AMQP.BasicProperties replyProps = (AMQP.BasicProperties) props.get("replyProps");
		Envelope envelope = (Envelope) props.get("envelope");
		String response = Restaurant.Upload(id, image, type);
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