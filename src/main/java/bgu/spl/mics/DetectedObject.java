package bgu.spl.mics;

public class DetectedObject {
    private String id;
    private String description;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  DetectedObject(String id, String description)
    {
        this.id = id;
        this.description = description;
    }
}
