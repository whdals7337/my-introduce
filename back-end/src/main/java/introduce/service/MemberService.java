package introduce.service;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.ifs.CrudWithFileInterface;
import introduce.network.Header;
import introduce.utill.FileUtil;
import introduce.web.dto.member.MemberRequestDto;
import introduce.web.dto.member.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements CrudWithFileInterface<MemberRequestDto, MemberResponseDto> {

    @Value("${file.upload-dir}")
    private String fileUploadPath;

    @Value("${file.member-dir}")
    private String subFileUploadPath;

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id).get();
        MemberResponseDto dto = new MemberResponseDto(member);
        return dto;
    }

    @Override
    @Transactional
    public Header<MemberResponseDto> save(MemberRequestDto requestDto, MultipartFile file) {
        log.info("member save start");

        // [1] file parameter setting
        String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
        String saveName = FileUtil.getRandomFileName(originalName);
        String saveDir = fileUploadPath + "/" + subFileUploadPath;
        String savePath =  saveDir +"/"+ saveName;
        log.info("[1] file parameter setting");

        // [2] file 디렉토리 생성
        FileUtil.createDir(saveDir);
        log.info("[2] file 디렉토리 생성");

        // [3] member info DB 등록
        requestDto.settingFileInfo(savePath, originalName);
        Member member = memberRepository.save(requestDto.toEntity());
        log.info("[3] member info DB 등록");

        // [4] file transfer
        try {
            file.transferTo(new File(savePath));
            log.info("[4] file transfer");

        } catch (IOException e) {
            log.info("[4] file transfer fail");
            e.printStackTrace();
            return Header.ERROR("파일 변환 실패");
        }

        log.info("member save end");
        return response(member);
    }
    @Override
    @Transactional
    public Header update(MemberRequestDto requestDto, Long id, MultipartFile file) {
        log.info("member update start");
        Optional<Member> optional = memberRepository.findById(id);

        return optional.map(member -> {
            // 첨부된 파일이 없는 경우
            if(file == null || file.isEmpty()) {
                log.info("첨부된 파일 없음");

                // [1] 기존 정보 셋팅
                requestDto.settingFileInfo(member.getFilePath(), member.getFileOriginName());
                log.info("[1] 기존 정보 셋팅");

                // [2] member info DB update
                member.update(requestDto.toEntity());
                log.info("[2] member info DB update");
            }
            // 첨부된 파일이 있는 경우
            else {
                log.info("첨부된 파일 있음");

                // [1] file parameter setting
                String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
                String saveName = FileUtil.getRandomFileName(originalName);
                String saveDir = fileUploadPath + "/" + subFileUploadPath;
                String savePath = saveDir + "/" + saveName;
                String preExistingFilePath = member.getFilePath();
                requestDto.settingFileInfo(savePath, originalName);
                log.info("[1] file parameter setting");

                // [2] file 디렉토리 생성
                FileUtil.createDir(saveDir);
                log.info("[2] file 디렉토리 생성");

                // [3] member info DB update
                member.update(requestDto.toEntity());
                log.info("[3] member info DB update");

                // [4] file transfer
                try {
                    file.transferTo(new File(savePath));
                    log.info("[4] file transfer");

                } catch (IOException e) {
                    log.info("[4] file transfer fail");
                    e.printStackTrace();
                    return Header.ERROR("파일 변환 실패");
                }

                // [5] pre-existing file delete
                FileUtil.deleteFile(preExistingFilePath);
                log.info("[5] pre-existing file delete");

            }

            log.info("member update end");
            return response(member);
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header delete(Long id) {
        log.info("member delete start");
        Optional<Member> optional = memberRepository.findById(id);

        return optional.map(member -> {
            // [1] member info DB delete
            memberRepository.delete(member);
            log.info("[1] member info DB delete");

            // [2] pre-existing file delete
            FileUtil.deleteFile(member.getFilePath());
            log.info("[2] pre-existing file delete");

            log.info("member delete end");
            return Header.OK();

        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header<MemberResponseDto> findById(Long id) {
        log.info("member findById start");
        log.info("member findById end");
        return memberRepository.findById(id).map(this::response).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Transactional
    public Header<List<MemberResponseDto>> findAll() {
        log.info("member findAll start");
        log.info("member findAll end");
        return Header.OK(memberRepository.findAll().stream().map(MemberResponseDto::new).collect(Collectors.toList()));
    }

    public Header<MemberResponseDto> response(Member member) {
        MemberResponseDto responseDto = MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .comment(member.getComment())
                .filePath(member.getFilePath())
                .fileOriginName(member.getFileOriginName())
                .subIntroduction(member.getSubIntroduction())
                .introduction(member.getIntroduction())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .build();

        return Header.OK(responseDto);
    }
}
