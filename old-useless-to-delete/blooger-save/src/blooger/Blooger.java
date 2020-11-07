package blooger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author lost
 */
public class Blooger extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Blog Editor");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        /*
        String blogPath = "/home/lost/Documents/gitHub/lostsh.github.io/assets/blog";
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Article articel = new Article(blogPath, dateFormat.format(new Date()), "blog editor creation");
        articel.create();
        articel.setArticle("Hello, this is my first auto-genered bolog page !\nSo intresting !");
        
        articel.exportArticle("/home/lost/Documents/gitHub/lostsh.github.io/assets/blog/template.html");
        
        System.exit(0);*/
    }
    
}
