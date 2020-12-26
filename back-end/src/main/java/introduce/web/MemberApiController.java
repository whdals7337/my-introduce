package introduce.web;

import introduce.ifs.CrudWithFileInterface;
import introduce.domain.network.Header;
import introduce.service.MemberService;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.member.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberApiController implements CrudWithFileInterface<MemberRequestDto, MemberResponseDto> {

    @Value("${file.upload-dir}")
    private String fileUploadPath;

    @Value("${file.member-dir}")
    private String subFileUploadPath;

    private final MemberService memberService;

    @PostMapping("/api/member")
    public Header<MemberResponseDto> save(MemberRequestDto requestDto, @RequestParam("file") MultipartFile file) {
        return memberService.save(requestDto, file);
    }

    @PutMapping("/api/member/{id}")
    public Header<MemberResponseDto> update(MemberRequestDto requestDto, @PathVariable Long id, @RequestParam(name="file", required=false) MultipartFile file) {
        return memberService.update(requestDto, id, file);
    }

    @DeleteMapping("/api/member/{id}")
    public Header<MemberResponseDto> delete(@PathVariable Long id) {
        return memberService.delete(id);
    }

    @GetMapping("/api/member/{id}")
    public Header<MemberResponseDto> findById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @GetMapping("/api/member")
    public Header<List<MemberResponseDto>> findAll() {
        return memberService.findAll();
    }
}
