package bgu.spl.mics;

import bgu.spl.mics.application.messages.DetectObjectEvent;
import bgu.spl.mics.application.messages.PoseEvent;
import bgu.spl.mics.application.messages.TrackedObjectEvent;
import bgu.spl.mics.application.services.CameraService;
import bgu.spl.mics.application.services.LiDarService;
import bgu.spl.mics.application.services.PoseService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The MicroService is an abstract class that any micro-service in the system
 * must extend. The abstract MicroService class is responsible to get and
 * manipulate the singleton {@link MessageBus} instance.
 * <p>
 * Derived classes of MicroService should never directly touch the message-bus.
 * Instead, they have a set of internal protected wrapping methods (e.g.,
 * {@link #sendBroadcast(bgu.spl.mics.Broadcast)}, {@link #sendBroadcast(bgu.spl.mics.Broadcast)},
 * etc.) they can use. When subscribing to message-types,
 * the derived class also supplies a {@link Callback} that should be called when
 * a message of the subscribed type was taken from the micro-service
 * message-queue (see {@link MessageBus#register(bgu.spl.mics.MicroService)}
 * method). The abstract MicroService stores this callback together with the
 * type of the message is related to.
 * 
 * Only private fields and methods may be added to this class.
 * <p>
 */
public abstract class MicroService implements Runnable {

    private boolean terminated = false;
    private final String name;
    private ConcurrentLinkedQueue<Message> messagesQueue;
    private ConcurrentHashMap<Class<? extends Message>,Callback<? extends Message>> callbackMap;
    private MessageBus bus;

    public AtomicInteger getTick() {
        return tick;
    }

    private AtomicInteger tick;

    protected enum MessageTypeEnum
    {
        DETECTOBJECT,
        TRACKEDOBJECT,
        POSE,
        TICK,
        TERMINATE,
        CRASHED
    }

    /**
     * @param name the micro-service name (used mainly for debugging purposes -
     *             does not have to be unique)
     */
    public MicroService(String name) {
        this.name = name;
        this.bus = MessageBusImpl.getInstance();
        this.callbackMap = new ConcurrentHashMap<>();
        this.messagesQueue= new ConcurrentLinkedQueue<>();
        tick = new AtomicInteger(0);

    }

    /**
     * Subscribes to events of type {@code type} with the callback
     * {@code callback}. This means two things:
     * 1. Subscribe to events in the singleton event-bus using the supplied
     * {@code type}
     * 2. Store the {@code callback} so that when events of type {@code type}
     * are received it will be called.
     * <p>
     * For a received message {@code m} of type {@code type = m.getClass()}
     * calling the callback {@code callback} means running the method
     * {@link Callback#call(java.lang.Object)} by calling
     * {@code callback.call(m)}.
     * <p>
     * @param <E>      The type of event to subscribe to.
     * @param <T>      The type of result expected for the subscribed event.
     * @param type     The {@link Class} representing the type of event to
     *                 subscribe to.
     * @param callback The callback that should be called when messages of type
     *                 {@code type} are taken from this micro-service message
     *                 queue.
     */
    protected final <T, E extends Event<T>> void subscribeEvent(Class<E> type, Callback<E> callback) {

        bus.subscribeEvent(type,this);
        this.callbackMap.put(type,callback);
    }

    /**
     * Subscribes to broadcast message of type {@code type} with the callback
     * {@code callback}. This means two things:
     * 1. Subscribe to broadcast messages in the singleton event-bus using the
     * supplied {@code type}
     * 2. Store the {@code callback} so that when broadcast messages of type
     * {@code type} received it will be called.
     * <p>
     * For a received message {@code m} of type {@code type = m.getClass()}
     * calling the callback {@code callback} means running the method
     * {@link Callback#call(java.lang.Object)} by calling
     * {@code callback.call(m)}.
     * <p>
     * @param <B>      The type of broadcast message to subscribe to
     * @param type     The {@link Class} representing the type of broadcast
     *                 message to subscribe to.
     * @param callback The callback that should be called when messages of type
     *                 {@code type} are taken from this micro-service message
     *                 queue.
     */
    protected final <B extends Broadcast> void subscribeBroadcast(Class<B> type, Callback<B> callback) {
        bus.subscribeBroadcast(type,this);
        callbackMap.put(type,callback);
    }

    /**
     * Sends the event {@code e} using the message-bus and receive a {@link Future<T>}
     * object that may be resolved to hold a result. This method must be Non-Blocking since
     * there may be events which do not require any response and resolving.
     * <p>
     * @param <T>       The type of the expected result of the request
     *                  {@code e}
     * @param e         The event to send
     * @return  		{@link Future<T>} object that may be resolved later by a different
     *         			micro-service processing this event.
     * 	       			null in case no micro-service has subscribed to {@code e.getClass()}.
     */
    protected final <T> Future<T> sendEvent(Event<T> e) {
        return  bus.sendEvent(e);

    }

    /**
     * A Micro-Service calls this method in order to send the broadcast message {@code b} using the message-bus
     * to all the services subscribed to it.
     * <p>
     * @param b The broadcast message to send
     */
    protected final void sendBroadcast(Broadcast b) {
        bus.sendBroadcast(b);
    }

    /**
     * Completes the received request {@code e} with the result {@code result}
     * using the message-bus.
     * <p>
     * @param <T>    The type of the expected result of the processed event
     *               {@code e}.
     * @param e      The event to complete.
     * @param result The result to resolve the relevant Future object.
     *               {@code e}.
     */
    protected final <T> void complete(Event<T> e, T result) {
        bus.complete(e,result);
    }

    /**
     * this method is called once when the event loop starts.
     */
    protected abstract void initialize();

    /**
     * Signals the event loop that it must terminate after handling the current
     * message.
     */
    protected final void terminate() {
        this.terminated = true;
        TerminatedCounter.getInstance().decreaseRunning();
        if (this.getClass()== CameraService.class)
        {
            ((CameraService)this).terminateCam();
        } else if (this.getClass()== LiDarService.class) {
            ((LiDarService)this).terminateLiDar();
        } else if (this.getClass()== PoseService.class) {
            ((PoseService)this).terminateGPSIMU();
        }
    }

    /**
     * @return the name of the service - the service name is given to it in the
     *         construction time and is used mainly for debugging purposes.
     */
    public final String getName() {
        return name;
    }

    /**
     * The entry point of the micro-service. TODO: you must complete this code
     * otherwise you will end up in an infinite loop.
     */
    @Override
    public final void run() {
        initialize();
        while (!terminated) {
            if (!messagesQueue.isEmpty())
            {
                Message msg = messagesQueue.remove();



                if (msg.getClass()== TrackedObjectEvent.class)
                    TrackedObjectMessage(msg);
                else if (msg.getClass()== DetectObjectEvent.class)
                    DetectedObjectMessage(msg);
                else if (msg.getClass()== PoseEvent.class)
                    PoseMessage(msg);
                else
                {
                    callbackMap.get(msg.getClass()).call();
                }

            }
        }
    }

    public void TrackedObjectMessage(Message msg){};

    public void DetectedObjectMessage(Message msg){};

    public void PoseMessage(Message msg){};

    /**
     *
     * @PRE isQueueExist==false;
     */
    public void createMessageQueue()
    {
        messagesQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * @PRE: None
     */
    public boolean isQueueExist()
    {
        return !(messagesQueue==null);
    }


    /**
     * @PRE: this.messageQueue!=null
     */
    public void removeQueue()
    {
        this.messagesQueue = null;
    }

    /**
     * @POST: this.tick = @PRE: this.tick + 1
     */
    public void updateTick()
    {
        tick.compareAndSet(tick.intValue(),tick.intValue()+1);
    }

    public void receiveMessage(Message msg)
    {
        messagesQueue.add(msg);
    }

    public final void terminatMe()
    {
        terminate();
    }

    public final void sendBroadCast(Broadcast b)
    {
        sendBroadcast(b);
    }

}
