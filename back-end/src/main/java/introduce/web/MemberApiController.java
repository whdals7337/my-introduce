package introduce.web;

import introduce.service.MemberService;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.member.MemberSaveRequestDto;
import introduce.web.dto.member.MemberUpdateRequestDto;
import introduce.web.dto.project.ProjectResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/member")
    public Long save(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.save(requestDto);
    }

    @PutMapping("/api/member/{id}")
    public Long update(@PathVariable Long id, @RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.update(id, requestDto);
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
