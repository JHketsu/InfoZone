package model;

import java.util.ArrayList;
import java.util.Vector;

public class ArticleMessage extends NormalMessage{
    private String num;
    private ArrayList<Article> articles=new ArrayList<Article>();

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }


}
