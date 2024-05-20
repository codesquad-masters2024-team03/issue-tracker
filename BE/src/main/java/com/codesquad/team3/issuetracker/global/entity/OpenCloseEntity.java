package com.codesquad.team3.issuetracker.global.entity;

import lombok.Getter;

public interface OpenCloseEntity {

    void close();
    void open();
    boolean isClosed();
}

