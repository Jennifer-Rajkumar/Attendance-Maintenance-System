package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Properties;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer extends UnicastRemoteObject implements EAttendance {
    public RMIServer() throws RemoteException {
    }
    public Object getObject() throws Exception {
        int cnt = 0;
        try {
            Properties prop = new Properties();
			prop.load(new FileInputStream("serviceConfiguration.properties"));
			Enumeration counten = prop.elements();
			int noOfService = 0;
			while (counten.hasMoreElements()) {
				noOfService += 1;
				System.out.println(counten.nextElement());
			}
			System.out.println("No of services: " + noOfService);
			Class c[] = new Class[noOfService];
			Object o[] = new Object[noOfService];
			Enumeration en = prop.elements();
			while (en.hasMoreElements()) {
				String serviceFile = (String) en.nextElement();
				System.out.println("Service files : " + serviceFile);
				Properties serProp = new Properties();
				serProp.load(new FileInputStream(serviceFile));
				String interfaceImpl = serProp.getProperty("interfaceImpl");
				String interfaceName = serProp.getProperty("interfaceName");
				//System.out.println(interfaceImpl+" "+interfaceName);
				o[cnt] = Class.forName(interfaceImpl).getConstructor().newInstance();
				c[cnt] = Class.forName(interfaceName);
			    cnt++;
			}
			
			//System.out.println("After while");
			Object obj = Proxy.newProxyInstance(new School().getClass().getClassLoader(), c, new MyInvocationHandler(o));
			//System.out.println("After proxy");
			return obj;
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public static void main(String[] args) throws Exception {
        RMIServer server = new RMIServer();
        LocateRegistry.createRegistry(2000);
        Naming.bind("rmi://localhost:2000/service", server);
        System.out.println("Server ready to serve...");
    }
}

class MyInvocationHandler implements InvocationHandler, Serializable {
    Object obj[];
    public MyInvocationHandler(Object obj[]) {
        this.obj = obj;
    }
    
    @Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	    Object r = null;
	    for (Object o : obj) {
	        if (o != null) {
	            Method m[] = o.getClass().getDeclaredMethods();
	            //System.out.println("Inside invoke");
	            for (Method mm : m) {
	                mm.setAccessible(true);
	            }
	            try {
	                r = method.invoke(o, args);
	            } catch (Exception e) {}
	        }
	    }
        return r;
    }
}
