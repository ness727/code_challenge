package com.megamaker.admin.dto.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProblemSearchCond {
    private String search;
    private Integer level;
}
