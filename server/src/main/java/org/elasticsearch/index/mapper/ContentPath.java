/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.index.mapper;

import java.util.Stack;

public final class ContentPath {

    private static final char DELIMITER = '.';

    private final StringBuilder sb;

    private int index = 0;

    private Stack<Integer> endIndex;

    private boolean withinLeafObject = false;

    public ContentPath() {
        this.sb = new StringBuilder();
        this.endIndex = new Stack<>();
    }

    String[] getPath() {
        // used for testing
        return sb.toString().split("[.]");
    }

    public void add(String name) {
        sb.append(name).append(DELIMITER);
        endIndex.push(sb.length() - 1);
        ++index;
    }

    public void remove() {
        endIndex.pop();
        if (endIndex.isEmpty()) {
            sb.setLength(0);
            --index;
            return;
        }
        int limit = endIndex.lastElement();
        sb.setLength(limit + 1);
        --index;
    }

    public void setWithinLeafObject(boolean withinLeafObject) {
        this.withinLeafObject = withinLeafObject;
    }

    public boolean isWithinLeafObject() {
        return withinLeafObject;
    }

    public String pathAsText(String name) {
        sb.append(name);
        return sb.toString();
    }

    public int length() {
        return index;
    }
}
