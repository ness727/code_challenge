package com.megamaker.admin.presentation;

import com.megamaker.admin.domain.problem.vo.Level;
import com.megamaker.admin.domain.problem.dto.ProblemSearchCond;
import com.megamaker.admin.domain.problem.dto.RequestProblemUpdate;
import com.megamaker.admin.domain.problem.dto.ResponseListProblem;
import com.megamaker.admin.domain.problem.dto.ResponseProblem;
import com.megamaker.admin.application.ProblemService;
import com.megamaker.admin.domain.problem.mapper.ProblemUpdate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("levels", Level.values());
        return "problem/edit-form";
    }

    @PutMapping
    public String update(@ProblemUpdate RequestProblemUpdate requestProblemUpdate,
                         Model model, HttpServletRequest request) {
        ResponseProblem updatedProblem = problemService.update(requestProblemUpdate);
        model.addAttribute("problem", updatedProblem);
        return "redirect:problem/" + updatedProblem.getId() + "?" + request.getQueryString();
    }

    @DeleteMapping
    public String remove(@RequestParam Long id, HttpServletRequest request) {
        problemService.remove(id);
        return "redirect:problem/list?" + request.getQueryString();
    }
}
