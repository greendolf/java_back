package app.api.time;

import infrastructure.controllers.out.ws.ITransporter;

public interface ITransporterUsing {

    public void useTransporter(ITransporter transporter);
}
