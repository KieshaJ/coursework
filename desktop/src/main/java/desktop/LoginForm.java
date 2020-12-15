package desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LoginForm {
    private static final String EMPTY_STRING = "";

    private JPanel panel;

    private JLabel usernameLabel;
    private JTextField username;

    private JLabel passwordLabel;
    private JPasswordField password;

    private JLabel infoLabel;
    private JButton loginButton;

    public JPanel getPanel() {
        panel = new JPanel(new GridBagLayout());

        usernameLabel = new JLabel("Username", JLabel.LEFT);
        username = new JTextField(EMPTY_STRING, 45);

        panel.add(usernameLabel, null);
        panel.add(username, null);

        passwordLabel = new JLabel("Password", JLabel.LEFT);
        password = new JPasswordField(EMPTY_STRING, 32);

        panel.add(passwordLabel, null);
        panel.add(password, null);

        infoLabel = new JLabel(EMPTY_STRING, JLabel.CENTER);

        GridBagConstraints spacer = new GridBagConstraints();
        spacer.fill = GridBagConstraints.BOTH;
        spacer.gridwidth = GridBagConstraints.REMAINDER;
        spacer.weighty = 1.0;

        panel.add(infoLabel, spacer);

        loginButton = new JButton("Login");
        panel.add(loginButton, null);

        loginButton.addActionListener(getAddActionListener());

        panel.setBorder(new TitledBorder("Login"));

        return panel;
    }

    private ActionListener getAddActionListener() {
        return e -> {
            String username = this.username.getText();
            String password = String.valueOf(this.password.getPassword());

            String targetUrl = "http://localhost:8080/users/login";

            Map<String, String> values = new HashMap<>() {{
                put("username", username);
                put("password", password);
            }};

            String payload = new JSONObject(values).toString();

            StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(targetUrl);
            request.setEntity(entity);

            HttpResponse response = null;
            try {
                response = client.execute(request);
            }
            catch(IOException ioException) {
                ioException.printStackTrace();
            }

            System.out.println(response.getStatusLine().getStatusCode());
            Map<String, Object> map = null;
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String content = "";

                StringBuilder contentBuilder = new StringBuilder();

                while((content = br.readLine()) != null) {
                    contentBuilder.append(content);
                }

                JSONObject result = new JSONObject(contentBuilder.toString());
                System.out.println(result.toString());

                ObjectMapper mapper = new ObjectMapper();
                map = mapper.readValue(result.toString(), Map.class);

            }
            catch(IOException | JSONException ioException) {
                ioException.printStackTrace();
            }

            System.out.println("tet");
        };
    }
}
