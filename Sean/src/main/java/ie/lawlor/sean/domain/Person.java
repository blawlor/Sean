package ie.lawlor.sean.domain;

/**
 * Created by blawlor on 30/06/13.
 */
public class Person {

    private long id;
    private String name;
    private int order;
    private String imageFileName;

    public Person(long id, String name, int order, String imageFileName) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.imageFileName = imageFileName;
    }

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", imageFileName='" + imageFileName + '\'' +
                '}';
    }
}
