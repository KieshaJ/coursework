package desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kj.coursework.util.enums.UserType;
import com.kj.coursework.util.model.user.UserInfo;
import com.kj.coursework.util.response.CategoryResponse;
import com.kj.coursework.util.response.UserResponse;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginForm {
//    private Scene scene;
    private UserResponse user;
    private List<CategoryResponse> categoryList;

    public LoginForm() {
    }

//    public Scene getScene() {
//        return scene;
//    }

//    public void setScene(Scene scene) {
//        this.scene = scene;
//    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public List<CategoryResponse> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryResponse> categoryList) {
        this.categoryList = categoryList;
    }

    public Scene setupScene(Stage primaryStage, Scene nextScene, UserResponse user) {
        Scene scene;
        VBox vBox = new VBox();
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        Button loginButton = new Button("LOGIN");

        loginButton.setOnAction(e -> {
            Map<String, String> loginRequestMap = new HashMap<>();
            loginRequestMap.put("username", username.getText());
            loginRequestMap.put("password", password.getText());

            login(loginRequestMap, user);

            if(user != null) {
                primaryStage.setScene(nextScene);
                primaryStage.setTitle("Categories");
            }
        });

        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().addAll(
                new Label("Username"),
                username,
                new Label("Password"),
                password,
                loginButton
        );

        scene = new Scene(vBox, 800, 600);

        return scene;
    }

    private static UserResponse mapToUserEntity(Map<String, Object> responseMap) {
        UserResponse userResponse;
        Map<String, String> userInfoMap = (Map) responseMap.get("userInfo");

        UserInfo userInfo = new UserInfo(
                userInfoMap.get("name"),
                userInfoMap.get("surname"),
                userInfoMap.get("phoneNo"),
                userInfoMap.get("email"),
                userInfoMap.get("address")
        );

        UserType userType = UserType.valueOf(responseMap.get("userType").toString());

        userResponse = new UserResponse(
                responseMap.get("id").toString(),
                responseMap.get("companyId").toString(),
                responseMap.get("categoryId") == null ? null : responseMap.get("categoryId").toString(),
                userType,
                responseMap.get("username").toString(),
                userInfo
        );

        return userResponse;
    }

    private static void login(Map<String, String> loginRequestMap, UserResponse user) {
        String targetUrl = "http://localhost:8080/users/login";
        String payload = new JSONObject(loginRequestMap).toString();
        StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(targetUrl);
        request.setEntity(entity);

        HttpResponse response;
        Map<String, Object> responseMap;
        try {
            response = client.execute(request);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String content;
            StringBuilder contentBuilder = new StringBuilder();

            while((content = br.readLine()) != null) {
                contentBuilder.append(content);
            }

            JSONObject result = new JSONObject(contentBuilder.toString());
            ObjectMapper mapper = new ObjectMapper();
            responseMap = mapper.readValue(result.toString(), Map.class);

            user = mapToUserEntity(responseMap);
        }
        catch(IOException | JSONException ioException) {
            ioException.printStackTrace();
        }
    }
}
