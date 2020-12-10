package introduce.web;

import introduce.domain.skill.Skill;
import introduce.domain.skill.SkillRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SkillApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private SkillRepository skillRepository;

    @After
    public void tearDown() throws Exception {
        skillRepository.deleteAll();
    }

    @Test
    public void skill_save_test() throws Exception {
        MockMultipartFile testFile
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        String skillName = "스킬이름";
        String imageOriginName = "hello.txt";
        Integer skillLevel = 3;
        Integer level = 1;
        Long memberId = (long) 1;

        String url = "http://localhost:" + port + "/api/skill";

        this.mockMvc.perform(multipart(url)
                .file(testFile)
                .param("skillName", skillName)
                .param("skillLevel", String.valueOf(skillLevel))
                .param("level", String.valueOf(level))
                .param("memberId", String.valueOf(memberId)))
                .andExpect(status().isOk());

        List<Skill> all = skillRepository.findAll();
        assertThat(all.get(0).getSkillName()).isEqualTo(skillName);
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

        MockMultipartFile testFile
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        String expectedSkillName = "skillName";
        String expectedImageOriginName = "hello.txt";
        int expectedSkillLevel = 1;
        int expectedLevel = 1;
        Long expectedMemberId = (long) 2;

        String url = "http://localhost:" + port + "/api/skill/" + updateId;

        MockMultipartHttpServletRequestBuilder builder =
                multipart(url);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        this.mockMvc.perform(builder
                .file(testFile)
                .param("skillName", expectedSkillName)
                .param("skillLevel", String.valueOf(expectedSkillLevel))
                .param("level", String.valueOf(expectedLevel))
                .param("memberId", String.valueOf(expectedMemberId)))
                .andExpect(status().isOk());

        // 수정된 값 검증
        Skill target = skillRepository.findById(updateId).get();
        assertThat(target.getSkillName()).isEqualTo(expectedSkillName);
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
