package org.example;

import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

@Service
public class ReaderAccountService {
    private final Path path = Paths.get("accounts.xml"); // заменили Path.of на Paths.get

    private Document load() {
        try {
            if (!path.toFile().exists()) {
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                doc.appendChild(doc.createElement("accounts"));
                save(doc);
            }
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path.toFile());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки accounts.xml: " + e.getMessage(), e);
        }
    }

    private void save(Document doc) {
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(doc), new StreamResult(path.toFile()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addIssue(String username, int bookId) {
        Document doc = load();
        Element issue = doc.createElement("issue");
        issue.setAttribute("username", username);
        issue.setAttribute("bookId", String.valueOf(bookId));
        issue.setAttribute("ts", Instant.now().toString());
        doc.getDocumentElement().appendChild(issue);
        save(doc);
    }

    public List<Map<String,String>> listIssued(String username) {
        Document doc = load();
        NodeList list = doc.getElementsByTagName("issue");
        List<Map<String,String>> out = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            if (e.getAttribute("username").equals(username)) {
                Map<String,String> row = new LinkedHashMap<>();
                row.put("bookId", e.getAttribute("bookId"));
                row.put("ts", e.getAttribute("ts"));
                out.add(row);
            }
        }
        return out;
    }
}
