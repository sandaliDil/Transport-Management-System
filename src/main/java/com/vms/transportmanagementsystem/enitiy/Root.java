package com.vms.transportmanagementsystem.enitiy;

public class Root {

    private int rootId;
    private String rootDetails;

    @Override
    public String toString() {
        return "Root{" +
                "rootId=" + rootId +
                ", rootDetails='" + rootDetails + '\'' +
                '}';
    }

    public Root(String rootDetails, int rootId) {
        this.rootDetails = rootDetails;
        this.rootId = rootId;
    }

    public Root() {
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public String getRootDetails() {
        return rootDetails;
    }

    public void setRootDetails(String rootDetails) {
        this.rootDetails = rootDetails;
    }
}
