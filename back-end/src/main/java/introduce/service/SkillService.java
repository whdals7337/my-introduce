package introduce.service;

import introduce.domain.skill.Skill;
import introduce.domain.skill.SkillRepository;
import introduce.web.dto.skill.SkillResponseDto;
import introduce.web.dto.skill.SkillSaveRequestDto;
import introduce.web.dto.skill.SkillUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SkillService {

    private final SkillRepository skillRepository;

    @Transactional
    public Long save(SkillSaveRequestDto requestDto) {
        return skillRepository.save(requestDto.toEntity()).getSkillId();
    }

    @Transactional
    public Long update(Long id, SkillUpdateRequestDto requestDto) {
        Skill skill = skillRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 스킬의 정보가 없습니다."));
        if(skill.getLevel() != requestDto.getLevel()){
            int originLevel = skill.getLevel();
            int changedLevel = requestDto.getLevel();

            // 원래 순서 값이 변경할 순서 값보다 큰 경우
            if (originLevel > changedLevel) {
                // 원래 값부터 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 증가
                List<Skill> rangeRows = skillRepository.findByLevelBetween(changedLevel, originLevel-1);
                for(Skill row : rangeRows){
                    row.levelUp();
                }
            }
            // 원래 순서 값이 변경할 순서 값보다 작은 경우
            else {
                // 원래 값보다 크고 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 감소
                List<Skill> rangeRows = skillRepository.findByLevelBetween(originLevel+1, changedLevel);
                for(Skill row : rangeRows){
                    row.levelDown();
                }
            }
        }

        // 변경 요청된 값 update
        skill.update(requestDto.getSkillName(), requestDto.getFilePath(), requestDto.getFileOriginName(),
                requestDto.getSkillLevel(), requestDto.getLevel(), requestDto.getMemberId());

        return id;
    }


    @Transactional
    public void delete(Long id) {
        Skill skill = skillRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 스킬의 정보가 없습니다."));
        skillRepository.delete(skill);
    }

    @Transactional
    public List<SkillResponseDto> findAll() {
        return skillRepository.findAll().stream().map(SkillResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public SkillResponseDto findById(Long id) {
        Skill entity = skillRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 스킬의 정보가 없습니다."));
        return new SkillResponseDto(entity);
    }
}
