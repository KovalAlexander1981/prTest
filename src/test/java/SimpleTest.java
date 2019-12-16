import org.testng.annotations.Test;
import io.restassured.response.Response;


public class SimpleTest extends ApiQuery{

    @Test(description = "Get all products")
    public void getProductsDetails() {
        Response response = getAllProductsQuery(baseURI);
        checkStatusCode(response, 200);
    }

    @Test(description = "Create product")
    public void createProduct() {
        checkStatusCode(createProductQuery(baseURI, "id", "4", "name", "John").andReturn(), 201);
        checkProductIsPresent("4", "John");
    }

    @Test(description = "Delete product")
    public void deleteProduct() {
        checkStatusCode(deleteProductQuery(baseURI +"/2").andReturn(), 200);
        checkProductIsNotPresent("2");
    }

    @Test(description = "Update product")
    public void updateProduct() {
        checkStatusCode(putQuery(baseURI +"/3", "name", "Gordon").andReturn(), 200);
        checkProductIsPresent("3", "Gordon");

    }
}

