package introduce.web;

import introduce.domain.member.MemberRepository;
import introduce.domain.network.Header;
import introduce.service.MemberService;
import introduce.web.dto.member.MemberRequestDto;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.membertotalinfo.MemberTotalInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController extends CrudController<MemberRequestDto, MemberResponseDto, MemberRepository> {

    private final MemberService memberService;

    @GetMapping("/{id}/totalInfo")
    public Header<MemberTotalInfoResponseDto> totalInfo(@PathVariable Long id) {
        return memberService.totalInfo(id);
    }
}
