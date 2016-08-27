package com.zookeeper.test;

import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class ConnTest {
	private static final String connStr = "192.168.1.189:2181";
	private static String testPath = "/test";
	
	private static CustomClient createClient(String name){
		CustomClient tmp = new CustomClient(name);
		tmp.init();
		return tmp;
	}
	
	private static void doSomeThing(CustomClient customClient, String str){
		ZkClient client = customClient.getZkClient();

		boolean exist = client.exists(testPath);
		if(!exist){
			client.create(testPath, str, CreateMode.PERSISTENT);
		}
		
		client.subscribeDataChanges(testPath, new IZkDataListener() {
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				// TODO Auto-generated method stub
				System.err.println("client:" + customClient.getName() + ",find data changed");
			}
		});
	}
	
	private static void writeData(CustomClient customClient, String info){
		ZkClient client = customClient.getZkClient();
		boolean exist = client.exists(testPath);
		if(!exist){
			client.create(testPath, info, CreateMode.PERSISTENT);
		}
		client.writeData(testPath, info);
		System.err.println("client name:" + customClient.getName() + ",change testPath data");
	}
	
	
	public static void main(String[] args) throws InterruptedException {
//		CustomClient client0 = createClient("client");
//		doSomeThing(client0,"data");
//		writeData(client0,"data");
//		CountDownLatch latch = new CountDownLatch(10);
//		CustomClient[] cc = new CustomClient[10];
//		for (int i = 1; i <= 10; i++) {
//			final int tt = i;
//			Thread t = new Thread(
//				new Runnable() {
//					@Override
//					public void run() {
						// TODO Auto-generated method stub
//						CustomClient client = createClient("client" + i);
//						doSomeThing(client,"data" + i);
//						writeData(client,"data" + i);
//						latch.countDown();
//					}
//			});
//			t.start();
//		}
//		try {
//			latch.await();
//			System.err.println("all do");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		writeData(cc[9], "xixi");
		
		
		CustomClient client0 = createClient("first");
		doSomeThing(client0,"first");
		//writeData(client0,"first");
		
		CustomClient client1 = createClient("second");
		doSomeThing(client1,"second");
		//writeData(client1,"second");
		
		CustomClient client2 = createClient("third");
		doSomeThing(client2,"third");
		//writeData(client2,"third");
		
		
		writeData(client0,"readWord");
		
		Thread.sleep(5000);
	}
	
	private static class CustomClient{
		private static byte count = 0;
		
		private String name;
		
		private ZkClient zkClient;
		
		public CustomClient(String name) {
			super();
			this.name = name;
		}

		public void init(){
			zkClient = new ZkClient(connStr, 10000);
			count++;
			System.err.println("count" + count);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ZkClient getZkClient() {
			return zkClient;
		}

		public void setZkClient(ZkClient zkClient) {
			this.zkClient = zkClient;
		}
		
	}
}
