package com.megamaker.admin.controller;

import com.megamaker.admin.dto.problem.ProblemSearchCond;
import com.megamaker.admin.dto.problem.ResponseListProblem;
import com.megamaker.admin.dto.problem.ResponseProblem;
import com.megamaker.admin.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/problem")
@Controller
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping("/list")
    public String list(@ModelAttribute ProblemSearchCond problemSearchCond,
                       @PageableDefault(sort = "id", direction = ASC) Pageable pageable,
                       Model model) {
        Page<ResponseListProblem> foundProblemList = problemService.findAll(problemSearchCond, pageable);
        model.addAttribute("problemPage", foundProblemList);
        return "problem/list";
    }

    @GetMapping("/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ResponseProblem foundProblem = problemService.findById(id);
        model.addAttribute("problem", foundProblem);
        return "problem/edit-form";
    }


}
