package proj.me.bitframe.frames.tournament;

public class Picture implements Cloneable{
    private int width;
    private int height;
    private String uri;
    private int index;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        Picture p = (Picture)o;
        return this.width == p.width && this.height == p.height && this.uri.equals(p.uri) && this.index == p.index;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + uri.hashCode();
        result = 31 * result + index;
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}