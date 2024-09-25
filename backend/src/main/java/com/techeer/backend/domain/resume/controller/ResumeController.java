package com.techeer.backend.domain.resume.controller;

import com.techeer.backend.domain.resume.dto.request.CreateResumeReq;
import com.techeer.backend.domain.resume.dto.response.ResumeResponse;
import com.techeer.backend.domain.resume.entity.Resume;
import com.techeer.backend.domain.resume.service.ResumeService;
import com.techeer.backend.domain.user.entity.User;
import com.techeer.backend.domain.user.service.UserService;
import com.techeer.backend.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

//todo @RequiredArgsConstructor 추가 후 만든 생성자 삭제
@RestController
@RequiredArgsConstructor
@Tag(name = "resume", description = "Resume API")
@RequestMapping("/api/v1")
public class ResumeController {
    private final UserService userService;
    private final ResumeService resumeService;


    private final Map<Resume.TagType, Function<String, Enum<?>>> tagTypeMap = new HashMap<>() {{
        put(Resume.TagType.POSITION, tagValue -> Resume.Position.valueOf(tagValue.toUpperCase()));
        put(Resume.TagType.TECH_STACK, tagValue -> Resume.TechStack.valueOf(tagValue.toUpperCase()));
    }};

    // 이력서 등록
    // todo
    @Operation(summary = "이력서 등록")
    @PostMapping(value="/resumes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> resumeRegistration(
            @RequestPart @Valid CreateResumeReq createResumeReq,
            @RequestPart(name = "resume_file") @Valid MultipartFile resumeFile) throws IOException {

        // 파일 유효성 검사 -> 나중에 vaildtor로 변경해서 유효성 검사할 예정
        if (resumeFile.isEmpty()) {
            throw new IllegalArgumentException("이력서 파일이 비어있습니다.");
        }

        if (!resumeFile.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("이력서 파일은 PDF 형식만 허용됩니다.");
        }

        // todo 유저 이름으로 객체 탐색
        Optional<User> registrars = userService.findUserByName(createResumeReq.getUsername());
        User registrar = null;
        if (registrars.isPresent()) {registrar = registrars.get();}

        // resume db에 저장
        resumeService.createResume(registrar, createResumeReq, resumeFile);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResumeResponse>> searchResumesByUserName(@RequestParam("user_name") String userName) {
        List<ResumeResponse> resumeResponses = resumeService.searchResumesByUserName(userName);
        return ResponseEntity.ok(resumeResponses);
    }

    @GetMapping("/resumes/{tagType}/{tagValue}")
    public ResponseEntity<List<Resume>> getResumesByTag(
            @PathVariable Resume.TagType tagType,
            @PathVariable String tagValue) {

        Enum<?> tag = getTagEnum(tagType, tagValue);
        List<Resume> resumes = resumeService.getResumesByTag(tagType, tag);
        return ResponseEntity.ok(resumes);
    }

    private Enum<?> getTagEnum(Resume.TagType tagType, String tagValue) {
        return tagTypeMap.getOrDefault(tagType, t -> {
            throw new IllegalArgumentException("태그 유형이 존재하지 않습니다.");
        }).apply(tagValue);
    }

}
