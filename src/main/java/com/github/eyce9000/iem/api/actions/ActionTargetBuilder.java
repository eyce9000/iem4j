package com.github.eyce9000.iem.api.actions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.bigfix.schemas.bes.BESActionTarget;

public class ActionTargetBuilder {
	public static BESActionTarget targetComputers(long... computerIDs){
		List<BigInteger> ids = new ArrayList<BigInteger>(computerIDs.length);
		for(long id:computerIDs){
			ids.add(BigInteger.valueOf(id));
		}
		return targetComputers(ids);
	}
	public static BESActionTarget targetComputers(String... computerIDs){
		List<BigInteger> ids = new ArrayList<BigInteger>(computerIDs.length);
		for(String id:computerIDs){
			ids.add(BigInteger.valueOf(Long.parseLong(id)));
		}
		return targetComputers(ids);
	}
	public static BESActionTarget targetComputers(List<BigInteger> computerIDs){
		BESActionTarget target = new BESActionTarget();
		for(BigInteger id:computerIDs){
			target.getComputerID().add(id);
		}
		return target;
	}
}
