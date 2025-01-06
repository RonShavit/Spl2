package bgu.spl.mics;

import bgu.spl.mics.application.messages.DetectObjectEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.services.LiDarService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    @Test
    void subscribeToEventIncreasesListSize()
    {
        MessageBusImpl messageBus = (MessageBusImpl)MessageBusImpl.getInstance();
        messageBus.subscribeEvent(DetectObjectEvent.class,new LiDarService("LiDarTest"));
        assertEquals(1,messageBus.getSubscribedEvent().get(DetectObjectEvent.class).size());
    }
    @Test
    void subscribeToBroadCastIncreasesListSize()
    {
        MessageBusImpl messageBus = (MessageBusImpl)MessageBusImpl.getInstance();
        messageBus.subscribeBroadcast(TickBroadcast.class, new LiDarService(("LiDarTest")));
        assertEquals(1,messageBus.getSubscribedBroadcast().get(TickBroadcast.class).size());
    }

    @Test
    void unregisterDeletesMessageListInMicroService()
    {
        MessageBusImpl messageBus = (MessageBusImpl)MessageBusImpl.getInstance();
        LiDarService liDarServiceTest =  new LiDarService("LiDarTest");
        messageBus.subscribeBroadcast(TickBroadcast.class,liDarServiceTest);
        messageBus.unregister(liDarServiceTest);
        assertTrue(!liDarServiceTest.isQueueExist());
    }
}