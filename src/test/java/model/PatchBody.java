package model;

public class PatchBody {
    public PatchBody() {
    }
    public PatchBody(String updateName) {
        this.updateName = updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    String updateName;

}
