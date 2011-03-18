package com.inthinc.pro.backing;


public class ChartBeatBean extends BaseBean {
    private Boolean track = false;
    private String domain = "inthinc.com";
    private Integer uid = 8341;
    /**
     * @return the track
     */
    public Boolean getTrack() {
        return track;
    }
    /**
     * @param track the track to set
     */
    public void setTrack(Boolean track) {
        this.track = track;
    }
    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }
    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
    /**
     * @return the uid
     */
    public Integer getUid() {
        return uid;
    }
    /**
     * @param uid the uid to set
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
