package com.cy.pj.common.cache;

import java.util.Arrays;
import java.util.Objects;

public class SimpleCacheKey {
    private Object[] params;
    public SimpleCacheKey(Object[] params) {
        this.params = params;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleCacheKey that = (SimpleCacheKey) o;
        if(this.params==that.params)return true;
        if(this.params.length==0&&that.params.length==0)return true;
        return Arrays.toString(this.params).equals(Arrays.toString(that.params));
    }
    @Override
    public int hashCode() {
        return Objects.hash(params);
    }
}
