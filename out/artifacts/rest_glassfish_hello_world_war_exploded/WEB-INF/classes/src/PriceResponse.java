import java.time.LocalDateTime;

public class PriceResponse {

    LocalDateTime ldt;
    Double priceCalc;

    public LocalDateTime getLdt() {
        return ldt;
    }

    public Double getPriceCalc() {
        return priceCalc;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public void setPriceCalc(Double priceCalc) {
        this.priceCalc = priceCalc;
    }


}
