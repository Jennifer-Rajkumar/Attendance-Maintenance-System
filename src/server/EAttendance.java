package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EAttendance extends Remote{
	public Object getObject() throws Exception;
}
