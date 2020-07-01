package itx.examples.java.jackson;

/**
 * Created by gergej on 25.12.2016.
 */
public class RequestMessage extends Message {

    public static final String TYPE = "RequestMessage";

    private String data;

    public RequestMessage() {
        super(TYPE);
    }

    public RequestMessage(String data) {
        super(TYPE);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
