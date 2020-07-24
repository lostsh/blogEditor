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
    private TextField textFieldBlogPath;
    @FXML
    private TextField textFieldTemplatePath;
    
    private static boolean isOpen;
    
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
    }
    
    public void start(String blogPath, String template, Editor editor){
        ConfigController.parentEditor = editor;
        if(!"".equals(blogPath)) textFieldBlogPath.setText(blogPath);
        if(!"".equals(template)) textFieldTemplatePath.setText(template);
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
}
