package gr.csd.plantsreminder;

public class PlantData {

    private long id;
    private String name;
    private String type;
    private String last;
    private int water;
    private int fern;
    private int prun;

    public PlantData() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getFern() {
        return fern;
    }

    public void setFern(int fern) {
        this.fern = fern;
    }

    public int getPrun() {
        return prun;
    }

    public void setPrun(int prun) {
        this.prun = prun;
    }
}
