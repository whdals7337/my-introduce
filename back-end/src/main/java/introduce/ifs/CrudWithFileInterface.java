package introduce.ifs;

import introduce.domain.network.Header;
import org.springframework.web.multipart.MultipartFile;

public interface CrudWithFileInterface<Req, Res> {

    Header<Res> save(Req requestDto, MultipartFile file);
    Header<Res> update(Req requestDto, Long id, MultipartFile file);
    Header delete(Long id);
    Header<Res> findById(Long id);
}
