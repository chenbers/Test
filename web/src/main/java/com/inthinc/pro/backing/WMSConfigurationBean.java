package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class WMSConfigurationBean {

    private String url;
    private String query;
    private List<Layer> layers;
    private String layerQueryParam;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Layer> getLayersList() {
        return layers;
    }

    public void setLayersList(List<Layer> layers) {
        this.layers = layers;
    }

    public void setLayers(String layers) {

        this.layers = new ArrayList<Layer>();

        if ((layers == null) || (layers.isEmpty()))
            return;

        StringTokenizer nameTokens = new StringTokenizer(layers);
        while (nameTokens.hasMoreTokens()) {

            Layer layer = new Layer();

            layer.setLayer(nameTokens.nextToken());
            layer.setName(nameTokens.nextToken());

            this.layers.add(layer);
        }

    }

    public String getLayerQueryParam() {
        return layerQueryParam;
    }

    public void setLayerQueryParam(String layerQueryParam) {
        this.layerQueryParam = layerQueryParam;
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
