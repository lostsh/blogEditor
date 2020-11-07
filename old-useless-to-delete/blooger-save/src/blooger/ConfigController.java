package blooger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class.
 *
 * @author lost
 */
public class ConfigController implements Initializable {
    
    @FXML
    private Button buttonOK;
    @FXML
    private static TextField textFieldBlogPath;
    @FXML
    private static TextField textFieldTemplatePath;
    
    private static boolean isOpen;
    
    private String blogPath;
    private String templatePath;
    
    private static Editor parentEditor;
    
    private static ConfigController confWindow = new ConfigController();
    
    public static ConfigController getControler(){
        return confWindow;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldBlogPath = new TextField();
        textFieldTemplatePath = new TextField();
    }
    
    public void start(String blogPath, String template, Editor editor){
        ConfigController.parentEditor = editor;
        this.blogPath = blogPath;
        this.templatePath = template;
        show();
    }
    public boolean displayed(){ return isOpen; }
    
    private void show() {
        if (!isOpen) {
            Stage stage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("config.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException ex) {
                System.err.println("[!] Config Windows undoadable");
            } catch (RuntimeException e){
                System.err.println("[!] Strange Exception about Config Windows");
            }
            stage.show();
            setPathValues();
            isOpen = true;
        }
    }
    
    @FXML
    private void buttonOkAction(ActionEvent event) {
        Stage stage = (Stage)buttonOK.getScene().getWindow();
        stage.close();
        
        isOpen = false;
        parentEditor.setBlogAndTemplate(
                textFieldBlogPath.textProperty().get(),
                textFieldTemplatePath.textProperty().get()
        );
    }
    
    private void setPathValues(){
        textFieldBlogPath.textProperty().set(blogPath);
        textFieldTemplatePath.setText(templatePath);
        System.out.println("========"+blogPath+" | "+templatePath);
    }
}
