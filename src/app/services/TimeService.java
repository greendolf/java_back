package app.services;

import app.api.time.ITimeService;
import app.api.time.ITransporterUsing;
import infrastructure.controllers.out.ws.ITransporter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeService implements ITimeService, ITransporterUsing {
    private ITransporter transporter;

    @Override
    public void useTransporter(ITransporter transporter) {
        this.transporter = transporter;
    }

    @Override
    public void sendUpdate() {
        for (String login : transporter.getActiveClientNames()) {
            System.out.println("active clint: " + login);
            StringBuilder message = new StringBuilder();
            message.append("{\"date\" :\"");

            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String strDate = dateFormat.format(date);
            message.append(strDate);
            message.append("\"}");

            transporter.sendToClient(login, message.toString());
        }
    }
}
