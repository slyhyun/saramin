package com.wsd.saramin.apply.controller;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.apply.service.ApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@Tag(name = "Application Management", description = "지원 관리 API")
public class ApplyController {

    private final ApplyService applyService;

    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    @Operation(summary = "지원하기", description = "사용자가 특정 공고에 지원합니다.")
    @PostMapping
    public ResponseEntity<String> apply(
            @Parameter(description = "지원자의 사용자 ID") @RequestParam Long userId,
            @Parameter(description = "지원할 공고 ID") @RequestParam Long jobId) {
        applyService.apply(userId, jobId);
        return ResponseEntity.ok("지원이 완료되었습니다.");
    }

    @Operation(summary = "지원 내역 조회", description = "특정 사용자의 지원 내역을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ApplyDTO>> getApplications(
            @Parameter(description = "사용자 ID") @RequestParam Long userId,
            @Parameter(description = "지원 상태 필터 (PENDING, APPROVED, CANCELLED)") @RequestParam(required = false) String status,
            @Parameter(description = "페이지 번호 (기본값: 1)") @RequestParam(defaultValue = "1") String page) {
        List<ApplyDTO> applications = applyService.getApplies(userId, status, page);
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "지원 취소", description = "특정 지원 내역을 취소합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelApplication(
            @Parameter(description = "지원 ID") @PathVariable Long id) {
        applyService.cancelApply(id);
        return ResponseEntity.ok("지원이 취소되었습니다.");
    }
}
