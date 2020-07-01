package itx.examples.java.jackson;

/**
 * Created by gergej on 25.12.2016.
 */
public class ResponseMessage extends Message {

    public static final String TYPE = "ResponseMessage";

    private String data;
    private Long responseId;

    public ResponseMessage() {
        super(TYPE);
    }

    public ResponseMessage(String data, Long responseId) {
        super(TYPE);
        this.data = data;
        this.responseId = responseId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

}
