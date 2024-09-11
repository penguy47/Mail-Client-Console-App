package src.config;

import java.util.List;
import java.util.ArrayList;

public class FilterConfig {
    enum FilterType {
        FROM,
        SUBJECT,
        CONTENT,
        SPAM
    }

    FilterType type;
    public List<String> emails;
    public List<String> subjects;
    public List<String> contents;
    public String desFolder;

    public FilterConfig(FilterType type){
        this.type = type;
        this.emails = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.contents = new ArrayList<>();
    }
    
    public FilterConfig(String type){
        this.emails = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.contents = new ArrayList<>();
        type = type.trim();
        switch(type){
            case "from":
                this.type = FilterType.FROM;
                break;
            case "subject":
                this.type = FilterType.SUBJECT;
                break;
            case "content":
                this.type = FilterType.CONTENT;
                break;
            case "spam":
                this.type = FilterType.SPAM;
                break;    
        }
    }

    public void setFilterInfo(String filterInfo){
        String[] elements = filterInfo.split(",");
        switch (type) {
            case FilterType.FROM:
                for(String a : elements) emails.add(a.trim());
                break;
            case FilterType.SUBJECT:
                for(String a : elements) subjects.add(a.trim());
                break;
            case FilterType.CONTENT:
                for(String a : elements) contents.add(a.trim());
                break;
            case FilterType.SPAM:
                for(String a : elements) contents.add(a.trim());
                for(String a : elements) subjects.add(a.trim());
                break;
        }
    }

    public void setFolder(String folder){
        this.desFolder = folder;
    }

    @Override
    public String toString() {
        return "FilterConfig{" +
                "type='" + type +"\'" +
                ", emails='" + emails + '\'' +
                ", subjects='" + subjects + '\''+
                ", contents='" + contents + '\''+
                ", folder='" + desFolder + '\''+
                '}';
    }
}
