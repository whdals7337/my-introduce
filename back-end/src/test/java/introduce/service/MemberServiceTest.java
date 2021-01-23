package introduce.service;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.domain.network.Header;
import introduce.domain.network.Pagination;
import introduce.error.exception.file.FileNotTransferException;
import introduce.error.exception.member.MemberNotFoundException;
import introduce.web.dto.member.MemberRequestDto;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.membertotalinfo.MemberTotalInfoResponseDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


public class MemberServiceTest {

    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private SkillService skillService;
    @Mock
    private ProjectService projectService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        memberService = new MemberService(skillService, projectService);
        memberService.baseRepository = memberRepository;
        ReflectionTestUtils.setField(memberService, "fileUploadPath","/test-dir/files/");
        ReflectionTestUtils.setField(memberService, "domain", "http://localhost:8080");
        ReflectionTestUtils.setField(memberService, "dirType", "images");
        ReflectionTestUtils.setField(memberService, "subFileUploadPath", "member");
    }

    @Test
    public void saveWithFile() throws FileNotTransferException {
        given(memberRepository.save(any(Member.class))).willReturn(TestUtil.mockMember(1L, "N"));

        Header<MemberResponseDto> target = memberService.save(mockMemberRequestDto(), TestUtil.mockFile());

        assertThat(target.getStatus()).isEqualTo("200");

        MemberResponseDto data = target.getData();
        Member member = TestUtil.mockMember(1L, "N");
        validAll(data, member);
    }

    @Test
    public void updateWithFile() {
        given(memberRepository.findById(1L)).willReturn(Optional.of(TestUtil.mockMember(1L, "N")));

        Header target = memberService.update(mockMemberRequestDto(), 1L ,TestUtil.mockFile());

        assertThat(target.getStatus()).isEqualTo("200");

        MemberResponseDto data = (MemberResponseDto) target.getData();
        Member member = TestUtil.mockMember(1L, "N");
        validNotFile(data, member);
        assertThat(data.getFileOriginName()).isEqualTo("test.txt");

    }

    @Test
    public void updateWithoutFile() {
        given(memberRepository.findById(1L)).willReturn(Optional.of(TestUtil.mockMember(1L, "N")));

        Header target = memberService.update(mockMemberRequestDto(), 1L ,null);

        assertThat(target.getStatus()).isEqualTo("200");

        MemberResponseDto data = (MemberResponseDto) target.getData();
        Member member = TestUtil.mockMember(1L, "N");
        validAll(data, member);
    }

    @Test
    public void updateNotFoundMember() {
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> memberService.update(mockMemberRequestDto(), 1L, TestUtil.mockFile()))
                .withMessage("Member Entity가 존재하지 않습니다.");
    }

    @Test
    public void delete() {
        given(memberRepository.findById(1L)).willReturn(Optional.of(TestUtil.mockMember(1L, "N")));

        Header target = memberService.delete(1L);

        assertThat(target.getStatus()).isEqualTo("200");
    }

    @Test
    public void deleteNotFoundMember() {
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> memberService.delete(1L))
                .withMessage("Member Entity가 존재하지 않습니다.");
    }

    @Test
    public void findById() {
        given(memberRepository.findById(1L)).willReturn(Optional.of(TestUtil.mockMember(1L, "N")));

        Header<MemberResponseDto> target = memberService.findById(1L);

        assertThat(target.getStatus()).isEqualTo("200");

        MemberResponseDto data = target.getData();
        Member member = TestUtil.mockMember(1L, "N");
        validAll(data, member);
    }

    @Test
    public void findByIdNotFoundMember () {
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> memberService.findById(1L))
                .withMessage("Member Entity가 존재하지 않습니다.");
    }

    @Test
    public void findAll() {
        List<Member> list = new ArrayList<>();
        list.add(TestUtil.mockMember(1L, "Y"));
        list.add(TestUtil.mockMember(2L, "N"));
        list.add(TestUtil.mockMember(3L, "N"));
        list.add(TestUtil.mockMember(4L, "N"));

        given(memberRepository.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(list));

        Header<List<MemberResponseDto>> target = memberService
                .findAll(new MemberRequestDto(), PageRequest.of(0,4));

        assertThat(target.getStatus()).isEqualTo("200");

        List<MemberResponseDto> memberResponseDtoList = target.getData();
        int i = 0;
        for(MemberResponseDto data : memberResponseDtoList) {
            validAll(data, list.get(i));
            i++;
        }

        Pagination pagination = target.getPagination();
        assertThat(pagination.getTotalPages()).isEqualTo(1);
        assertThat(pagination.getTotalElements()).isEqualTo(4);
        assertThat(pagination.getCurrentPage()).isEqualTo(0);
        assertThat(pagination.getCurrentElements()).isEqualTo(4);
    }

    @Test
    public void findBySelectYN() {
        given(memberRepository.findBySelectYN("Y"))
                .willReturn(Optional.of(TestUtil.mockMember(1L, "Y")));

        Header<MemberResponseDto> target = memberService.findBySelectYN();

        assertThat(target.getStatus()).isEqualTo("200");

        MemberResponseDto data = target.getData();
        Member member = TestUtil.mockMember(1L, "Y");
        validAll(data, member);
    }

    @Test
    public void findBySelectYNNotFoundMember() {
        given(memberRepository.findBySelectYN("Y"))
                .willReturn(Optional.empty());

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> memberService.findBySelectYN())
                .withMessage("Member Entity가 존재하지 않습니다.");
    }

    @Test
    public void totalInfo() {
        given(memberRepository.findTotalInfo(1L)).willReturn(Optional.of(TestUtil.mockMember(1L, "N")));

        Header<MemberTotalInfoResponseDto> target = memberService.totalInfo(1L);

        assertThat(target.getStatus()).isEqualTo("200");

        MemberTotalInfoResponseDto data = target.getData();

        MemberResponseDto memberData = data.getMemberResponseDto();
        Member member = TestUtil.mockMember(1L, "N");
        validAll(memberData, member);

        assertThat(data.getSkillResponseDtoList()).isNull();
        assertThat(data.getProjectResponseDtoList()).isNull();
    }

    @Test
    public void totalInfoNotFoundMember() {
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> memberService.totalInfo(1L))
                .withMessage("Member Entity가 존재하지 않습니다.");
    }

    @Test
    public void updateSelect() {
        Member member = TestUtil.mockMember(1L, "N");
        List<Member> list = new ArrayList<>();
        list.add(member);
        list.add(TestUtil.mockMember(2L, "Y"));
        list.add(TestUtil.mockMember(3L, "N"));

        given(memberRepository.findAll()).willReturn(list);
        given(memberRepository.findById(1L))
                .willReturn(Optional.of(member));

        memberService.updateSelect(1L);

        assertThat(list.get(0).getSelectYN()).isEqualTo("Y");
        assertThat(list.get(1).getSelectYN()).isEqualTo("N");
        assertThat(list.get(2).getSelectYN()).isEqualTo("N");
    }

    @Test
    public void updateSelectNotFoundMember() {
        Member member = TestUtil.mockMember(1L, "N");
        List<Member> list = new ArrayList<>();
        list.add(member);
        list.add(TestUtil.mockMember(2L, "Y"));
        list.add(TestUtil.mockMember(3L, "N"));

        given(memberRepository.findAll()).willReturn(list);
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> memberService.updateSelect(1L))
                .withMessage("Member Entity가 존재하지 않습니다.");
    }

    @Test
    public void getMember() {
        Member member = TestUtil.mockMember(1L, "N");
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        Member target = memberService.getMember(1L);

        assertThat(target).isEqualTo(member);
    }

    @Test
    public void getMemberNotFoundMember() {
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> memberService.getMember(1L))
                .withMessage("Member Entity가 존재하지 않습니다.");
    }

    private void validAll(MemberResponseDto data, Member member) {
        assertThat(data.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(data.getComment()).isEqualTo(member.getComment());
        assertThat(data.getFileOriginName()).isEqualTo(member.getFileInfo().getFileOriginName());
        assertThat(data.getFileUrl()).isEqualTo(member.getFileInfo().getFileUrl());
        assertThat(data.getSubIntroduction()).isEqualTo(member.getSubIntroduction());
        assertThat(data.getIntroduction()).isEqualTo(member.getIntroduction());
        assertThat(data.getPhoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(data.getEmail()).isEqualTo(member.getEmail());
        assertThat(data.getSelectYN()).isEqualTo(member.getSelectYN());
    }

    private void validNotFile(MemberResponseDto data, Member member) {
        assertThat(data.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(data.getComment()).isEqualTo(member.getComment());
        assertThat(data.getSubIntroduction()).isEqualTo(member.getSubIntroduction());
        assertThat(data.getIntroduction()).isEqualTo(member.getIntroduction());
        assertThat(data.getPhoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(data.getEmail()).isEqualTo(member.getEmail());
        assertThat(data.getSelectYN()).isEqualTo(member.getSelectYN());
    }

    private MemberRequestDto mockMemberRequestDto() {
        return MemberRequestDto.builder()
                .comment("comment")
                .subIntroduction("subIntroduction")
                .introduction("introduction")
                .phoneNumber("phoneNumber")
                .email("email")
                .build();
    }
}
