package lk.pos.fx.shop.model;

public class UsersDTO {
    private String name;
    private String password;

    public UsersDTO() {
    }

    public UsersDTO(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsersDTO{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
