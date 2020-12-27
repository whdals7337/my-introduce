package introduce.ifs;

import introduce.domain.network.Header;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CrudWithFileInterface<Req, Res> {

    Header<Res> save(Req requestDto, MultipartFile file);
    Header<Res> update(Req requestDto, Long id, MultipartFile file);
    Header delete(Long id);
    Header<Res> findById(Long id);
    Header<List<Res>> findAll(Req requestDto, Pageable pageable);
}
