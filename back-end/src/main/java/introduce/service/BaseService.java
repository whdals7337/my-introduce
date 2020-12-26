package introduce.service;

import introduce.ifs.CrudWithFileInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseService<Req, Res, Repository> implements CrudWithFileInterface<Req, Res> {

    @Autowired(required = false)
    protected Repository baseRepository;
}
