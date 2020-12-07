package introduce.service;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.member.MemberSaveRequestDto;
import introduce.web.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getMemberId();
    }

    @Transactional
    public Long update(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다."));
        member.update(requestDto.getComment(), requestDto.getHeaderImagePath(), requestDto.getImageOriginName(),
                requestDto.getIntroduction(), requestDto.getPhoneNumber(), requestDto.getEmail());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다."));
        memberRepository.delete(member);
    }

    @Transactional
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream().map(MemberResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public MemberResponseDto findById(Long id) {
        Member entity = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다."));
        return new MemberResponseDto(entity);
    }
}
