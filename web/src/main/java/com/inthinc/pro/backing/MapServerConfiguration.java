package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MapServerConfiguration {

	private String host;
	private String port;
	private String url;
	private String proxy;
	private List<Layer> layers;
	private String wmsLayer;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getProxy() {
		return proxy;
	}
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	public List<Layer> getLayersList() {
		return layers;
	}
	public void setLayersList(List<Layer> layers) {
		this.layers = layers;
	}
	
	public void setLayers(String layers){
		
		this.layers = new ArrayList<Layer>();
		
		if ((layers == null)||(layers.isEmpty())) return;
		
		StringTokenizer nameTokens = new StringTokenizer(layers);
		while(nameTokens.hasMoreTokens()){
			
			Layer layer = new Layer();
			
			layer.setLayer(nameTokens.nextToken());
			layer.setName(nameTokens.nextToken());
			
			this.layers.add(layer);
		}
		
	}
	public String getWmsLayer() {
		return wmsLayer;
	}
	public void setWmsLayer(String wmsLayer) {
		this.wmsLayer = wmsLayer;
	}
	
	public class Layer {
		
		String layer;
		String name;


		public String getLayer() {
			return layer;
		}

		public void setLayer(String layer) {
			this.layer = layer;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
