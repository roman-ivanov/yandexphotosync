package ua.pp.bizon.sunc.remote.impl;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Loader {

    static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    static {
        context.scan("ua.pp.bizon.sunc.api.impl");
        context.refresh();
    }
    
    public static void main(String[] args) throws Exception {
        try {
            ServiceEntry entry = context.getBean(ServiceEntry.class);
            entry.load();
            StreamResult result = new StreamResult();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            result.setOutputStream(outputStream);
            TransformerFactory.newInstance().newTransformer().
            transform(new DOMSource(entry.getXmlNode()), result);
            System.out.println("app:service");
            System.out.println(new String(outputStream.toByteArray()));
            System.out.println("\n\napp:albums");
            System.out.println(IOUtils.toString(entry.getStream(entry.albums)));

            System.out.println("\n\napp:photos");
            System.out.println(IOUtils.toString(entry.getStream(entry.photos)));
        } finally {
            context.close();
        }
        System.out.println("done");
    }

   

}
