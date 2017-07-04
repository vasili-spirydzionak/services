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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vasili_Spirydzionak on 7/4/2017.
 */


public class patientResourceService implements PatientResource{
    private Map<Integer, Patient> patientDB = new ConcurrentHashMap<Integer, Patient>();
    private AtomicInteger idCounter = new AtomicInteger();


    public Response createPatient(InputStream is) {
        Patient patient = readPatient(is);
        patient.setId(idCounter.incrementAndGet());
        patientDB.put(patient.getId(), patient);
        System.out.println("Created a patient  " + patient.getId());
        return Response.created(URI.create("/patients" + patient.getId())).build();
    }


    public StreamingOutput getPatient(int id) {
        final Patient patient = patientDB.get(id);
        if (patient == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new StreamingOutput() {
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                outputPatient(outputStream, patient);
            }
        };

    }


    public void updatePatient(int id, InputStream is) {
       Patient update = readPatient(is);
       Patient current = patientDB.get(id);
        if(current == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        current.setFullname(update.getFullname());
        current.setAge(update.getAge());
        current.setDiagnosis(update.getDiagnosis());
    }


    public void outputPatient(OutputStream os, Patient patient) throws IOException {
        PrintStream writer = new PrintStream(os);
        writer.println("<patient id=\""+patient.getId()+"\"");
        writer.println("<fullname>"+patient.getFullname()+"</fullname>");
        writer.println("<diagnosis>"+patient.getDiagnosis()+"</diagnosis>");
        writer.println("<age>"+patient.getAge()+"</age>");
        writer.println("</patient>");
    }

    public Patient readPatient(InputStream is) {
            Patient patient = new Patient();
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = docBuilder.parse(is);
            Element root = document.getDocumentElement();

            if(root.getAttribute("id")!=null & !root.getAttribute("id").trim().equals("")) {
                patient.setId(Integer.valueOf(root.getAttribute("id")));
            }

            NodeList nodes = document.getChildNodes();
            for(int i=0; i<nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                if(element.getTagName().equals("fullname")) {
                    patient.setFullname(element.getTextContent());
                }
                if(element.getTagName().equals("diagnosis")) {
                    patient.setDiagnosis(element.getTextContent());
                }
                if(element.getTagName().equals("age")) {
                    patient.setDiagnosis(element.getTextContent());
                }
            }
        } catch (Exception e) {
           throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return patient;
    }

}
