package com.wsd.saramin.company.controller;

import com.wsd.saramin.company.dto.CompanyDTO;
import com.wsd.saramin.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@Tag(name = "Company", description = "회사 관련 API")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(
            summary = "회사 정보 조회",
            description = "주어진 회사 ID로 회사 정보를 조회합니다.",
            parameters = @Parameter(name = "id", description = "조회할 회사의 ID", required = true)
    )
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) {
        CompanyDTO company = companyService.getCompany(id);
        return ResponseEntity.ok(company);
    }

    @Operation(
            summary = "회사 정보 수정",
            description = "주어진 회사 ID와 데이터를 사용하여 회사 정보를 수정합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 회사 정보", required = true
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.updateCompany(id, companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }
}
