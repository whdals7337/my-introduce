package introduce.web;

import introduce.domain.network.Header;
import introduce.ifs.CrudWithFileInterface;
import introduce.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public abstract class CrudController<Req, Res, Repository> implements CrudWithFileInterface<Req, Res> {

    @Autowired(required = false)
    protected BaseService<Req, Res, Repository> baseService;

    @Override
    @PostMapping("")
    public Header<Res> save(Req requestDto, @RequestParam("file") MultipartFile file) {
        return baseService.save(requestDto, file);
    }

    @Override
    @PutMapping("{id}")
    public Header<Res> update(Req requestDto, @PathVariable Long id, @RequestParam(name="file", required=false) MultipartFile file) {
        return baseService.update(requestDto, id, file);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete( @PathVariable Long id) {
        return baseService.delete(id);
    }

    @Override
    @GetMapping("{id}")
    public Header<Res> findById( @PathVariable Long id) {
        return baseService.findById(id);
    }

    @Override
    @GetMapping("")
    public Header<List<Res>> findAll(Req requestDto, @PageableDefault(sort="rgDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return baseService.findAll(requestDto, pageable);
    }
}

