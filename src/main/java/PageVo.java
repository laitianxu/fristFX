public class PageVo {
    private String no;
    private String data;
    private String time;
    private int number;
    public PageVo() {
    }

    public PageVo(String no, String data, String time, int number) {
        this.no = no;
        this.data = data;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
