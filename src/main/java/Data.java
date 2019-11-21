import java.util.Objects;

public class Data{
    private String no;
    private String data;
    private Integer number;

    public Data() {
    }

    public Data(String no, String data, Integer number) {
        this.no = no;
        this.data = data;
        this.number = number;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Data{" +
                "no='" + no + '\'' +
                ", data='" + data + '\'' +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Objects.equals(no, data.no);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no);
    }


}
