CREATE TABLE issue (
                       id int PRIMARY KEY AUTO_INCREMENT,
                       writer_id int NOT NULL ,
                       title varchar(60) NOT NULL,
                       create_time timestamp NOT NULL,
                       is_closed bool DEFAULT false,
                       milestone_id int,
                       is_deleted bool DEFAULT false
);

CREATE TABLE comment (
                         id int PRIMARY KEY AUTO_INCREMENT,
                         writer_id int NOT NULL ,
                         contents varchar(500) NOT NULL,
                         create_time timestamp NOT NULL,
                         issue_id int NOT NULL ,
                         is_deleted bool DEFAULT false,

                         is_primary bool DEFAULT false
);

CREATE TABLE member (
                        id int PRIMARY KEY AUTO_INCREMENT,
                        member_id varchar(16) NOT NULL,
                        password varchar(12) NOT NULL,
                        nickname varchar(20) NOT NULL ,
                        profile_img varchar(50),
                        birthday timestamp NOT NULL,
                        join_time timestamp NOT NULL,
                        email varchar(50) NOT NULL,
                        is_deleted bool DEFAULT false,
                        UNIQUE (member_id, is_deleted),
                        UNIQUE (nickname, is_deleted)
);

CREATE TABLE label (
                       id int PRIMARY KEY AUTO_INCREMENT,
                       title varchar(10) NOT Null,
                       description varchar(50) NOT NULL,
                       color char(10) DEFAULT '000000' NOT NULL,
                       font_color char(10) not null,
                       is_deleted bool DEFAULT false

);

CREATE TABLE milestone (
                           id int primary key AUTO_INCREMENT,
                           title varchar(20) NOT NULL ,
                           description varchar(100),
                           deadline timestamp,
                           is_closed bool DEFAULT false,
                           is_deleted bool DEFAULT false
);

CREATE TABLE labels_in_issue (
                                 label_id int NOT NULL,
                                 issue_id int NOT NULL,
                                 PRIMARY KEY (label_id, issue_id)
);

CREATE TABLE member_in_issue (
                                 member_id int NOT NULL,
                                 issue_id int NOT NULL,
                                 PRIMARY KEY (member_id, issue_id)
);

CREATE TABLE uploaded_file_in_comment (
                                          file varchar(50),
                                          comment_id int,
                                          PRIMARY KEY (comment_id)
);

CREATE TABLE assigner(
                         issue_id int,
                         assigner_id int,
                         PRIMARY KEY (issue_id, assigner_id)

);

ALTER TABLE assigner ADD FOREIGN KEY (issue_id) REFERENCES issue(id);

ALTER TABLE assigner ADD FOREIGN KEY (assigner_id) REFERENCES member(id);

ALTER TABLE uploaded_file_in_comment ADD FOREIGN KEY (comment_id) REFERENCES comment (id);

ALTER TABLE comment ADD FOREIGN KEY (issue_id) REFERENCES issue (id);

ALTER TABLE issue ADD FOREIGN KEY (writer_id) REFERENCES member (id);

ALTER TABLE issue ADD FOREIGN KEY (milestone_id) REFERENCES milestone (id);

ALTER TABLE comment ADD FOREIGN KEY (writer_id) REFERENCES member (id);

ALTER TABLE labels_in_issue ADD FOREIGN KEY (label_id) REFERENCES label (id);

ALTER TABLE labels_in_issue ADD FOREIGN KEY (issue_id) REFERENCES issue (id);