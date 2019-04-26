package Commands;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Commands.Command;
import Model.Restaurant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

//import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

public class RetrieveRestaurant extends ConcreteCommand {

	public void execute() throws NoSuchAlgorithmException {
		this.consume("r5");
		HashMap<String, Object> props = parameters;
		Channel channel = (Channel) props.get("channel");
		JSONParser parser = new JSONParser();
		int id = 0;
//		String username = "";
		try {
			JSONObject body = (JSONObject) parser.parse((String) props.get("body"));

			// Get jwt token
//			JSONObject headers = (JSONObject) parser.parse(body.get("headers").toString());
//			System.out.println(headers.toString());
//			String jwt = headers.get("jwt").toString();
//			System.out.println(jwt);
//			HashMap<String, String> credentials = ParseJWT(jwt);

//			username = credentials.get("username");
			String strId = ((String) body.get("uri")).split("/")[((String) body.get("uri")).split("/").length - 1];
			id = Integer.parseInt(strId);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		AMQP.BasicProperties properties = (AMQP.BasicProperties) props.get("properties");
		AMQP.BasicProperties replyProps = (AMQP.BasicProperties) props.get("replyProps");
		Envelope envelope = (Envelope) props.get("envelope");
		String response = Restaurant.getById(id);
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
