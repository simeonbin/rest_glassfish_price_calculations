import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.*;
import java.time.LocalDateTime;

class PriceParamInput {
    private Long productID = (long)2000;
    private Double value1 = 2.2;
    private Double volatility = 5.5;

    public Long getProductID() {
        return productID;
    }

    public Double getValue1() {
        return value1;
    }

    public Double getVolatility() {
        return volatility;
    }

}

class CalculateThePrice {

    public Double calculation(Long productID, Double value1) //, Double volatility )
    {
        // Long productID = (long) jsonObjPrice.get("productID");
           //Long productID = (long) productID;
           //Double value1 = (double) value1;
      //  Double value1 = (double) jsonObjPrice.get("value1");
      //  Double volatility = (double) jsonObjPrice.get("volatility");

        Double d=20.22;

        return d;
    }
}
// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld/{productID}/{value1}/{volatility}")
public class HelloWorld {
    Long a; Double b; Double c;
    PriceParamInput priceParamsInput = new PriceParamInput();
   Long value1 = (long)0;
   JSONObject jsonObjectPriceParams;
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
  //  @Consumes("application/json")
    @Consumes("text/plain")
    @Produces("text/plain")
    public String calculatePrice(@PathParam("productID") Long productID,
                                 @PathParam("value1") Double value1,
                                 @PathParam("volatility") Double volatility) throws ParseException {

        CalculateThePrice dVal = new CalculateThePrice();

        //Use Gson to convert between JSON Strings and Java Objects
        Gson g = new Gson();
        String jsonStringPPI = g.toJson(priceParamsInput);

        // Use JSON.simple to build JSONObject
         JSONParser parser = new JSONParser();
        try {
            jsonObjectPriceParams = (JSONObject) parser.parse(jsonStringPPI);
        } catch (ParseException e) { }

        Long productIDFromJ = (Long)jsonObjectPriceParams.get("productID");
        Double value1FromJ = (Double)jsonObjectPriceParams.get("value1");
        Double volatilityFromJ = (Double)jsonObjectPriceParams.get("volatility");

      //  productID =   jsonObjPriceParams;
        Double valAdd = dVal.calculation(productID, value1);
        LocalDateTime localDateTime = LocalDateTime.now();

        // Return String Response with DateTime stamp and Price of product
        return localDateTime + " " + "Price= " + String.valueOf(valAdd) +
                 " " + String.valueOf(productID) +
                 " " + String.valueOf(value1) +
                 " " + String.valueOf(volatility) +

                 " " + String.valueOf(productIDFromJ) +
                 " " + String.valueOf(value1FromJ) +
                 " " + String.valueOf(volatilityFromJ);
    }
}
