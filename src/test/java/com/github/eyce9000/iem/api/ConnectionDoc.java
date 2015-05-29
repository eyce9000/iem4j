package com.github.eyce9000.iem.api;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="connection")
public class ConnectionDoc {
	@XmlElement(name="host")
	public URI host;
	@XmlElement(name="username")
	public String username;
	@XmlElement(name="password")
	public String password;
}
