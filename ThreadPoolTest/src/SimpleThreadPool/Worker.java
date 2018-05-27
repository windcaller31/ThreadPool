package SimpleThreadPool;

public class Worker implements Runnable{
	public Worker(Runnable runner){
		ThreadExcutor.queue.offer(runner);
	}
	
	@Override
	public void run() {
		while (ThreadExcutor.Running==true){
			if(ThreadExcutor.shutDown == true){
				ThreadExcutor.shutItDown();
			}
			Runnable task = null;
			try {
				task = getTask();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			System.out.println("开始执行任务");
			task.run();
//			ThreadExcutor.threadList.remove(0);
//			System.out.println();
		}
	}
	
	//获取一个task
	public Runnable getTask() throws InterruptedException{
		return ThreadExcutor.queue.take();
	}
	
	//interrupt
	//当前线程停止
	public void interruptIfIdle(){
		 for (Thread thread : ThreadExcutor.threadList) {
             System.out.println(thread.getName() + " interrupt");
             thread.interrupt();
         }
	}
}
