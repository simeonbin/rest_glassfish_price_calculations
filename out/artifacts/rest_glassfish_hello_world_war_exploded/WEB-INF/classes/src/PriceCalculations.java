import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.*;
import java.time.LocalDateTime;



// This Java class will be hosted at the URI path "/helloworld"
@Path("/pricecalculations/{productID}/{value1}/{volatility}")
public class PriceCalculations {

    PriceParamRequest priceParamsInput = new PriceParamRequest();
    Long value1 = (long)0;
    JSONObject jsonObjectPriceParams;
    String jsonStringPPI;
    Long productIDFromJ;
    Double value1FromJ;
    Double volatilityFromJ;

    public void parseJsonPriceParameters () {

        // Use JSON.simple to build JSONObject
        JSONParser parser = new JSONParser();
        try {
            jsonObjectPriceParams = (JSONObject) parser.parse(jsonStringPPI);
        } catch (ParseException e) { }

        productIDFromJ = (Long)jsonObjectPriceParams.get("productID");
        value1FromJ = (Double)jsonObjectPriceParams.get("value1");
        volatilityFromJ = (Double)jsonObjectPriceParams.get("volatility");
    }

    // This Java method will process HTTP GET requests
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
        jsonStringPPI = g.toJson(priceParamsInput);

        parseJsonPriceParameters();

        Double valPrice = dVal.calculation(productID, value1, volatility);
        LocalDateTime localDateTime = LocalDateTime.now();

        PriceResponse priceResponse = new PriceResponse();

        priceResponse.setLdt(localDateTime);
        priceResponse.setPriceCalc(valPrice);

        // Return String Response with DateTime stamp and Price of product
        return localDateTime + " " + "Price= " + String.valueOf(valPrice) +
                 " " + String.valueOf(productID) +
                 " " + String.valueOf(value1) +
                 " " + String.valueOf(volatility) +

                 " " + String.valueOf(productIDFromJ) +
                 " " + String.valueOf(value1FromJ) +
                 " " + String.valueOf(volatilityFromJ);
    }
}
