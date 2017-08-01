package zero.model;

public class Status {

    private int state;

    private String msg;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Status{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                '}';
    }
}
