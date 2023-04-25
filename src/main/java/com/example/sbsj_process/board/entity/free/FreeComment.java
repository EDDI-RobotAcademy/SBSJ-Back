package com.example.sbsj_process.board.entity.free;

import com.example.sbsj_process.board.entity.free.FreeBoard;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString( exclude = "freeBoard")
@Getter
@Setter
@Entity
public class FreeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freeCommentId;

    @Lob
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_id")
    private FreeBoard freeBoard;

    @Column(length = 32, nullable = false)
    private String writer;


    public void update(String comment) {
        this.comment = comment;
    }

}