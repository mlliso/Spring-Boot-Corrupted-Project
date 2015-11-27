package pl.mcx.corrupted.project.security.sm.basic;

import pl.mcx.corrupted.project.integration.tcp.SMGateway;
import pl.mcx.corrupted.project.security.sm.SystemModule;

public final class BasicSM implements SystemModule {

    private final SMGateway smGateway;

    public BasicSM(final SMGateway smGateway) {
        this.smGateway = smGateway;
    }

    @Override
    public String execute(final String data) {
        return new String(smGateway.sendAndReceiveBytes(data.getBytes()));
    }


}
