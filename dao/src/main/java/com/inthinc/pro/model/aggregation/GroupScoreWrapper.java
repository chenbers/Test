package com.inthinc.pro.model.aggregation;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Group;

@XmlRootElement
public class GroupScoreWrapper {
    private Group group;
    @Column(name = "driveQV")
    private Score score;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "GroupScoreWrapper [group=" + group + ", score=" + score + "]";
    }
}
