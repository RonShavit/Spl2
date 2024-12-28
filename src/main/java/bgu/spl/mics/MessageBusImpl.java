package bgu.spl.mics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only one public method (in addition to getters which can be public solely for unit testing) may be added to this class
 * All other methods and members you add the class must be private.
 */
public class MessageBusImpl implements MessageBus {
	private static MessageBus singelton = null;
	private Map<Class<? extends Event<?>>, List<MicroService>> subscribedEvent;
	private Map<Class<? extends Broadcast>, List<MicroService>> subscribedBroadcast;

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		// TODO Auto-generated method stub
		if(subscribedEvent.containsKey(type))
		{
			List<MicroService> list = subscribedEvent.get(type);
			list.add(m);
			subscribedEvent.put(type,list);
		}
		else
		{
			List<MicroService> list = new LinkedList<>();
			list.add(m);
			subscribedEvent.put(type,list);
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		// TODO Auto-generated method stub
		if(subscribedBroadcast.containsKey(type))
		{
			List<MicroService> list = subscribedBroadcast.get(type);
			list.add(m);
			subscribedBroadcast.put(type,list);
		}
		else
		{
			List<MicroService> list = new LinkedList<>();
			list.add(m);
			subscribedBroadcast.put(type,list);
		}

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub
		e.resolveFuture(result);

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub
		try {
			List<MicroService> list = subscribedBroadcast.get(b.getClass());
			for(MicroService m:list)
			{
				// TODO : implement MicroService:reciveBrodacst()
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		try {
			List<MicroService> list = subscribedEvent.get(e.getClass());
			MicroService first = list.getFirst();
			list.remove(first);
			Future<T> f = e.getFuture();
			list.addLast(first);
			return  f;
		}
		catch (Exception ex)
		{}
		return null;
	}

	@Override
	public void register(MicroService m) {
		// TODO Auto-generated method stub
		m.createMessageQueue(); // TODO : implement

	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub
		if (m.isQueueExist())
			m.removeQueue();
		// TODO :
	}


	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	returns the instance of the singelton (create it if it doesn't exist)
	 */
	public static MessageBus getInstance()
	{
		if (singelton==null)
		{
			synchronized (singelton) {
				if (singelton == null)
					singelton = new MessageBusImpl();
			}
		}
		return  singelton;
	}

	/**
	 * constructor
	 */
	private MessageBusImpl()
	{
		subscribedEvent = new ConcurrentHashMap<>();
		subscribedBroadcast = new ConcurrentHashMap<>();
	}

	

}
