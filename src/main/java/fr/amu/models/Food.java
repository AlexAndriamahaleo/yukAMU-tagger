package fr.amu.models;

public class Food {

    public enum TAG {NONE, EXCELLENT, GOOD, MEDIOCRE, BAD};

	private int id ;
	private String name;
	private String imgurl ;
	private TAG tag ;
	private boolean done ;

	public Food() {
	}

	public Food(int id, String name, String imgurl, TAG tag, boolean done) {
		this.id = id;
		this.name = name;
		this.imgurl = imgurl;
		this.tag = tag;
		this.done = done;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public TAG getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = Food.TAG.valueOf(tag);
	}

	public boolean isDone() {
		return done;
	}

    public void setDone(boolean done) {
		this.done = done;
	}

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", tag='" + tag + '\'' +
                ", done=" + done +
                '}';
    }

}

