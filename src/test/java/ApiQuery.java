import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;


public class ApiQuery {

    public static final String baseURI = "http://localhost:8080/products";
    RequestSpecification httpRequest = RestAssured.given();


    public void checkStatusCode(Response response, int statusCode) {
        System.out.println(response.getStatusCode() + "  " + response.asString());
        Assert.assertEquals(response.getStatusCode(), statusCode, "Request was not successful");
    }

    public Response getAllProductsQuery(String path){
        return httpRequest.request(Method.GET, path);
    }

    public Response deleteProductQuery(String path){
        return httpRequest.delete(path);
    }

    public Response putQuery(String path, String key, String value){
        JSONObject requestParams = new JSONObject();
        requestParams.put(key, value);
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParams.toJSONString());
        return httpRequest.put(path);
    }

    public Response createProductQuery(String path, String key1, String value1, String key2, String value2) {
        JSONObject requestParams = new JSONObject();
        requestParams.put(key1, value1);
        requestParams.put(key2, value2);
        httpRequest.body(requestParams.toJSONString());
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParams.toJSONString());
        return httpRequest.post(path);
    }

    public void checkProductIsPresent(String idValue, String nameValue) {
        JsonPath jsonPathEvaluator = getAllProductsQuery(baseURI).jsonPath();
        ArrayList<String> keys = jsonPathEvaluator.get("id");
        ArrayList <String> values = jsonPathEvaluator.get("name");
        for (int i = 0; i<keys.size(); i++) {
            if (keys.get(i).equals(idValue)) {
                Assert.assertEquals(values.get(i), (nameValue), "Name is different");
            }
        }
    }

    public void checkProductIsNotPresent(String key) {
        JsonPath jsonPathEvaluator = getAllProductsQuery(baseURI).jsonPath();
        ArrayList<String> keys = jsonPathEvaluator.get("id");
        Assert.assertFalse(keys.contains(key), "Product is present in the list");
        }
    }



