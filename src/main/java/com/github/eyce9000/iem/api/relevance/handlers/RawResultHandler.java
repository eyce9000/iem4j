package com.github.eyce9000.iem.api.relevance.handlers;

import java.util.Map;

public interface RawResultHandler {
	public void onError(Exception ex);
	public void onResult(Map<String,Object> rawResult) throws Exception;
	public void onComplete(long timestamp);
}
