package com.sinaleju.lifecircle.app.model.json;

import java.util.List;

import org.json.JSONException;

import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;

public interface MultiJSONParserBase<T> {
	String NULL = "NULL";
	List<MultiModelBase> parseJSON(String json, MultiModelsSet set)
			throws JSONException;
}
