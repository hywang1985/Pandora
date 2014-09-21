package com.pandorabox.web.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.pandorabox.cons.CommonConstant;

public abstract class DescriptorHandler<T> {

	protected DescriptorHandler() {
	}

	public List<T> convertJSONToDescriptors(String jsonString) {
		List<T> result = null;
		JSONArray imagesInfos = retrieveDescriptorJSONObjects(jsonString);
		Iterator<?> it = imagesInfos.iterator();
		while (it.hasNext()) {
			String fileName = null;
			JSONObject info = JSONObject.fromObject(it.next());
			String relativeUrl = findURL(info);
			fileName = findFileName(relativeUrl);

			if (result == null) {
				result = new ArrayList<T>();
			}
			T descriptor = buildDescriptor(fileName, relativeUrl);
			result.add(descriptor);
		}
		return result;
	};

	public List<Integer> getDelectedDescriptors(String jsonString) {
		List<Integer> result = null;
		JSONArray deletedDescriptors = retrieveDescriptorJSONObjects(jsonString);
		Iterator it = deletedDescriptors.iterator();
		while (it.hasNext()) {
			Integer descriptorId = Integer.parseInt((String) it.next());
			if (result == null) {
				result = new ArrayList<Integer>();
			}
			result.add(descriptorId);
		}
		return result;
	}

	protected JSONArray retrieveDescriptorJSONObjects(String jsonString) {
		JSONArray imagesInfos = JSONArray.fromObject(jsonString);
		return imagesInfos;
	}

	protected String findURL(JSONObject info) {
		return info.getString(CommonConstant.URL_KEY);
	}

	protected String findFileName(String relativeUrl) {
		String fileName;
		String[] splited = StringUtils.split(relativeUrl, "/");
		if (splited.length > 1) {
			fileName = splited[splited.length - 1];
		} else {
			fileName = relativeUrl;
		}
		return fileName;
	}

	protected T buildDescriptor(String fileName, String relativeUrl) {
		T descriptor = createDescriptor();
		setUpDescriptor(descriptor, fileName, relativeUrl);
		return descriptor;
	}

	protected abstract T createDescriptor();

	protected abstract void setUpDescriptor(T descriptor, String fileName,
			String relativeUrl);
}
