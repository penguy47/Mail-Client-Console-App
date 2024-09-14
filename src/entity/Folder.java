package src.entity;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public List<Mail> getMails(){
        return this.mails;
    }

    public void loadMailsStatusFromFile(String filePath){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            List<String> ids = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                ids.add(line);
            }
            for(Mail mail : mails){
                for(String id : ids){
                    if(mail.getId().equals(id)){
                        mail.setRead();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.name;
    }
}
