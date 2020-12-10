package introduce.web;

import introduce.service.MemberService;
import introduce.service.ProjectService;
import introduce.service.SkillService;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.project.ProjectResponseDto;
import introduce.web.dto.skill.SkillResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
public class FileApiController {

    private final MemberService memberService;

    private final ProjectService projectService;

    private final SkillService skillService;

    @GetMapping("/api/download/{type}/{id}")
    public ResponseEntity<Resource> fileDownload(@PathVariable String type, @PathVariable("id") Long id) throws IOException {
        String filePath;
        String filename;

        switch (type) {
            case "member" :
                MemberResponseDto memberResponseDto = memberService.getMember(id);
                filePath = memberResponseDto.getFilePath();
                filename = memberResponseDto.getFileOriginName();
                break;
            case "project" :
                ProjectResponseDto projectResponseDto = projectService.getProject(id);
                filePath = projectResponseDto.getFilePath();
                filename = projectResponseDto.getFileOriginName();
                break;
            case "skill" :
                SkillResponseDto skillResponseDto = skillService.getSkill(id);
                filePath = skillResponseDto.getFilePath();
                filename = skillResponseDto.getFileOriginName();
                break;
            default:
                filePath = null;
                filename = null;
        }

        if(filePath != null && filename != null){
            Path path = Paths.get(filePath);
            Resource resource = new InputStreamResource(Files.newInputStream(path));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        }
        else {
            throw new IOException("존재하지않는 파일입니다.");
        }
    }
}
