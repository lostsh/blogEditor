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
    @FXML
    private static TextField textFieldarticlesIndexFilePath;
    
    private static boolean isOpen;
    
    private static String blogPath;
    private static String templatePath;
    private static String indexArticlesFilePath;
    
    private static Editor parentEditor;
    
    private static final ConfigController confWindow = new ConfigController();
    
    public static ConfigController getControler(){
        return confWindow;
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldBlogPath = new TextField();
        textFieldTemplatePath = new TextField();
        textFieldarticlesIndexFilePath = new TextField();
    }
    
    public void start(String blogPath, String template, Editor editor, String indexArticles){
        ConfigController.parentEditor = editor;
        ConfigController.blogPath = blogPath;
        ConfigController.templatePath = template;
        ConfigController.indexArticlesFilePath = indexArticles;
        System.out.format(
                    "[ == ] Vars dump (ConfigController:Params) =>\n\tBlog Path :[%s]\n\tTemplate Path :[%s]\n\tArticles Index :[%s]\n", 
                    blogPath, 
                    template, 
                    indexArticles
            );
        System.out.format(
                    "[ == ] Vars dump (ConfigController:this) =>\n\tBlog Path :[%s]\n\tTemplate Path :[%s]\n\tArticles Index :[%s]\n", 
                    ConfigController.blogPath, 
                    ConfigController.templatePath, 
                    ConfigController.indexArticlesFilePath
            );
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
        
        System.out.format(
                    "[ == ] Vars dump (Editor:UpdateValues) =>\n\tBlog Path :[%s]\n\tTemplate Path :[%s]\n\tArticles Index :[%s]\n", 
                    ConfigController.blogPath, 
                    ConfigController.templatePath, 
                    ConfigController.indexArticlesFilePath
            );
        
        isOpen = false;
        parentEditor.setBlogAndTemplateAndIndex(
                textFieldBlogPath.textProperty().get(),
                textFieldTemplatePath.textProperty().get(),
                textFieldarticlesIndexFilePath.textProperty().get()
        );
    }
    
    private static void setPathValues(){
        textFieldBlogPath.textProperty().set(blogPath);
        textFieldTemplatePath.setText(templatePath);
        textFieldarticlesIndexFilePath.setText(indexArticlesFilePath);
        System.out.println("[*] Config ========["+blogPath+"] | ["+templatePath+"] | ["+indexArticlesFilePath+"]");
    }
}
