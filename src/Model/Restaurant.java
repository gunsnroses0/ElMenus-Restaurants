package Model;

import java.security.NoSuchAlgorithmException;

//import com.arangodb.ArangoCollection;
//import com.arangodb.ArangoCursor;
//import com.arangodb.ArangoDBException;
//import com.arangodb.entity.BaseDocument;
//import com.arangodb.entity.DocumentCreateEntity;
//import com.arangodb.util.MapBuilder;
//import lib.ArangoClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import sun.tools.jstat.Jstat;

//import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;
import java.util.Properties;

public class Restaurant {
	
	public static String getById(int id) {
		String callStatement = "{? = call Get_Restaurant_By_Id( ? ) }";
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject inputObject = new JSONObject();
		inputObject.put("type", Types.INTEGER);
		inputObject.put("value", id);
		jsonArray.add(inputObject);
		json.put("call_statement", callStatement);
		json.put("out_type", Types.OTHER);
		json.put("input_array", jsonArray);

		return json.toString();
	}

	public static String Create(String name, String hotline, String delivery_time, int delivery_fees,
			String delivery_hours, String description) throws NoSuchAlgorithmException {
//	        byte[] salt = SHAHashing.getSalt();
//	        password = SHAHashing.get_SHA_256_SecurePassword(password, salt);
		String callStatement = "{ call Add_Restaurant( ?, ?, ?, ?, ?, ?) }";
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject inputName = new JSONObject();
		JSONObject inputHotline = new JSONObject();
		JSONObject inputDeliveryTime = new JSONObject();
		JSONObject inputDeliveryFees = new JSONObject();
		JSONObject inputDeliveryHours = new JSONObject();
		JSONObject inputDescription = new JSONObject();
		inputName.put("type", Types.VARCHAR);
		inputName.put("value", name);
		inputHotline.put("type", Types.VARCHAR);
		inputHotline.put("value", hotline);
		inputDeliveryTime.put("type", Types.VARCHAR);
		inputDeliveryTime.put("value", delivery_time);
		inputDeliveryFees.put("type", Types.INTEGER);
		inputDeliveryFees.put("value", delivery_fees);
		inputDeliveryHours.put("type", Types.VARCHAR);
		inputDeliveryHours.put("value", delivery_hours);
		inputDescription.put("type", Types.VARCHAR);
		inputDescription.put("value", description);
		jsonArray.add(inputName);
		jsonArray.add(inputHotline);
		jsonArray.add(inputDeliveryTime);
		jsonArray.add(inputDeliveryFees);
		jsonArray.add(inputDeliveryHours);
		jsonArray.add(inputDescription);
		json.put("out_type", 0);
		json.put("call_statement", callStatement);
		json.put("input_array", jsonArray);
		return json.toString();
	}
//
//    public static HashMap<String, Object>  update(String id, HashMap<String, Object> atrributes) {
//        BaseDocument updatedMessage = getCollection().getDocument(id, BaseDocument.class);
//
//        for (String key : atrributes.keySet()) {
//            updatedMessage.addAttribute(key, atrributes.get(key));
//        }
//
//        getCollection().updateDocument(id, updatedMessage);
//
//        atrributes.put("id", id);
//
//        return atrributes;
//    }
//
//    public static boolean delete(String id) {
//        try {
//            getCollection().deleteDocument(id);
//        } catch (ArangoDBException e) {
//            if (e.getErrorNum() == 1202) {
//                System.out.println("document not found");
//            }
//        }
//
//        return true;
//    }
//
//
//    public static ArrayList<HashMap<String, Object>> getAll(String senderId, String receiverId) {
//        String query = "FOR message IN messages FILTER ((message.receiver_id == @sender_id AND message.sender_id == @receiver_id) " +
//                "OR (message.receiver_id == @receiver_id AND message.sender_id == @sender_id)) RETURN message";
//
//         MapBuilder vars = new MapBuilder();
//         vars.put("sender_id", Integer.parseInt(senderId));
//         vars.put("receiver_id", Integer.parseInt(receiverId));
//         Map<String, Object> bindVars = vars.get();
//        ArangoCursor<BaseDocument> cursor = getCollection().db().query(query, bindVars, null,
//                BaseDocument.class);
//
//        ArrayList<HashMap<String, Object>> messages = new ArrayList<HashMap<String, Object>>();
//        cursor.forEachRemaining((BaseDocument aDocument) -> {
//            Map<String, Object> properties = aDocument.getProperties();
//            HashMap<String, Object> message = new HashMap<String, Object>();
//            for (String key :  properties.keySet()) {
//                message.put(key, aDocument.getAttribute(key));
//            }
//            message.put("id", aDocument.getKey());
//            messages.add(message);
//        });
//        System.out.println(messages);
//
//        return messages;
//    }
//
//    public static HashMap<String, Object> get(String messageId) {
//        BaseDocument aDocument = getCollection().getDocument(messageId, BaseDocument.class);
//        Map<String, Object> properties = aDocument.getProperties();
//        HashMap<String, Object> message = new HashMap<String, Object>();
//        for (String key :  properties.keySet()) {
//            message.put(key, aDocument.getAttribute(key));
//        }
//        message.put("id", aDocument.getKey());
//
//        return message;
//    }
//
//    private static ArangoCollection getCollection() {
//        if (collection == null) {
//            collection = ArangoClient.createOrRetrieveCollection(COLLECTION_NAME);
//        }
//        return collection;
//    }
}