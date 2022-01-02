package ext.yongc.plm.report;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class JsonParser {
    private static final Logger LOGGER = Logger.getLogger(JsonParser.class.getName());

    /**
     * Parse the object to a json string
     * 
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        SerializationConfig cfg = mapper.getSerializationConfig();
        cfg = cfg.withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.setSerializationConfig(cfg);
        try {
            String json = mapper.writeValueAsString(obj);
            return json;
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Parse the json string to the object of specific class
     * 
     * @param content
     * @param valueType
     * @return
     */
    public static <T> T fromJson(String content, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T object = null;
        try {
            object = mapper.readValue(content, valueType);
        } catch (IOException e) {
            LOGGER.error("Parse Error", e);
        }
        return object;
    }
}
