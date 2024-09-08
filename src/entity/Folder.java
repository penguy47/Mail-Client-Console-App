package src.entity;

import java.util.List;
import java.util.ArrayList;

public class Folder {
    String name;
    List<Mail> mails;

    public Folder(String name){
        this.name = name;
        mails = new ArrayList<>();
    }

    public void addMail(Mail mail){
        mails.add(mail);
    }

    public void removeMail(Mail mail){
        mails.remove(mail);
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}
