package com.lanimal.pollster.dto;

import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class PollDTO {
    String question;
    List<String> options;
}
