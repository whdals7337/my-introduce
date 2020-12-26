package introduce.web;

import introduce.domain.member.MemberRepository;
import introduce.web.dto.member.MemberRequestDto;
import introduce.web.dto.member.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController extends CrudController<MemberRequestDto, MemberResponseDto, MemberRepository> {
}
