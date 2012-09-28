package com.inthinc.pro.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

public class DebugPhaseTracker implements PhaseListener
{
    private static final Logger logger = Logger.getLogger(DebugPhaseTracker.class);
    
    public DebugPhaseTracker()
    {
    }
    
    @Override
    public void afterPhase(PhaseEvent event)
    {
        logger.debug("AFTER PHASE: " + event.getPhaseId() + " ThreadID: " + Thread.currentThread().getId());
        
    }

    @Override
    public void beforePhase(PhaseEvent event)
    {
        logger.debug("BEFORE PHASE: " + event.getPhaseId() + " ThreadID: " + Thread.currentThread().getId());
        
    }

    @Override
    public PhaseId getPhaseId()
    {
        return PhaseId.ANY_PHASE;
    }

}
