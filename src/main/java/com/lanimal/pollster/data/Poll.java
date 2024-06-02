package com.lanimal.pollster.data;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity(name = "poll")
public class Poll extends AbstractEntity {

    @Column(name = "chat_link", nullable = false, length = 100)
    private String chatLink;

    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @Column(name = "end_message", length = 250)
    private String endMessage;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "time_to_live", nullable = false)
    private Long timeToLive;

    @Column(name = "repeat_schedule", length = 50)
    private String repeatSchedule;

    @Column(name = "question", nullable = false, length = 250)
    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;
}
