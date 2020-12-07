package introduce.web;

import introduce.service.MemberService;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.member.MemberSaveRequestDto;
import introduce.web.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    @Value("${file.upload-dir}")
    private String fileUploadPath;

    @Value("${file.member-dir}")
    private String subFileUploadPath;

    private final MemberService memberService;

    @PostMapping("/api/member")
    public Long save(@RequestBody MemberSaveRequestDto requestDto, @RequestParam("file") MultipartFile file) throws Exception {
        return memberService.save(requestDto, file);
    }

    @PutMapping("/api/member/{id}")
    public Long update(@PathVariable Long id, @RequestBody MemberUpdateRequestDto requestDto, @RequestParam("file") MultipartFile file) throws Exception {
        return memberService.update(id, requestDto, file);
    }

    @DeleteMapping("/api/member/{id}")
    public Long delete(@PathVariable Long id) {
        memberService.delete(id);
        return id;
}

    @GetMapping("/api/member")
    public List<MemberResponseDto> findAll() {
        return memberService.findAll();
    }

    @GetMapping("/api/member/{id}")
    public MemberResponseDto findById(@PathVariable Long id) {
        return memberService.findById(id);
    }
}
