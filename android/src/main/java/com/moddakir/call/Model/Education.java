package com.moddakir.call.Model;

import java.io.Serializable;
import java.util.List;


public class Education  implements Serializable {
    private List<String> paths;
    private String level;
    private boolean isEducationDialogDisplayed;

    public Education() {
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isEducationDialogDisplayed() {
        return isEducationDialogDisplayed;
    }

    public void setEducationDialogDisplayed(boolean educationDialogDisplayed) {
        isEducationDialogDisplayed = educationDialogDisplayed;
    }
}
