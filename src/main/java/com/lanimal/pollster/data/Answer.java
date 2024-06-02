package com.lanimal.pollster.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "answer")
public class Answer extends AbstractEntity {
    @Column(name = "poll", nullable = false)
    private Long pollId;

    @Column(name = "text", nullable = false, length = 250)
    private String text;

    @ManyToOne
    @JoinColumn(name = "poll", referencedColumnName = "id", insertable = false, updatable = false)
    private Poll poll;
}
