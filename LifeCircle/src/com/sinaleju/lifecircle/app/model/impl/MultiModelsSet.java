package com.sinaleju.lifecircle.app.model.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.widget.BaseAdapter;

import com.sinaleju.lifecircle.app.model.json.MultiJSONParserBase;
import com.sinaleju.lifecircle.app.utils.LCPageCounter;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class MultiModelsSet {

	private static final String TAG = "MultiModelsSet";
	private boolean refresh = true;
	private LinkedList<MultiModelBase> mModels = new LinkedList<MultiModelBase>();
	private List<MultiModelBase> mTempModel = new LinkedList<MultiModelBase>();
	private MultiJSONParserBase<MultiModelBase> mJSONParser;
	private Map<String, Integer> mReferenceMap = new HashMap<String, Integer>();
	private NodeList<MultiModelBase> mCurNodeList;
	private LockedNode<MultiModelBase> mLockedNode = null;
	private final int TYPE_COUNT;
	private LCPageCounter mPageCounter = new LCPageCounter();
	public static final String NULL_JSON = "1";
	private int MSG_TYPE = 0;
	
	public int getMSG_TYPE() {
		return MSG_TYPE;
	}

	public void setMSG_TYPE(int mSG_TYPE) {
		MSG_TYPE = mSG_TYPE;
	}

	public MultiModelsSet(int typeCount,
			MultiJSONParserBase<MultiModelBase> mJSONParser) {
		this.mJSONParser = mJSONParser;
		this.TYPE_COUNT = typeCount;
	}

	public MultiModelsSet(MultiJSONParserBase<MultiModelBase> mJSONParser) {
		this(-1, mJSONParser);
	}

	public boolean add(String json) {
		
		//clear node saver
		mTempModel.clear();
		
		if (json == null || json.equals(""))
			return false;

		if (mJSONParser != null) {
			try {
				
				// refresh handle
				if (isRefresh()) {
					refresh();
				}
				
				// parse
				List<MultiModelBase> models = mJSONParser.parseJSON(json, this);
				

				// invalid check
				if (models == null) {
					LogUtils.i(TAG, "data from json  null");
					return false;
				}
				if (models.size() == 0) {
					LogUtils.i(TAG, "data from json size is 0");
					return false;
				}

				// add
				addMulti(models);
				
				//mirror
				mTempModel = models;

			} catch (JSONException e) {
				LogUtils.e(TAG, "MutilModelsParseJSONErro", e);
				return false;
			}
			return true;
		}
		return false;
	}

	public void refresh() {
		clear();
		setRefresh(false);
	}

	public int size() {
		return mModels.size();
	}

	public MultiModelBase get(int index) {
		if (illegalArgument(index))
			throw new IllegalArgumentException("index must >= 0 ");
		if (noElements())
			throw new IndexOutOfBoundsException(
					"cur size is 0 , index invalid ");
		if(mModels != null && index >= mModels.size())
			throw new IndexOutOfBoundsException(
					"cur size >=  mModels.size(), index invalid ");
		return mModels.get(index);
	}

	protected boolean illegalArgument(int index) {
		return index < 0;
	}

	protected boolean noElements() {
		return size() == 0;
	}

	protected void clear() {
		if (nodeDelete())
			nodeClear();
		else
			totallyClear();
	}
	
	public void refreshImmediatelyWithAdapterNotify(BaseAdapter a){
		clear();
		a.notifyDataSetChanged();
	}

	protected void nodeClear() {
		// typeNodeClear();
		modelNodeClear();
	}

	/**
	 * 获取上一次更新添加的队列
	 * @return
	 */
	public List<MultiModelBase> getTempList() {
		return mTempModel;
	}

	/**
	 * models 的节点删除
	 */
	protected void modelNodeClear() {
		mModels.clear();
		mModels.addAll(mLockedNode.getSavedList());
	}

	public void changeNodeList(List<MultiModelBase> list) {
		if (list == null)
			return;
		modelNodeClear();
		mModels.addAll(list);
		//tai tmd luan He-He
		mCurNodeList = list instanceof NodeList?(NodeList<MultiModelBase>) list:null;
	}

	/**
	 * type的节点删除
	 */
	protected void typeNodeClear() {
		// mReferenceMap.clear();
		// mReferenceMap.putAll(mLockedNode.getRecordNodeMap());
		// mTypeCount = mLockedNode.getTypeCountNode();
	}

	public boolean locked() {
		return mLockedNode != null;
	}

	public void lockNode() {
		mLockedNode = new LockedNode<MultiModelBase>(size(), mReferenceMap,
				mModels, mTypeCount);
	}

	protected boolean nodeDelete() {
		return locked() && size() >= getNode();
	}

	/**
	 * 获取node position
	 * 
	 * @return
	 */
	protected int getNode() {
		return mLockedNode.node();
	}

	public List<MultiModelBase> getNodeList() {
		return mLockedNode.getSavedList();
	}

	public void totallyClear() {
		mModels.clear();
		mReferenceMap.clear();
	}

	protected void addFirst(LinkedList<MultiModelBase> modes) {
		modes.addAll(0, modes);
	}

	protected void addFirst(MultiModelBase mode) {
		mModels.addFirst(mode);
	}

	protected void addLast(MultiModelBase mode) {
		mModels.addLast(mode);
	}

	private int mTypeCount = 0;

	protected void addMulti(List<MultiModelBase> multiModels) {
		LogUtils.v(TAG, "addMulti  ---" + " multiModels.size()  : " + multiModels.size());
		for (int i = 0; i < multiModels.size(); i++) {
			MultiModelBase model = multiModels.get(i);

			// add
			addLast(model);

			// setType
			String className = model.getClass().getName();
			if (mReferenceMap.containsKey(className)) {
				model.setModelType(mReferenceMap.get(className));
				continue;
			}

			mReferenceMap.put(className, getInternalTypeCount());
			model.setModelType(getInternalTypeCount());
			autoIncreaseTypeCount();
		}
	}

	protected void resetTypeCount() {
		mTypeCount = 0;
	}

	protected void autoIncreaseTypeCount() {
		mTypeCount++;
	}

	private boolean TYPECOUNT() {
		return TYPE_COUNT != -1;
	}

	protected void addLast(LinkedList<MultiModelBase> modes) {
		mModels.addAll(modes);
	}

	protected boolean isRefresh() {
		return refresh;
	}

	protected void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public void setRefresh() {
		setRefresh(true);
		resetPageCounter();
	}

	public MultiJSONParserBase<MultiModelBase> getJSONParser() {
		return mJSONParser;
	}

	public void setJSONParser(MultiJSONParserBase<MultiModelBase> mJSONParser) {
		this.mJSONParser = mJSONParser;
	}

	public int typeCount() {
		return TYPECOUNT() ? TYPE_COUNT : mReferenceMap.size();
	}

	private int getInternalTypeCount() {
		return mTypeCount;
	}

	private static class LockedNode<T> {
		private Map<String, Integer> mRecordNodeMap = new HashMap<String, Integer>();
		private List<T> mNodeList = new LinkedList<T>();
		private final int NODE;
		private int typeCountNode;

		public LockedNode(int node) {
			this(node, null, null, 0);
		}

		public LockedNode(int node, Map<String, Integer> mRecordNodeMap,
				LinkedList<T> T, int typeCountNode) {
			this.NODE = node;
			setRecordNodeMap(mRecordNodeMap);
			setSavedList(T);
		}

		public Map<String, Integer> getRecordNodeMap() {
			return mRecordNodeMap;
		}

		public void setRecordNodeMap(Map<String, Integer> mRecordNodeMap) {
			if (mRecordNodeMap != null)
				this.mRecordNodeMap.putAll(mRecordNodeMap);
		}

		public int node() {
			return NODE;
		}

		public List<T> getSavedList() {
			return mNodeList;
		}

		public void setSavedList(List<T> nodeList) {
			if (nodeList != null)
				this.mNodeList.addAll(nodeList);
		}

		public void add(T t) {
			mNodeList.add(t);
		}

		public void addAll(List<T> ts) {
			mNodeList.addAll(ts);
		}

		public int getTypeCountNode() {
			return typeCountNode;
		}

		public void setTypeCountNode(int typeCountNode) {
			this.typeCountNode = typeCountNode;
		}

	}

	public int getNextPageStart() {
		return getMPageCounter().getStart();
	}

	public void setNextPageStart(int i) {
		getMPageCounter().setNextStart(i);
	}

	public int getPageSize() {
		return getMPageCounter().getSize();
	}

	public int getPageCounterTotalValue() {
		return getMPageCounter().getTotal();
	}

	public void setPageCounterTotalValue(int total) {
		getMPageCounter().setTotal(total);
	}

	public void setPageLeft(int left) {
		getMPageCounter().setLeft(left);
	}

	private void resetPageCounter() {
		getMPageCounter().reset();
	}

	public boolean isMax() {
		return getMPageCounter().isMax();
	}
	
	public LCPageCounter getMPageCounter() {
		if (locked() && mCurNodeList != null)
			return mCurNodeList.getCounter();
		return this.mPageCounter;
	}

	public static class NodeList<E> extends LinkedList<E> {
		private static final long serialVersionUID = -8113480489725162725L;
		private LCPageCounter mCounter;
		
		public NodeList() {
			init();
		}
		
		private void init() {
			mCounter = new LCPageCounter();
		}

		public LCPageCounter getCounter() {
			return mCounter;
		}

		public void clear() {
			super.clear();
			mCounter.reset();
		}
	}
}
