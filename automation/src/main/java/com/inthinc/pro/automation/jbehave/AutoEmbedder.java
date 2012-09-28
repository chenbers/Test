package com.inthinc.pro.automation.jbehave;

import java.util.List;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.PrintStreamEmbedderMonitor;
import org.jbehave.core.embedder.StoryMapper;

import com.inthinc.pro.automation.selenium.AbstractPage;

public class AutoEmbedder extends Embedder {

    public AutoEmbedder(List<AbstractPage> pages) {
        super(new StoryMapper(), new AutoStoryRunner(pages), new PrintStreamEmbedderMonitor());
        ((AutoStoryRunner)this.storyRunner()).setEmbedder(this);
    }

}
