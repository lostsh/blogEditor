package blooger;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author lostsh
 */
public class MainViewController implements Initializable {

    @FXML
    private ListView<Article> listViewArticles;
    @FXML
    private Button buttonPublish;
    @FXML
    private TextField textFieldTitle;
    @FXML
    private TextField textFieldDate;
    @FXML
    private TextArea textArea;
    
    private BooleanProperty requiredFieldsFillProprety = new SimpleBooleanProperty(false);
    
    private Editor editor;
    @FXML
    private Button buttonNew;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editor = new Editor();
        editor.start();
        
        requiredFieldsFillProprety.bind(textFieldTitle.textProperty().isEmpty().or(textFieldDate.textProperty().isEmpty()));
        buttonPublish.disableProperty().bind(requiredFieldsFillProprety);
        Platform.runLater(() -> {
            //importation des elements dans la liste
            editor.loadArticles();
            loadArticles();
        });
    }
    
    public void loadArticles(){
        listViewArticles.getItems().clear();
        editor.articles().forEach(article -> listViewArticles.getItems().add(article));
    }
    
    @FXML
    private void buttonPublishAction(ActionEvent event) {
        editor.publish();
    }

    @FXML
    private void buttonConfigAction(ActionEvent event) throws IOException {
        editor.configDilog();
    }

    @FXML
    private void buttonNewAction(ActionEvent event) {
        editor.clearCurrentArticle();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateuh = dateFormat.format(new Date());
        textFieldDate.setText(dateuh);
        textFieldTitle.clear();
        textArea.clear();
        
        //editor.newArticle(dateuh, title);
    }

    @FXML
    private void buttonSaveAction(ActionEvent event) {
        if(editor.article()==null){
            String dt = textFieldDate.textProperty().get();
            String title = textFieldTitle.textProperty().get();
            editor.newArticle(dt, title);
            loadArticles();
        }
        editor.article().setArticle(textArea.textProperty().get());
        editor.save();
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        Article selectedArticle = listViewArticles.getSelectionModel().getSelectedItem();
        editor.selectedArticle(selectedArticle);
        textArea.textProperty().set(editor.article().getArticle());
        textFieldDate.textProperty().set(editor.article().getDate());
        textFieldTitle.textProperty().set(editor.article().getTitle());
    }
}
