package SimpleThreadPool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

// 线程池里的核心线程数与最大线程数
// 线程池里真正工作的线程worker
// 线程池里用来存取任务的队列BlockingQueue
// 线程中的任务task

public class ThreadExcutor {
	// 初始化running
	public static volatile boolean Running = true;

	// 阻塞队列放 task
	public static BlockingDeque<Runnable> queue = null;
	//正在工作的 worker
	public static final HashSet<Worker> workers = new HashSet<Worker>();
	//要做的 task 
	public static final List<Thread> threadList = new ArrayList<Thread>();

	// 线程池大小
	int poolSize = 0;
	// 工作线程数
	int coreSize = 0;

	public static boolean shutDown = false;

	// 初始化线程池大小
	public ThreadExcutor(int poolSize) {
		this.poolSize = poolSize;
		queue = new LinkedBlockingDeque<Runnable>(poolSize);
	}

	// 执行体
	public void exec(Runnable runner) {
		if (runner == null) {
			throw new NullPointerException("null");
		}
		if (coreSize < poolSize) {
			addThread(runner);
		} else {
			try {
				queue.put(runner);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 加入新的线程
	public void addThread(Runnable runner) {
		coreSize++;
		Worker worker = new Worker(runner);
		workers.add(worker);
		Thread t = new Thread(worker);
		threadList.add(t);
		t.start();
	}

	// shut it down
	public static void shutItDown() {
		// 先让工作线程不要继续接任务
		Running = false;
		//当前线程停止
		if (!workers.isEmpty()) {
			for (Worker worker : workers) {
				worker.interruptIfIdle();
			}
		}
		shutDown = true;
		Thread.currentThread().interrupt();
	}

}
