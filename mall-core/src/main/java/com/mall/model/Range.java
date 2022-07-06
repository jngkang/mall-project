package com.mall.model;

import java.io.Serializable;

public class Range<T> implements Serializable {
    private T left;
    private boolean leftEQ;
    private T right;
    private boolean rightEQ;
    public Range(){

    }
    public Range(T left, boolean leftEQ, T right, boolean rightEQ) {
        this.left = left;
        this.leftEQ = leftEQ;
        this.right = right;
        this.rightEQ = rightEQ;
    }

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public boolean isLeftEQ() {
        return leftEQ;
    }

    public void setLeftEQ(boolean leftEQ) {
        this.leftEQ = leftEQ;
    }

    public T getRight() {
        return right;
    }

    public void setRight(T right) {
        this.right = right;
    }

    public boolean isRightEQ() {
        return rightEQ;
    }

    public void setRightEQ(boolean rightEQ) {
        this.rightEQ = rightEQ;
    }

     public static <T> RangeBuilder<T> builder(){
         return new RangeBuilder<T>();
    }

    public static class RangeBuilder<T> implements Serializable {
        private T left;
        private boolean leftEQ = true;
        private T right;
        private boolean rightEQ = true;

         RangeBuilder() {
        }

        public RangeBuilder<T> left(T left) {
            this.left = left;
            return this;
        }

        public RangeBuilder<T> leftEQ(boolean leftEQ) {
            this.leftEQ = leftEQ;
            return this;
        }

        public RangeBuilder<T> right(T right) {
            this.right = right;
            return this;
        }

        public RangeBuilder<T> rightEQ(boolean rightEQ) {
            this.rightEQ = rightEQ;
            return this;
        }

        public Range<T> build() {
            return new Range<T>(this.left, this.leftEQ, this.right, this.rightEQ);
        }

    }
}