package mailwithatt;

import java.io.Serializable;
import java.util.ArrayList;

import javax.mail.Session;

public interface SendMailToHM extends Serializable {
	public Session Authentication2();
	public void Notification2(String toEmail);
}
