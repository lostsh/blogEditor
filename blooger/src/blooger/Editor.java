package blooger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author lostsh
 */
public class Editor {
    private static final String confFile = "config.conf";
    
    private String blogPath;
    
    private String templatePath;
        
    private ArrayList<Article> articles;
    
    private Article currentArticle;
    
    public Editor(){
        articles = new ArrayList<>();
        blogPath = "";
        templatePath = "";
    }
    
    public void start(){
        loadConfig();
    }
    
    public Article article(){ return currentArticle; }

    private void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(confFile));
            String line;
            while((line=br.readLine())!=null){
                if(line.contains("BlogPath="))
                    blogPath=line.substring(line.indexOf("BlogPath=")+9,line.length());
                if(line.contains("TemplatePath="))
                    templatePath=line.substring(line.indexOf("TemplatePath=")+13,line.length());
            }
            br.close();
        } catch (FileNotFoundException ex) {
            configDilog();
        } catch (IOException ex) {
            System.err.println("[!] Config file reading exception");
        }
        if("".equals(blogPath)||"".equals(templatePath)){
            configDilog();
        }
    }
    
    public void setBlogAndTemplate(String blogPath, String templatePath){
        this.blogPath = blogPath;
        this.templatePath = templatePath;
        savePaths(blogPath, templatePath);
    }
    
    private void savePaths(String blogPath, String templatePath) {
        if (!"".equals(blogPath) && !"".equals(templatePath)) {
            File configFile = new File(confFile);
            try {
                configFile.createNewFile();
                BufferedWriter wr = new BufferedWriter(new FileWriter(configFile));
                wr.write(String.format("BlogPath=%s", blogPath));
                wr.newLine();
                wr.write(String.format("TemplatePath=%s", templatePath));
                wr.close();
            } catch (IOException ex) {
                System.err.println("[!] Impossible de d'ecrire dans le fichier de configuration");
            }
        }
    }
    
    public void configDilog(){
        ConfigController confWindow = ConfigController.getControler();
        confWindow.start(blogPath, templatePath, this);
        if(!confWindow.displayed()){
            loadConfig();
        }
    }
    
    public void save(){
        currentArticle.exportArticle(templatePath);
        
        //on prend l'article courant
        //si un article est charge
        //et on le sauvegarde dans son fichier
        
        /*
        String blogPath = textFieldBlogPath.textProperty().get();
        
        Article articel = new Article(blogPath, textFieldDate.textProperty().get(), textFieldTitle.textProperty().get());
        articel.create();
        articel.setArticle(textArea.textProperty().get());
        
        articel.exportArticle(blogPath.concat("/template.html"));*/
    }
    public void publish(){
        //on utilise la commande git pour publiser
    }
    public void newArticle(String date, String title){
        currentArticle = new Article(blogPath, date, title);
        currentArticle.create();
        
        articles.add(currentArticle);
    }
    
    public void clearCurrentArticle(){
        currentArticle = null;
    }
    
    public void loadArticles(){
        for(File dayFolder : new File(blogPath).listFiles()){
            if(dayFolder.isDirectory() && dayFolder.listFiles().length > 0){
                for(File blogPost : dayFolder.listFiles()){
                    Article article = new Article(blogPath, dayFolder.getName(), blogPost.getName().replace(".html", "")); 
                    article.importContent(templatePath);
                    articles.add(article);
                }
            }
        }
    }
    
    public ArrayList<Article> articles(){ return articles; }
    
    public void selectedArticle(Article article){
        currentArticle = article;
    }
}
