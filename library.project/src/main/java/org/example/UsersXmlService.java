package org.example;

import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UsersXmlService {
    private final Path usersPath = Paths.get("users.xml"); // заменили Path.of на Paths.get

    // Заменили record на обычный класс
    public static class User {
        private final String username;
        private final String password;
        private final String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getRole() { return role; }
    }

    public Optional<User> find(String username) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(usersPath.toFile());
            NodeList list = doc.getElementsByTagName("user");
            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                if (e.getAttribute("username").equals(username)) {
                    return Optional.of(new User(
                            e.getAttribute("username"),
                            e.getAttribute("password"),
                            e.getAttribute("role")
                    ));
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения users.xml: " + e.getMessage(), e);
        }
    }

    public void add(String username, String password, String role) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(usersPath.toFile());
            NodeList list = doc.getElementsByTagName("user");
            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                if (e.getAttribute("username").equals(username))
                    throw new IllegalArgumentException("Пользователь уже существует");
            }
            Element u = doc.createElement("user");
            u.setAttribute("username", username);
            u.setAttribute("password", password);
            u.setAttribute("role", role);
            doc.getDocumentElement().appendChild(u);

            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(doc), new StreamResult(usersPath.toFile()));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка записи users.xml: " + e.getMessage(), e);
        }
    }
}
