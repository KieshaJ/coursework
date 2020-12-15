package desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kj.coursework.util.enums.UserType;
import com.kj.coursework.util.model.user.UserInfo;
import com.kj.coursework.util.response.CategoryResponse;
import com.kj.coursework.util.response.CompanyResponse;
import com.kj.coursework.util.response.UserResponse;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Desktop extends Application {
    private StackPane root = new StackPane();
    private Stage stage;

    private Scene scene1;
    private Scene scene2;

    private UserResponse user;
    private List<CategoryResponse> categoryList;
    private List<UserResponse> userList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {

    }

    @Override
    public void start(Stage primaryStage) {
        scene1 = setupScene1(primaryStage);
        primaryStage.setScene(scene1);
        primaryStage.show();
        primaryStage.setTitle("Login");
        primaryStage.setAlwaysOnTop(true);
    }

    private Scene setupScene1(Stage primaryStage) {
        Scene scene;
        VBox vBox = new VBox();
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        Button loginButton = new Button("LOGIN");

        loginButton.setOnAction(e -> {
            Map<String, String> loginRequestMap = new HashMap<>();
            loginRequestMap.put("username", username.getText());
            loginRequestMap.put("password", password.getText());

            login(loginRequestMap);

            if(user != null) {
                this.categoryList = getCategoryList(user);

                primaryStage.setScene(setupScene2(primaryStage));
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

    private Scene setupScene2(Stage primaryStage) {
        Scene scene;
        VBox vbox = new VBox();
        TextField testField = new TextField();

        TreeItem<String> rootItem = new TreeItem<>("Categories");
        rootItem.setExpanded(true);

        if(this.categoryList == null) {
            testField.setText("test");
        }

        rootItem.getChildren().add(new TreeItem<>("one"));
        rootItem.getChildren().add(new TreeItem<>("twe"));
        rootItem.getChildren().add(new TreeItem<>("three"));

        TreeView<String> treeView = new TreeView<>(rootItem);
        vbox.getChildren().add(treeView);
        vbox.getChildren().add(testField);
        scene = new Scene(vbox, 800, 600);

        primaryStage.setScene(scene);

        return scene;
    }

    private UserResponse mapToUserEntity(Map<String, Object> responseMap) {
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

    private void login(Map<String, String> loginRequestMap) {
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

            this.user = mapToUserEntity(responseMap);
        }
        catch(IOException | JSONException ioException) {
            ioException.printStackTrace();
        }
    }

    private List<CategoryResponse> mapToCategoryList(Map<String, Object> responseMap) {
        List<CategoryResponse> categories = new ArrayList<>();

        if(responseMap.get("categoryList") instanceof List) {
            categories = (List<CategoryResponse>) responseMap.get("categoryList");

            return categories;
        }

        return categories;
    }

    private List<CategoryResponse> getCategoryList(UserResponse user) {
        // get user from userId first
        if(this.user.getUserType() == UserType.COMPANY) {
            CompanyResponse company;

            String targetUrl = "http://localhost:8080/companies/" + user.getCompanyId();

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(targetUrl);

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

                this.categoryList = mapToCategoryList(responseMap);
            }
            catch(IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        // check user type

        // if user type is company

        //      get company categories by companyId and
        //      get all company users by companyId

        // else

        //      get category by
        return null;
    }
}
