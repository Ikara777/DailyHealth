package dto;


public class ManagementDTO {
    private String id;
    private String passwd;
    private String name;
    private String gender;
    private String tel;
    private String address;



    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String password) {
        this.passwd = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }



    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "ManagementDTO [id=" + id + ", password=" + passwd + ", name=" + name + ", gender="
                + gender + ", tel=" + tel + "address="+address+"]";
    }
}