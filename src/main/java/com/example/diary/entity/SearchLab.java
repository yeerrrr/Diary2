package com.example.diary.entity;

import java.util.List;

public class SearchLab {
    private List<labelContent> LabelContent;
    private LabelData Label;

    public List<labelContent> getLabelContent() {
        return LabelContent;
    }

    public void setLabelContent(List<labelContent> labelContent) {
        LabelContent = labelContent;
    }

    public LabelData getLabel() {
        return Label;
    }

    public void setLabel(LabelData label) {
        Label = label;
    }

    @Override
    public String toString() {
        return "SearchLab{" +
                "LabelContent=" + LabelContent +
                ", Label=" + Label +
                '}';
    }
}
