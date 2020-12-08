package introduce.domain.skill;

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
public class SkillRepositoryTest {

    @Autowired
    SkillRepository skillRepository;

    @After
    public void cleanup() {
        skillRepository.deleteAll();;
    }

    @Test
    public void project_domain_test() {
        LocalDateTime now = LocalDateTime.of(2020,12,5,0,0,0);
        String skillName = "JAVA";
        String skillImagePath = "D:\\my-introduce\\images\\skills\\skillImage01.img";
        String imageOriginName = "java_logo_image";
        int skillLevel = 1;
        int level = 1;

        skillRepository.save(Skill.builder()
                .skillName(skillName)
                .filePath(skillImagePath)
                .fileOriginName(imageOriginName)
                .skillLevel(skillLevel)
                .level(level)
                .build());

        List<Skill> skillList = skillRepository.findAll();
        Skill skill = skillList.get(0);

        System.out.println(">>>>>>>>>> rgDate:" + skill.getRgDate());
        System.out.println(">>>>>>>>>> mdDate:" + skill.getMdDate());

        assertThat(skill.getSkillName()).isEqualTo(skillName);
        assertThat(skill.getFilePath()).isEqualTo(skillImagePath);
        assertThat(skill.getFileOriginName()).isEqualTo(imageOriginName);
        assertThat(skill.getSkillLevel()).isEqualTo(skillLevel);
        assertThat(skill.getLevel()).isEqualTo(level);
        assertThat(skill.getRgDate()).isAfter(now);
        assertThat(skill.getMdDate()).isAfter(now);
    }
}
