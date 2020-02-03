package hu.companycar.model.domain;

public class Pair {

    private final int left;
    private final int right;

    public Pair(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getRight() {
        return right;
    }
}
