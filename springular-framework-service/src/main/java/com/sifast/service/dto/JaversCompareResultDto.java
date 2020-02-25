package com.sifast.service.dto;

public class JaversCompareResultDto {

    private String left;

    private String right;

    private String propertyName;

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("JaversCompareResultDto [left=");
        builder.append(left);
        builder.append(", right=");
        builder.append(right);
        builder.append(", propertyName=");
        builder.append(propertyName);
        builder.append("]");
        return builder.toString();
    }
}