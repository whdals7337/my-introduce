package introduce.web.api;

import introduce.domain.member.MemberRepository;
import introduce.domain.network.Header;
import introduce.service.MemberService;
import introduce.web.CrudController;
import introduce.web.dto.member.MemberRequestDto;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.membertotalinfo.MemberTotalInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController extends CrudController<MemberRequestDto, MemberResponseDto, MemberRepository> {

    private final MemberService memberService;

    @GetMapping("/{id}/totalInfo")
    public Header<MemberTotalInfoResponseDto> totalInfo(@PathVariable Long id) {
        return memberService.totalInfo(id);
    }

    @GetMapping("/select")
    public Header<MemberResponseDto> findBySelectYN() {return memberService.findBySelectYN(); }

    @PatchMapping("/select/{id}")
    public Header<MemberResponseDto> updateSelect(@PathVariable Long id) {
        return memberService.updateSelect(id);
    }
}
