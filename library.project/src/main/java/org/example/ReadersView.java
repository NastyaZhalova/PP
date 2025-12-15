package org.example;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ReadersView {
    public static List<String> listReaders() {
        try {
            Path usersPath = Paths.get("users.xml");
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(usersPath.toFile());

            NodeList list = doc.getElementsByTagName("user");
            List<String> out = new ArrayList<>();
            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                if ("READER".equals(e.getAttribute("role"))) {
                    out.add(e.getAttribute("username"));
                }
            }
            return out;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
