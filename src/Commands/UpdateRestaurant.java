package Commands;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import Model.Restaurant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class UpdateRestaurant extends ConcreteCommand {

	public void execute() throws NoSuchAlgorithmException {
		HashMap<String, Object> props = parameters;
		this.consume("r4");
		Channel channel = (Channel) props.get("channel");
		JSONParser parser = new JSONParser();
//		int id = 0;
		String username = "";
		String name = "";
		String hotline = "";
		String delivery_time = "";
		int delivery_fees = 0;
		int id = 0;
		String delivery_hours = "";
		String description = "";
		try {
			// Body
			JSONObject body = (JSONObject) parser.parse((String) props.get("body"));
			System.out.println("Body " + body);

			// Get JWT Token
			JSONObject headers = (JSONObject) parser.parse(body.get("headers").toString());
			String jwt = headers.get("jwt").toString();
			HashMap<String, String> credentials = ParseJWT(jwt);
			username = credentials.get("username");

			String strId = ((String) body.get("uri")).split("/")[((String) body.get("uri")).split("/").length - 1];
			id = Integer.parseInt(strId);

			// FORM
			JSONObject form = (JSONObject) body.get("form");
			name = form.get("name").toString();
			hotline = form.get("hotline").toString();
			delivery_time = form.get("delivery_time").toString();
			delivery_fees = Integer.parseInt(form.get("delivery_fees").toString());
			delivery_hours = form.get("delivery_hours").toString();
			description = form.get("description").toString();
		} catch (ParseException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
				| SignatureException | IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		AMQP.BasicProperties properties = (AMQP.BasicProperties) props.get("properties");
		AMQP.BasicProperties replyProps = (AMQP.BasicProperties) props.get("replyProps");
		Envelope envelope = (Envelope) props.get("envelope");
		String response = Restaurant.Update(id, username, name, hotline, delivery_time, delivery_fees, delivery_hours,
				description);
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