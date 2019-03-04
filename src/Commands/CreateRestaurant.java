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

public class CreateRestaurant extends ConcreteCommand {
	public void execute() throws NoSuchAlgorithmException {
		this.consume("r3");
		HashMap<String, Object> props = parameters;
		Channel channel = (Channel) props.get("channel");
		JSONParser parser = new JSONParser();
		String username = "";
		String name = "";
		String hotline = "";
		String delivery_time = "";
		int delivery_fees = 0;
		String delivery_hours = "";
		String description = "";
		try {
			JSONObject body = (JSONObject) parser.parse((String) props.get("body"));
			System.out.println("Body " + body);

			// Get jwt token
			JSONObject headers = (JSONObject) parser.parse(body.get("headers").toString());
			String jwt = headers.get("jwt").toString();
			HashMap<String, String> credentials = ParseJWT(jwt);

			username = credentials.get("username");

			JSONObject params = (JSONObject) parser.parse(body.get("body").toString());
			name = params.get("name").toString();
			hotline = params.get("hotline").toString();
			delivery_time = params.get("delivery_time").toString();
			delivery_fees = Integer.parseInt(params.get("delivery_fees").toString());
			delivery_hours = params.get("delivery_hours").toString();
			description = params.get("description").toString();
		} catch (ParseException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
				| SignatureException | IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		AMQP.BasicProperties properties = (AMQP.BasicProperties) props.get("properties");
		AMQP.BasicProperties replyProps = (AMQP.BasicProperties) props.get("replyProps");
		Envelope envelope = (Envelope) props.get("envelope");
		String response = Restaurant.Create(username, name, hotline, delivery_time, delivery_fees, delivery_hours,
				description);
//		System.out.print("Response ");
//		System.out.println(response);
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

	private HashMap<String, String> ParseJWT(String jwt) throws ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
		HashMap<String, String> credentials = new HashMap<String, String>();
		String secret = "secret";
		Jws<Claims> claims = Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(jwt);
		credentials.put("username", (String) claims.getBody().get("username"));
		credentials.put("user_type", (String) claims.getBody().get("user_type"));
		return credentials;
	}

}