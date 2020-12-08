package introduce.domain.project;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @After
    public void cleanup() {
        projectRepository.deleteAll();;
    }

    @Test
    public void project_domain_test() {
        LocalDateTime now = LocalDateTime.of(2020,12,5,0,0,0);
        String projectTitle = "프로젝트 이름";
        String projectContent = "프로젝트는 Spring4 + angularJs를 기반으로 개발된 프로젝트입니다.";
        String projectPostScript = "#Spring #angularJs #현장실습";
        String projectImagePath = "D:\\my-introduce\\images\\project\\projectImage01.img";
        String imageOriginName = "프로젝트 이미지 이름";
        int level = 1;

        projectRepository.save(Project.builder()
                .projectTitle(projectTitle)
                .projectContent(projectContent)
                .projectPostScript(projectPostScript)
                .filePath(projectImagePath)
                .fileOriginName(imageOriginName)
                .level(level)
                .build());

        List<Project> projectList = projectRepository.findAll();
        Project project = projectList.get(0);

        System.out.println(">>>>>>>>>> rgDate:" + project.getRgDate());
        System.out.println(">>>>>>>>>> mdDate:" + project.getMdDate());

        assertThat(project.getProjectTitle()).isEqualTo(projectTitle);
        assertThat(project.getProjectContent()).isEqualTo(projectContent);
        assertThat(project.getProjectPostScript()).isEqualTo(projectPostScript);
        assertThat(project.getFilePath()).isEqualTo(projectImagePath);
        assertThat(project.getFileOriginName()).isEqualTo(imageOriginName);
        assertThat(project.getLevel()).isEqualTo(level);
        assertThat(project.getRgDate()).isAfter(now);
        assertThat(project.getMdDate()).isAfter(now);
    }

}