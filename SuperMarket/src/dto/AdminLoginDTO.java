package dto;

public class AdminLoginDTO {
    private String adminUsrName;
    private String adminPassword;

    public AdminLoginDTO(String adminUsrName, String adminPassword) {
        this.adminUsrName = adminUsrName;
        this.adminPassword = adminPassword;
    }

    public AdminLoginDTO() {
    }

    public String getAdminUsrName() {
        return adminUsrName;
    }

    public void setAdminUsrName(String adminUsrName) {
        this.adminUsrName = adminUsrName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "AdminLogin{" +
                "adminUsrName='" + adminUsrName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }
}
