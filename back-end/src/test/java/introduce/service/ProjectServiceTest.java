package introduce.service;

import introduce.domain.FileInfo;
import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.domain.network.Header;
import introduce.domain.network.Pagination;
import introduce.domain.project.Project;
import introduce.domain.project.ProjectRepository;
import introduce.error.exception.member.MemberNotFoundException;
import introduce.error.exception.project.ProjectNotFoundException;
import introduce.web.dto.project.ProjectRequestDto;
import introduce.web.dto.project.ProjectResponseDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ProjectRepository projectRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        projectService = new ProjectService(memberRepository);
        projectService.baseRepository = projectRepository;
        ReflectionTestUtils.setField(projectService, "fileUploadPath","/test-dir/files/");
        ReflectionTestUtils.setField(projectService, "domain", "http://localhost:8080");
        ReflectionTestUtils.setField(projectService, "dirType", "images");
        ReflectionTestUtils.setField(projectService, "subFileUploadPath", "project");

    }

    @Test
    public void saveWithFile() throws IOException {
        Member member = TestUtil.mockMember(1L, "N");

        given(projectRepository.save(any(Project.class))).willReturn(mockProject(member, 1L, 1));

        Header<ProjectResponseDto> target = projectService.save(mockProjectRequestDto(1L, 1), TestUtil.mockFile());

        assertThat(target.getStatus()).isEqualTo("200");

        ProjectResponseDto data = target.getData();
        Project project = mockProject(member, 1L, 1);
        validAll(data, project);
    }

    @Test
    public void updateWithFile() {
        Member member = TestUtil.mockMember(1L, "N");

        given(projectRepository.findById(1L)).willReturn(Optional.of(mockProject(member, 1L, 1)));
        given(memberRepository.getOne(1L)).willReturn(TestUtil.mockMember(1L, "N"));

        Header target = projectService.update(mockProjectRequestDto(1L, 1), 1L,  TestUtil.mockFile());

        assertThat(target.getStatus()).isEqualTo("200");

        ProjectResponseDto data = (ProjectResponseDto) target.getData();
        Project project = mockProject(member, 1L, 1);
        validNotFile(data, project);
        assertThat(data.getFileOriginName()).isEqualTo("test.txt");
    }

    @Test
    public void updateWithoutFile() {
        Member member = TestUtil.mockMember(1L, "N");

        given(projectRepository.findById(1L)).willReturn(Optional.of(mockProject(member, 1L, 1)));
        given(memberRepository.getOne(1L)).willReturn(TestUtil.mockMember(1L, "N"));

        Header target = projectService.update(mockProjectRequestDto(1L, 1), 1L,  null);

        assertThat(target.getStatus()).isEqualTo("200");

        ProjectResponseDto data = (ProjectResponseDto) target.getData();
        Project project = mockProject(member, 1L, 1);
        validAll(data, project);
    }

    @Test
    public void updateDiffLevel() {
        Member member = TestUtil.mockMember(1L, "N");
        List<Project> list = new ArrayList<>();
        list.add(mockProject(member, 2L, 2));
        list.add(mockProject(member, 3L, 3));

        given(projectRepository.findById(1L)).willReturn(Optional.of(mockProject(member, 1L, 1)));
        given(memberRepository.getOne(1L)).willReturn(TestUtil.mockMember(1L, "N"));
        given(projectRepository.findByLevelBetween(2,3)).willReturn(list);

        Header target = projectService.update(mockProjectRequestDto(1L, 3), 1L,  null);

        assertThat(target.getStatus()).isEqualTo("200");

        int i = 1;
        for(Project pro : list) {
            assertThat(pro.getLevel()).isEqualTo(i);
            i++;
        }

        ProjectResponseDto data = (ProjectResponseDto) target.getData();
        assertThat(data.getLevel()).isEqualTo(3);
    }


    @Test
    public void updateNotFoundProject() {
        given(projectRepository.findById(1L)).willReturn(Optional.empty());

        assertThatExceptionOfType(ProjectNotFoundException.class)
                .isThrownBy(() -> projectService.update(mockProjectRequestDto(1L, 1), 1L, TestUtil.mockFile()))
                .withMessage("Project Entity가 존재하지 않습니다.");
    }

    @Test
    public void delete() {
        given(projectRepository.findById(1L))
                .willReturn(Optional.of(mockProject(TestUtil.mockMember(1L, "N"),1L, 3)));
        Header target = projectService.delete(1L);
        assertThat(target.getStatus()).isEqualTo("200");
    }

    @Test
    public void deleteNotFoundProject() {
        given(projectRepository.findById(1L)).willReturn(Optional.empty());

        assertThatExceptionOfType(ProjectNotFoundException.class)
                .isThrownBy(() -> projectService.delete(1L))
                .withMessage("Project Entity가 존재하지 않습니다.");
    }

    @Test
    public void findById() {
        Member member = TestUtil.mockMember(1L, "N");
        given(projectRepository.findById(1L)).willReturn(Optional.of(mockProject(member, 1L, 1)));
        Header<ProjectResponseDto> target = projectService.findById(1L);

        assertThat(target.getStatus()).isEqualTo("200");

        ProjectResponseDto data = target.getData();
        Project project = mockProject(member, 1L, 1);
        validAll(data, project);
    }

    @Test
    public void findByIdNotFoundProject () {
        given(projectRepository.findById(1L)).willReturn(Optional.empty());
        assertThatExceptionOfType(ProjectNotFoundException.class)
                .isThrownBy(() -> projectService.findById(1L))
                .withMessage("Project Entity가 존재하지 않습니다.");
    }

    @Test
    public void findAll() {
        Member member = TestUtil.mockMember(1L, "N");
        List<Project> list = new ArrayList<>();
        list.add(mockProject(member, 1L, 1));
        list.add(mockProject(member, 2L, 1));
        list.add(mockProject(member, 3L, 1));
        list.add(mockProject(member, 4L, 1));

        given(projectRepository.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(list));

        Header<List<ProjectResponseDto>> target = projectService
                .findAll(new ProjectRequestDto(), PageRequest.of(0,4));

        List<ProjectResponseDto> projectResponseDtoList = target.getData();
        int i = 0;
        for(ProjectResponseDto data : projectResponseDtoList) {
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
    public void findAllWithMemberId() {
        List<Project> list = new ArrayList<>();
        list.add(mockProject(TestUtil.mockMember(1L, "N"), 1L, 1));
        list.add(mockProject(TestUtil.mockMember(1L, "N"), 2L, 1));

        given(memberRepository.findById(1L)).willReturn(Optional.of(TestUtil.mockMember(1L, "N")));
        given(projectRepository.findAllByMember(any(Member.class), any(Pageable.class))).willReturn(new PageImpl<>(list));

        Header<List<ProjectResponseDto>> target = projectService
                .findAll(mockProjectRequestDto(1L, 1), PageRequest.of(0,4));

        List<ProjectResponseDto> projectResponseDtoList = target.getData();
        int i = 0;
        for(ProjectResponseDto data : projectResponseDtoList) {
            validAll(data, list.get(i));
            i++;
        }

        Pagination pagination = target.getPagination();
        assertThat(pagination.getTotalPages()).isEqualTo(1);
        assertThat(pagination.getTotalElements()).isEqualTo(2);
        assertThat(pagination.getCurrentPage()).isEqualTo(0);
        assertThat(pagination.getCurrentElements()).isEqualTo(2);
    }

    @Test
    public void findAllWithMemberIdMemberNotFound() {
        List<Project> list = new ArrayList<>();
        list.add(mockProject(TestUtil.mockMember(1L, "N"), 1L, 1));
        list.add(mockProject(TestUtil.mockMember(1L, "N"), 2L, 1));

        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> projectService.findAll(mockProjectRequestDto(1L, 1), PageRequest.of(0,4)))
                .withMessage("Member Entity가 존재하지 않습니다.");
    }

    @Test
    public void getProject() {
        Member member = TestUtil.mockMember(1L, "N");
        Project project = mockProject(member, 1L, 1);

        given(projectRepository.findById(1L)).willReturn(Optional.of(project));

        Project target = projectService.getProject(1L);

        assertThat(target).isEqualTo(project);
    }

    @Test
    public void getProjectNotFoundProject() {
        given(projectRepository.findById(1L)).willReturn(Optional.empty());

        assertThatExceptionOfType(ProjectNotFoundException.class)
                .isThrownBy(() -> projectService.getProject(1L))
                .withMessage("Project Entity가 존재하지 않습니다.");
    }

    private void validAll(ProjectResponseDto data, Project project) {
        assertThat(data.getProjectId()).isEqualTo(project.getProjectId());
        assertThat(data.getProjectTitle()).isEqualTo(project.getProjectTitle());
        assertThat(data.getProjectContent()).isEqualTo(project.getProjectContent());
        assertThat(data.getProjectPostScript()).isEqualTo(project.getProjectPostScript());
        assertThat(data.getProjectLink()).isEqualTo(project.getProjectLink());
        assertThat(data.getFileOriginName()).isEqualTo(project.getFileInfo().getFileOriginName());
        assertThat(data.getFileUrl()).isEqualTo(project.getFileInfo().getFileUrl());
        assertThat(data.getLevel()).isEqualTo(project.getLevel());
    }

    private void validNotFile(ProjectResponseDto data, Project project) {
        assertThat(data.getProjectId()).isEqualTo(project.getProjectId());
        assertThat(data.getProjectTitle()).isEqualTo(project.getProjectTitle());
        assertThat(data.getProjectContent()).isEqualTo(project.getProjectContent());
        assertThat(data.getProjectPostScript()).isEqualTo(project.getProjectPostScript());
        assertThat(data.getProjectLink()).isEqualTo(project.getProjectLink());
        assertThat(data.getLevel()).isEqualTo(project.getLevel());
    }

    private ProjectRequestDto mockProjectRequestDto (Long memberId, int level) {
       return ProjectRequestDto.builder()
               .projectTitle("projectTitle")
               .projectContent("projectContent")
               .projectPostScript("projectPostScript")
               .projectLink("projectLink")
               .level(level)
               .memberId(memberId)
               .build();
    }

    private Project mockProject(Member member, Long id, int level) {
        return Project.builder()
                .projectId(id)
                .projectTitle("projectTitle")
                .projectContent("projectContent")
                .projectPostScript("projectPostScript")
                .fileInfo(new FileInfo("filePath", "fileOriginName", "fileUrl"))
                .projectLink("projectLink")
                .level(level)
                .member(member)
                .build();
    }
}
