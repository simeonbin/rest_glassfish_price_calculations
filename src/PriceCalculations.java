import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.time.LocalDateTime;

//import java.util.logging.Level;
//import java.util.logging.LogManager;
//import java.util.logging.Logger;
   //LoggerFactory;

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

  //  private static final Logger logger = LoggerFactory.getLogger(PriceCalculations.class);

    public void parseJsonPriceParameters () {

        // Use JSON.simple to build JSONObject from String
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

        Gson gson = new Gson();
        String jsonPriceResponse = gson.toJson(gson);
        System.out.println (jsonPriceResponse.toString());
        
        // logger.debug("jsonPriceResponse = " + jsonPriceResponse.toString());

        // Return String Response with DateTime stamp and Price of product
        return localDateTime + " " + "Price= " + String.valueOf(valPrice) +
                 " ProductID=" + String.valueOf(productID) +
                 " Value1=" + String.valueOf(value1) +
                 " Volatility=" + String.valueOf(volatility) +

                 " productIDFromJ=" + String.valueOf(productIDFromJ) +
                 " value1FromJ=" + String.valueOf(value1FromJ) +
                 " volatilityFromJ=" + String.valueOf(volatilityFromJ);
    }
}
