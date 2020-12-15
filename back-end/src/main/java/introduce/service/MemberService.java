package introduce.service;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.utill.FileUtil;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.member.MemberSaveRequestDto;
import introduce.web.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @Transactional
    public Long save(MemberSaveRequestDto requestDto, MultipartFile file) throws Exception {
        logger.info("member save start");

        // [1] file parameter setting
        String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
        String saveName = FileUtil.getRandomFileName(originalName);
        String saveDir = fileUploadPath + "/" + subFileUploadPath;
        String savePath =  saveDir +"/"+ saveName;
        logger.info("[1] file parameter setting");

        // [2] file 디렉토리 생성
        FileUtil.createDir(saveDir);
        logger.info("[2] file 디렉토리 생성");

        // [3] member info DB 등록
        requestDto.saveFileInfoSetting(savePath, originalName);
        Long memberId = memberRepository.save(requestDto.toEntity()).getMemberId();
        logger.info("[3] member info DB 등록");

        // [4] file transfer
        file.transferTo(new File(savePath));
        logger.info("[4] file transfer");

        logger.info("member save end");
        return memberId;
    }

    @Transactional
    public Long update(Long id, MemberUpdateRequestDto requestDto, MultipartFile file) throws Exception {
        logger.info("member update start");
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다."));

        // 첨부된 파일이 없는 경우
        if(file == null || file.isEmpty()) {
            logger.info("첨부된 파일 없음");

            // [1] 기존 정보 셋팅
            requestDto.updateFileInfoSetting(member.getFilePath(), member.getFileOriginName());
            logger.info("[1] 기존 정보 셋팅");

            // [2] member info DB update
            member.update(requestDto.getComment(), requestDto.getFilePath(), requestDto.getFileOriginName(),
                    requestDto.getSubIntroduction(), requestDto.getIntroduction(), requestDto.getPhoneNumber(), requestDto.getEmail());
            logger.info("[2] member info DB update");

            logger.info("member update end");
            return id;
        }

        // 첨부된 파일이 있는 경우
        logger.info("첨부된 파일 있음");

        // [1] file parameter setting
        String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
        String saveName = FileUtil.getRandomFileName(originalName);
        String saveDir = fileUploadPath + "/" + subFileUploadPath;
        String savePath =  saveDir +"/"+ saveName;
        String preExistingFilePath = member.getFilePath();
        requestDto.updateFileInfoSetting(savePath, originalName);
        logger.info("[1] file parameter setting");

        // [2] file 디렉토리 생성
        FileUtil.createDir(saveDir);
        logger.info("[2] file 디렉토리 생성");

        // [3] member info DB update
        member.update(requestDto.getComment(), requestDto.getFilePath(), requestDto.getFileOriginName(),
                requestDto.getSubIntroduction(), requestDto.getIntroduction(), requestDto.getPhoneNumber(), requestDto.getEmail());
        logger.info("[3] member info DB update");

        // [4] file transfer
        file.transferTo(new File(savePath));
        logger.info("[4] file transfer");

        // [5] pre-existing file delete
        FileUtil.deleteFile(preExistingFilePath);
        logger.info("[5] pre-existing file delete");

        logger.info("member update end");
        return id;
    }

    @Transactional
    public void delete(Long id) {
        logger.info("member delete start");

        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다."));
        String preExistingFilePath = member.getFilePath();

        // [1] member info DB delete
        memberRepository.delete(member);
        logger.info("[1] member info DB delete");

        // [2] pre-existing file delete
        FileUtil.deleteFile(preExistingFilePath);
        logger.info("[2] pre-existing file delete");

        logger.info("member delete end");
    }

    @Transactional
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream().map(MemberResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public MemberResponseDto findById(Long id) {
        Member entity = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다."));
        return new MemberResponseDto(entity);
    }
}
