package com.example.sbsj_process.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class QnaBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaBoardId;

    @Column(length = 64, nullable = false)
    private String title;

    @Column(length = 32, nullable = false)
    private String writer;

    @Column(length = 16, nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean privateCheck;

    @Lob
    private String content;

    @CreationTimestamp
    private Date regDate;

    @UpdateTimestamp
    private Date upDate;

    public QnaBoard(String title, String writer, String content, String password, Boolean privateCheck) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.password = password;
        this.privateCheck = privateCheck;
    }

}
