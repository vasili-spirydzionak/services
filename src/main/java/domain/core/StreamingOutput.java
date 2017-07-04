package domain.core;

import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Vasili_Spirydzionak on 7/4/2017.
 */
public interface StreamingOutput {
    void write(OutputStream os) throws IOException, WebApplicationException;
}
