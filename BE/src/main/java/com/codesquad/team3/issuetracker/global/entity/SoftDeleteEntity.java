package com.codesquad.team3.issuetracker.global.entity;

import lombok.Getter;

public interface SoftDeleteEntity {


     void delete();

     void recover();


}
