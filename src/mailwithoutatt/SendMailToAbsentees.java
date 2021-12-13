package mailwithoutatt;

import java.io.Serializable;
import java.util.ArrayList;

import javax.mail.Session;

public interface SendMailToAbsentees extends Serializable {
	public Session Authentication1();
	public void Notification1(ArrayList<String> toEmail);
}
