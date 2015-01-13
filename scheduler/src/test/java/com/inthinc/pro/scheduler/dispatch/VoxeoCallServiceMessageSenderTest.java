package com.inthinc.pro.scheduler.dispatch;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link com.inthinc.pro.scheduler.dispatch.VoxeoCallServiceMessageSender}.
 */
public class VoxeoCallServiceMessageSenderTest {

    @Test
    public void testCleanAmpersand(){
        VoxeoCallServiceMessageSender voxeoCallServiceMessageSender = new VoxeoCallServiceMessageSender();
        String ampMessage = "This is an & message test that also has < and >";
        String curedMessage = voxeoCallServiceMessageSender.cleanMessage(ampMessage);
        Assert.assertEquals(curedMessage, "This is an &amp; message test that also has &lt; and &gt;");
    }
}
