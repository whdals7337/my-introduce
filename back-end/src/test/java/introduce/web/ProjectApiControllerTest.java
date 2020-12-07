package introduce.web;

import introduce.domain.project.Project;
import introduce.domain.project.ProjectRepository;
import introduce.web.dto.project.ProjectSaveRequestDto;
import introduce.web.dto.project.ProjectUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProjectRepository projectRepository;

    @After
    public void tearDown() throws Exception {
        projectRepository.deleteAll();
    }

    @Test
    public void project_save_test() throws Exception {
        String projectTitle = "프로젝트 이름";
        String projectContent = "프로젝트 내용";
        String projectPostScript = "프로젝트 추신";
        String projectImagePath = "프로젝트 이미지 경로";
        String imageOriginName = "프로젝트 이미지 원본이름";
        Integer level = 1;
        Long memberId = (long) 1;

        ProjectSaveRequestDto requestDto = ProjectSaveRequestDto.builder()
                .projectTitle(projectTitle)
                .projectContent(projectContent)
                .projectPostScript(projectPostScript)
                .projectImagePath(projectImagePath)
                .imageOriginName(imageOriginName)
                .level(level)
                .memberId(memberId)
                .build();

        String url = "http://localhost:" + port + "/api/project";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0l);

        List<Project> all = projectRepository.findAll();
        assertThat(all.get(0).getProjectTitle()).isEqualTo(projectTitle);
        assertThat(all.get(0).getProjectContent()).isEqualTo(projectContent);
        assertThat(all.get(0).getProjectPostScript()).isEqualTo(projectPostScript);
        assertThat(all.get(0).getProjectImagePath()).isEqualTo(projectImagePath);
        assertThat(all.get(0).getImageOriginName()).isEqualTo(imageOriginName);
        assertThat(all.get(0).getLevel()).isEqualTo(level);
        assertThat(all.get(0).getMemberId()).isEqualTo(memberId);
    }

    @Test
    public void project_update_test() throws Exception{
        Long updateId = null;
        // level 의 3과 1을 교환한 순서
        int[] level_list = {2, 3, 1 ,4};

        // 프로젝트 값 4개 setting
        for(int i = 1; i < 5; i++){
            Project project = projectRepository.save(Project.builder()
                    .projectTitle("프로젝트 이름0" + i)
                    .projectContent("프로젝트 내용0" + i)
                    .projectPostScript("프로젝트 추신0" + i)
                    .projectImagePath("프로젝트 이미지 경로0" + i)
                    .imageOriginName("프로젝트 이미지 원본이름0" + i)
                    .level(i)
                    .memberId((long) 1)
                    .build());

            // 프로젝트 level 이 3 인 경우의 id값
            if(i == 3) {
                updateId = project.getProjectId();
            }
        }

        String expectedProjectTitle = "projectTitle";
        String expectedProjectContent = "projectContent";
        String expectedProjectPostScript = "projectPostScript";
        String expectedProjectImagePath = "projectImagePath";
        String expectedImageOriginName = "imageOriginName";
        int expectedLevel = 1;
        Long expectedMemberId = (long) 2;

        ProjectUpdateRequestDto requestDto = ProjectUpdateRequestDto.builder()
                .projectTitle(expectedProjectTitle)
                .projectContent(expectedProjectContent)
                .projectPostScript(expectedProjectPostScript)
                .projectImagePath(expectedProjectImagePath)
                .imageOriginName(expectedImageOriginName)
                .level(expectedLevel)
                .memberId(expectedMemberId)
                .build();

        String url = "http://localhost:" + port + "/api/project/" + updateId;

        HttpEntity<ProjectUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // 수정된 값 검증
        Project target = projectRepository.findById(updateId).get();
        assertThat(target.getProjectTitle()).isEqualTo(expectedProjectTitle);
        assertThat(target.getProjectContent()).isEqualTo(expectedProjectContent);
        assertThat(target.getProjectPostScript()).isEqualTo(expectedProjectPostScript);
        assertThat(target.getProjectImagePath()).isEqualTo(expectedProjectImagePath);
        assertThat(target.getImageOriginName()).isEqualTo(expectedImageOriginName);
        assertThat(target.getLevel()).isEqualTo(expectedLevel);
        assertThat(target.getMemberId()).isEqualTo(expectedMemberId);

        // 테이블의 level 값 전체 검증
        List<Project> all = projectRepository.findAll();
        for(int i = 0; i < 4; i++){
            assertThat(all.get(i).getLevel()).isEqualTo(level_list[i]);
        }
    }
}
