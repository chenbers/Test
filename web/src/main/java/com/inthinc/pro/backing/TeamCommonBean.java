package com.inthinc.pro.backing;

import com.inthinc.pro.model.Group;

public class TeamCommonBean extends BaseBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer groupID;
    private Group group;
    
    private DurationBean durationBean;
    
	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {

		if(groupID!= null){
			
		    group = getGroupHierarchy().getGroup(groupID);
		}
		this.groupID = groupID;
		    
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
    public DurationBean getDurationBean() {
        return durationBean;
    }
    public void setDurationBean(DurationBean durationBean) {
        this.durationBean = durationBean;
    }

}
