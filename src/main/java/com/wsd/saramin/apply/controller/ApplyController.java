package com.wsd.saramin.apply.controller;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.apply.service.ApplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplyController {

    private final ApplyService applyService;

    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    // 지원하기
    @PostMapping
    public ResponseEntity<String> apply(
            @RequestParam Long userId,
            @RequestParam Long jobId
    ) {
        applyService.apply(userId, jobId);
        return ResponseEntity.ok("지원이 완료되었습니다.");
    }

    // 지원 내역 조회
    @GetMapping
    public ResponseEntity<List<ApplyDTO>> getApplications(@RequestParam Long userId) {
        List<ApplyDTO> applications = applyService.getApplies(userId);
        return ResponseEntity.ok(applications);
    }

    // 지원 취소
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelApplication(@PathVariable Long id) {
        applyService.cancelApply(id);
        return ResponseEntity.ok("지원이 취소되었습니다.");
    }
}
