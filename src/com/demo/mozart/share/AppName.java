package com.demo.mozart.share;

public class AppName {
    private String packageName;
    private String className;
    private String labelName;

    public AppName(String packageName, String className, String labelName) {
        this.packageName = packageName;
        this.className = className;
        this.labelName = labelName;
    }

    public AppName() {
        this("", "", "");
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("packageName: ").append(packageName);
        sBuilder.append('\n');
        sBuilder.append("className: ").append(className);
        sBuilder.append('\n');
        sBuilder.append("labelName: ").append(labelName);
        return sBuilder.toString();
    }

}
