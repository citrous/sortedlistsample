package jp.citrous.sortedlist_sample;

/**
 * Created by citrous on 2015/07/12.
 */
public class SampleData {

    private int id;
    private String text;

    public SampleData(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
