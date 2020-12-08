package introduce.web;

import introduce.domain.skill.Skill;
import introduce.domain.skill.SkillRepository;
import introduce.web.dto.skill.SkillSaveRequestDto;
import introduce.web.dto.skill.SkillUpdateRequestDto;
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

public class SkillApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SkillRepository skillRepository;

    @After
    public void tearDown() throws Exception {
        skillRepository.deleteAll();
    }

    @Test
    public void skill_save_test() throws Exception {
        String skillName = "스킬이름";
        String skillImagePath = "스킬 이미지 경로";
        String imageOriginName = "스킬 이미지 원본이름";
        Integer skillLevel = 3;
        Integer level = 1;
        Long memberId = (long) 1;

        SkillSaveRequestDto requestDto = SkillSaveRequestDto.builder()
                .skillName(skillName)
                .filePath(skillImagePath)
                .fileOriginName(imageOriginName)
                .skillLevel(skillLevel)
                .level(level)
                .memberId(memberId)
                .build();

        String url = "http://localhost:" + port + "/api/skill";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0l);

        List<Skill> all = skillRepository.findAll();
        assertThat(all.get(0).getSkillName()).isEqualTo(skillName);
        assertThat(all.get(0).getFilePath()).isEqualTo(skillImagePath);
        assertThat(all.get(0).getFileOriginName()).isEqualTo(imageOriginName);
        assertThat(all.get(0).getSkillLevel()).isEqualTo(skillLevel);
        assertThat(all.get(0).getLevel()).isEqualTo(level);
        assertThat(all.get(0).getMemberId()).isEqualTo(memberId);
    }

    @Test
    public void skill_update_test() throws Exception{
        Long updateId = null;
        // level 의 3과 1을 교환한 순서
        int[] level_list = {2, 3, 1 ,4};

        // 스킬 값 4개 setting
        for(int i = 1; i < 5; i++){
            Skill skill = skillRepository.save(Skill.builder()
                    .skillName("스킬 이름0" + i)
                    .filePath("스킬 이미지 경로0" + i)
                    .fileOriginName("스킬 이미지 이름0" + i)
                    .skillLevel(3)
                    .level(i)
                    .memberId((long) 1)
                    .build());

            // 스킬 level 이 3 인 경우의 id값
            if(i == 3) {
                updateId = skill.getSkillId();
            }
        }

        String expectedSkillName = "skillName";
        String expectedSkillImagePath = "skillImagePath";
        String expectedImageOriginName = "imageOriginName";
        int expectedSkillLevel = 1;
        int expectedLevel = 1;
        Long expectedMemberId = (long) 2;

        SkillUpdateRequestDto requestDto = SkillUpdateRequestDto.builder()
                .skillName(expectedSkillName)
                .filePath(expectedSkillImagePath)
                .fileOriginName(expectedImageOriginName)
                .skillLevel(expectedSkillLevel)
                .level(expectedLevel)
                .memberId(expectedMemberId)
                .build();

        String url = "http://localhost:" + port + "/api/skill/" + updateId;

        HttpEntity<SkillUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // 수정된 값 검증
        Skill target = skillRepository.findById(updateId).get();
        assertThat(target.getSkillName()).isEqualTo(expectedSkillName);
        assertThat(target.getFilePath()).isEqualTo(expectedSkillImagePath);
        assertThat(target.getFileOriginName()).isEqualTo(expectedImageOriginName);
        assertThat(target.getSkillLevel()).isEqualTo(expectedSkillLevel);
        assertThat(target.getLevel()).isEqualTo(expectedLevel);
        assertThat(target.getMemberId()).isEqualTo(expectedMemberId);

        // 테이블의 level 값 전체 검증
        List<Skill> all = skillRepository.findAll();
        for(int i = 0; i < 4; i++){
            assertThat(all.get(i).getLevel()).isEqualTo(level_list[i]);
        }
    }
}
