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
	
	public static String Update(int id, String name, String hotline, String delivery_time, int delivery_fees,
			String delivery_hours, String description) throws NoSuchAlgorithmException {
        String callStatement = "{ ? = call Update_Restaurant_By_Id( ?,?,?,?,?,?,? ) }";
        JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject inputId = new JSONObject();
		JSONObject inputName = new JSONObject();
		JSONObject inputHotline = new JSONObject();
		JSONObject inputDeliveryTime = new JSONObject();
		JSONObject inputDeliveryFees = new JSONObject();
		JSONObject inputDeliveryHours = new JSONObject();
		JSONObject inputDescription = new JSONObject();
		inputId.put("type", Types.INTEGER);
		inputId.put("value", id);
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
		jsonArray.add(inputId);
		jsonArray.add(inputName);
		jsonArray.add(inputHotline);
		jsonArray.add(inputDeliveryTime);
		jsonArray.add(inputDeliveryFees);
		jsonArray.add(inputDeliveryHours);
		jsonArray.add(inputDescription);
		json.put("out_type", Types.INTEGER);
		json.put("call_statement", callStatement);
		json.put("input_array", jsonArray);
        return json.toString();
    }

	public static String DeleteById(int id) {
		String callStatement = "{? = call Delete_Restaurant( ? ) }";
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject inputID = new JSONObject();
		inputID.put("type", Types.INTEGER);
		inputID.put("value", id);
		jsonArray.add(inputID);
		json.put("out_type", Types.INTEGER);
		json.put("call_statement", callStatement);
		json.put("input_array", jsonArray);
		System.out.println(json.toString());
		return json.toString();
	}
}