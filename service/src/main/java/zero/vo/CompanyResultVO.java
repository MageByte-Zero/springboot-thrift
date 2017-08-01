package zero.vo;

import zero.model.BaseModel;
import zero.model.User;

import java.util.List;

public class CompanyResultVO extends BaseModel {
    private int id;
    private String code;
    private List<User> userList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "CompanyResultVO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", userList=" + userList +
                '}';
    }
}
