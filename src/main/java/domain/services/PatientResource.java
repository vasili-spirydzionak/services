package domain.services;

import domain.dto.Patient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;

/**
 * Created by Vasili_Spirydzionak on 7/4/2017.
 */
public interface PatientResource {
    @POST
    @Consumes("application/xml")
    public Response createPatient(InputStream is);

    @GET
    @Path("{id}")
    @Produces("application/xml")
    public StreamingOutput getPatient(@PathParam("id") int id);

    @PUT
    @Path("{id}")
    @Consumes("application/xml")
    public void updatePatient(@PathParam("id") int id, InputStream is);


    public void outputPatient(OutputStream os, Patient patient) throws IOException;

    public Patient readPatient(InputStream is);
}
