package pl.mcx.corrupted.project.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mcx.corrupted.project.security.sm.SystemModule;


@Component
public final class SMServiceImpl implements SMService {

    private SystemModule systemModule;

    @Autowired
    public SMServiceImpl(final SystemModule systemModule) {
        this.systemModule = systemModule;
    }

    @Override
    public String send(final String data) {
        return systemModule.execute(data);
    }

}
