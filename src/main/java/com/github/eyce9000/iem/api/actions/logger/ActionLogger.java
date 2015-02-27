package com.github.eyce9000.iem.api.actions.logger;

import com.bigfix.schemas.besapi.BESAPI.Action;

public interface ActionLogger {
	public void logAction(Action action, String iemUser);
}
