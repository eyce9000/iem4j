package com.github.eyce9000.iem.api.relevance;

import java.util.List;
import java.util.Map;

public interface SessionRelevanceResponseHandler {
	public void onError(Exception ex);
	public void onResponse(List<Map<String,Object>> rows, long responseTime);
}
