package org.example;

import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.*;
import javax.xml.xpath.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class LibraryXmlService {
    private final Path xmlPath = Paths.get("library.xml");
    private final Path xsdPath = Paths.get("library.xsd");

    private DocumentBuilder newBuilderWithSchema() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(xsdPath.toFile());
        factory.setSchema(schema);
        return factory.newDocumentBuilder();
    }

    private Document load() {
        try {
            if (!xmlPath.toFile().exists()) {
                DocumentBuilder b = newBuilderWithSchema();
                Document doc = b.newDocument();
                Element root = doc.createElement("library");
                doc.appendChild(root);
                save(doc);
            }
            return newBuilderWithSchema().parse(xmlPath.toFile());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки library.xml: " + e.getMessage(), e);
        }
    }

    private void save(Document doc) {
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            t.transform(new DOMSource(doc), new StreamResult(xmlPath.toFile()));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сохранения library.xml: " + e.getMessage(), e);
        }
    }

    public List<Map<String, String>> listAll() {
        Document doc = load();
        NodeList nl = doc.getElementsByTagName("book");
        List<Map<String, String>> out = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            Element b = (Element) nl.item(i);
            Map<String, String> row = new LinkedHashMap<>();
            row.put("id", b.getAttribute("id"));
            row.put("copies", b.getAttribute("copies"));
            row.put("available", b.getAttribute("available"));
            row.put("title", text(b, "title"));
            row.put("author", text(b, "author"));
            row.put("year", text(b, "year"));
            row.put("price", text(b, "price"));
            row.put("category", text(b, "category"));
            out.add(row);
        }
        return out;
    }

    private String text(Element e, String tag) {
        Node node = e.getElementsByTagName(tag).item(0);
        return node != null ? node.getTextContent() : "";
    }

    public void addBook(int id, String title, String author, int year,
                        BigDecimal price, String category, int copies, int available) {
        Document doc = load();
        XPath xp = XPathFactory.newInstance().newXPath();
        try {
            Object result = xp.evaluate("/library/book[@id='" + id + "']", doc, XPathConstants.NODE);
            if (result != null) {
                throw new IllegalArgumentException("Книга с таким ID уже существует");
            }
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        Element book = doc.createElement("book");
        book.setAttribute("id", String.valueOf(id));
        book.setAttribute("copies", String.valueOf(copies));
        book.setAttribute("available", String.valueOf(available));
        append(doc, book, "title", title);
        append(doc, book, "author", author);
        append(doc, book, "year", String.valueOf(year));
        append(doc, book, "price", price.toPlainString());
        append(doc, book, "category", category);

        doc.getDocumentElement().appendChild(book);
        save(doc);
    }

    public void changePrice(int id, BigDecimal newPrice) {
        Document doc = load();
        Element b = findById(doc, id);
        if (b == null) throw new IllegalArgumentException("Книга не найдена");
        b.getElementsByTagName("price").item(0).setTextContent(newPrice.toPlainString());
        save(doc);
    }

    public void issue(int id) {
        Document doc = load();
        Element b = findById(doc, id);
        if (b == null) throw new IllegalArgumentException("Книга не найдена");
        int avail = Integer.parseInt(b.getAttribute("available"));
        if (avail <= 0) throw new IllegalStateException("Нет доступных экземпляров");
        b.setAttribute("available", String.valueOf(avail - 1));
        save(doc);
    }

    // ✅ Метод поиска по полю
    public List<Map<String, String>> searchEquals(String tag, String value) {
        Document doc = load();
        List<Map<String, String>> out = new ArrayList<>();
        NodeList nl = doc.getElementsByTagName("book");
        for (int i = 0; i < nl.getLength(); i++) {
            Element b = (Element) nl.item(i);
            String val = text(b, tag);
            if (val.equalsIgnoreCase(value)) {
                Map<String, String> row = new LinkedHashMap<>();
                row.put("id", b.getAttribute("id"));
                row.put("title", text(b, "title"));
                row.put("author", text(b, "author"));
                row.put("year", text(b, "year"));
                row.put("price", text(b, "price"));
                row.put("category", text(b, "category"));
                row.put("copies", b.getAttribute("copies"));
                row.put("available", b.getAttribute("available"));
                out.add(row);
            }
        }
        return out;
    }

    private Element findById(Document doc, int id) {
        try {
            Object result = XPathFactory.newInstance()
                    .newXPath()
                    .evaluate("/library/book[@id='" + id + "']", doc, XPathConstants.NODE);

            if (result instanceof Element) {
                return (Element) result;
            }
            return null;
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    private void append(Document doc, Element parent, String tag, String text) {
        Element el = doc.createElement(tag);
        el.setTextContent(text);
        parent.appendChild(el);
    }
}
