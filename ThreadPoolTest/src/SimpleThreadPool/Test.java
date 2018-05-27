package SimpleThreadPool;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		ThreadExcutor excutor = new ThreadExcutor(3);
		for (int i = 0; i < 10; i++) {
			excutor.exec(new Runnable() {
				public void run() {
					System.out.println("线程 " + Thread.currentThread().getName() + " 在帮我干活");
					System.out.println("---- :" + ThreadExcutor.threadList.size());
				}
			});
		}
		excutor.shutItDown();;
	}
}
