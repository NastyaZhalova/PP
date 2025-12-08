package org.example;

import org.w3c.dom.*;
import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

public class LibraryApp {
    private Document doc;
    private File xmlFile = new File("library.xml");

    public LibraryApp() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("library.xsd"));
            factory.setSchema(schema);

            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlFile);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ошибка загрузки XML: " + e.getMessage());
            System.exit(1);
        }
    }

    public void showBooks() {
        NodeList books = doc.getElementsByTagName("book");
        String[] columns = {"ID","Title","Author","Year","Price","Category","Copies","Available"};
        Vector<String[]> data = new Vector<>();

        for (int i = 0; i < books.getLength(); i++) {
            Element b = (Element) books.item(i);
            String[] row = {
                    b.getAttribute("id"),
                    b.getElementsByTagName("title").item(0).getTextContent(),
                    b.getElementsByTagName("author").item(0).getTextContent(),
                    b.getElementsByTagName("year").item(0).getTextContent(),
                    b.getElementsByTagName("price").item(0).getTextContent(),
                    b.getElementsByTagName("category").item(0).getTextContent(),
                    b.getAttribute("copies"),
                    b.getAttribute("available")
            };
            data.add(row);
        }

        JTable table = new JTable(data.toArray(new Object[][]{}), columns);
        JOptionPane.showMessageDialog(null, new JScrollPane(table), "Все книги", JOptionPane.PLAIN_MESSAGE);
    }

    public void addBook() {
        String id = JOptionPane.showInputDialog("ID:");
        String title = JOptionPane.showInputDialog("Title:");
        String author = JOptionPane.showInputDialog("Author:");
        String year = JOptionPane.showInputDialog("Year:");
        String price = JOptionPane.showInputDialog("Price:");
        String category = JOptionPane.showInputDialog("Category:");
        String copies = JOptionPane.showInputDialog("Copies:");
        String available = JOptionPane.showInputDialog("Available:");

        Element book = doc.createElement("book");
        book.setAttribute("id", id);
        book.setAttribute("copies", copies);
        book.setAttribute("available", available);

        Element t = doc.createElement("title");
        t.setTextContent(title);
        book.appendChild(t);
        Element a = doc.createElement("author");
        a.setTextContent(author);
        book.appendChild(a);
        Element y = doc.createElement("year");
        y.setTextContent(year);
        book.appendChild(y);
        Element p = doc.createElement("price");
        p.setTextContent(price);
        book.appendChild(p);
        Element c = doc.createElement("category");
        c.setTextContent(category);
        book.appendChild(c);

        doc.getDocumentElement().appendChild(book);
        save();
    }

    public void searchBy(String tag, String value) {
        NodeList books = doc.getElementsByTagName("book");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < books.getLength(); i++) {
            Element b = (Element) books.item(i);
            String val = b.getElementsByTagName(tag).item(0).getTextContent();
            if (val.equalsIgnoreCase(value)) {
                sb.append("ID ").append(b.getAttribute("id")).append(": ")
                        .append(b.getElementsByTagName("title").item(0).getTextContent()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.length()>0?sb.toString():"Не найдено");
    }

    public void changePrice(String id, String newPrice) {
        NodeList books = doc.getElementsByTagName("book");
        for (int i = 0; i < books.getLength(); i++) {
            Element b = (Element) books.item(i);
            if (b.getAttribute("id").equals(id)) {
                b.getElementsByTagName("price").item(0).setTextContent(newPrice);
                save();
                return;
            }
        }
    }

    public void issueBook(String id) {
        NodeList books = doc.getElementsByTagName("book");
        for (int i = 0; i < books.getLength(); i++) {
            Element b = (Element) books.item(i);
            if (b.getAttribute("id").equals(id)) {
                int avail = Integer.parseInt(b.getAttribute("available"));
                if (avail > 0) {
                    b.setAttribute("available", String.valueOf(avail-1));
                    save();
                    JOptionPane.showMessageDialog(null,"Книга выдана");
                } else {
                    JOptionPane.showMessageDialog(null,"Нет доступных экземпляров");
                }
                return;
            }
        }
    }

    private void save() {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(xmlFile)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Ошибка сохранения: "+e.getMessage());
        }
    }

    public static void main(String[] args) {
        LibraryApp app = new LibraryApp();
        while (true) {
            String[] options = {"Показать все","Добавить","Поиск по автору","Поиск по году","Поиск по категории","Изменить цену","Выдать книгу","Выход"};
            int choice = JOptionPane.showOptionDialog(null,"Меню","Library",0,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            if (choice == 0) app.showBooks();
            else if (choice == 1) app.addBook();
            else if (choice == 2) {
                String a=JOptionPane.showInputDialog("Автор:");
                app.searchBy("author",a);
            }
            else if (choice == 3) {
                String y=JOptionPane.showInputDialog("Год:");
                app.searchBy("year",y);
            }
            else if (choice == 4) {
                String c=JOptionPane.showInputDialog("Категория:");
                app.searchBy("category",c);
            }
            else if (choice == 5) {
                String id=JOptionPane.showInputDialog("ID книги:");
                String np=JOptionPane.showInputDialog("Новая цена:");
                app.changePrice(id,np);
            }
            else if (choice == 6) {
                String id=JOptionPane.showInputDialog("ID книги:");
                app.issueBook(id);
            }
            else break;
        }
    }
}
