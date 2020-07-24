package blooger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author lostsh
 */
public class Article {
    private final String blogPath;
    private final String articleDir;
    private final File articleFile;
    
    private String title;
    private String date;
    private String digest;
    private String article;
    
    public Article(String blogPagesPath, String date, String title){
        this.date = date;
        this.title = title;
        article = "";
        
        this.blogPath = blogPagesPath;
        this.articleDir = String.format("%s/%s", blogPath, date);
        String validTitle = title.replace(" ", "-").replace("/", "-");
        this.articleFile = new File(String.format("%s/%s.html", articleDir, validTitle));
    }
    
    public void create() {
        File articleDirectory = new File(articleDir);
        if(articleDirectory.mkdir()){
            System.out.print("\n[*] Dir has been created ");
        }else{
            System.out.print("\n[!] Dir can't be created :cry:");
        }
        System.out.print(" | "+articleDir+"\n");
        
        System.out.println("\n[+] Going to create "+articleFile);
        try {
            articleFile.createNewFile();
            System.out.println("[*] Article has been created");
        } catch (IOException e) {
            System.err.println("[!] Unable to create file (" + e.getMessage() + ")");
        }
    }
    
    public void setDigest(String resume){
        this.digest = resume;
    }
    
    public void setArticle(String articleContent){
        this.article = articleContent;
    }
    
    public boolean exsists(){
        return articleFile.exists();
    }
    
    public String getTitle() { return title; }

    public String getArticleDir() { return articleDir; }

    public File getArticleFile() { return articleFile; }

    public String getDate() { return date; }

    public String getDigest() { return digest; }

    public String getArticle() { return article; } 
    
    private String html() {
        StringBuilder htmlArticle = new StringBuilder("");
        htmlArticle.append("\n"
                + "        <h3>"+title+"</h3>\n"
                +               article + "\n"
        );
        return htmlArticle.toString();
    }
    
    public void exportArticle(String template){
        try {
            BufferedReader templateReader = new BufferedReader(new FileReader(new File(template)));
            BufferedWriter wr = new BufferedWriter(new FileWriter(articleFile));
            String templateLine;
            while((templateLine=templateReader.readLine())!=null){
                if(templateLine.contains("[ARTICLE]")){
                    wr.write(html());
                }else{
                    wr.write(templateLine);
                }
                wr.newLine();
            }
            templateReader.close();
            wr.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[!] File "+template+" not found ("+ex.getLocalizedMessage()+")");
        } catch (IOException ex) {
            System.err.println("[!] Error while wirting on article File ("+ex.getMessage()+")");
        }
    }
    
    public void importContent(String templatePath){
        try {
            BufferedReader templateReader = new BufferedReader(new FileReader(new File(templatePath)));
            BufferedReader articleReader = new BufferedReader(new FileReader(articleFile));
            String templateLine = templateReader.readLine();
            String articleFileLine;
            while ((articleFileLine = articleReader.readLine()) != null) {
                //System.out.println("___"+templateLine+"\n___"+articleFileLine);
                //foreatch line on the artciel if this line is not in the template get it else read the folowing template line
                if (!excludeSpaces(articleFileLine).equals(excludeSpaces(templateLine)) && !excludeSpaces(templateLine).equals("[ARTICLE]")) {
                    if (articleFileLine.contains("<h3>")) {
                        int startTagIndex = articleFileLine.indexOf("<h3>");
                        title = articleFileLine.substring(startTagIndex + 4, articleFileLine.indexOf("</h3>"));
                    } else {
                        article += articleFileLine!=null?articleFileLine+"\n":"\n";
                    }
                } else {
                    templateLine = templateReader.readLine();
                }
            }
            templateReader.close();
            articleReader.close();
        } catch (FileNotFoundException ex) {
            System.err.println("[!] File "+templatePath+" or "+articleFile.getAbsolutePath()+" not found ("+ex.getLocalizedMessage()+")");
        } catch (IOException ex) {
            System.err.println("[!] Error on reading template file or article File ("+ex.getMessage()+")");
        }
    }
    
    private String excludeSpaces(String line){
        return line.replaceAll(" ", "");//.replaceAll("\t", "");
    }

    @Override
    public String toString() {
        return title;
    }
}